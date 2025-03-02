package controller;

import domain.Crew;
import domain.CrewGroup;
import domain.attendance.Attendance;
import domain.attendance.AttendanceDate;
import domain.attendance.AttendanceStatus;
import view.FileInputView;
import view.InputView;
import view.OutputView;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static controller.Command.*;
import static domain.attendance.TimeTable.*;
import static util.DateTimeUtils.*;

public class AttendanceController {
    private static CrewGroup crews;
    private static Command currentCommand;
    private static Map<Command,Runnable> commandHandler;
    private static boolean runFlag;

    public AttendanceController() {
        crews = new CrewGroup();
        handlerRegister();
    }

    public void run(){
        loadFile();
        runFlag = true;
        OutputView.printWelcomeMessage();
        while(runFlag){
            operateCommand();
            OutputView.printWelcomeMessage();
        }
    }

    private static void handlerRegister(){
        commandHandler = new HashMap<>();
        commandHandler.put(ATTEND, AttendanceController::attendCommand);
        commandHandler.put(EDIT, AttendanceController::editCrewAttendance);
        commandHandler.put(FIND_CREW_RECORD, AttendanceController::findCrewCommand);
        commandHandler.put(FIND_WARNING_CREWS, AttendanceController::findWarningCrews);
        commandHandler.put(EXIT,() -> runFlag = false);
    }

    private static void operateCommand(){
        try{
            currentCommand = InputView.getCommand();
            commandHandler.get(currentCommand).run();
        }catch (IllegalArgumentException e){
            OutputView.printErrorMessage(e.getMessage());
        }
    }

    private static void validateAttendDate(){
        if(!isAttendanceDay(TODAY_DATE_NOW)){
            throw new IllegalArgumentException(TODAY_DATE_NOW.format(localDayFormatter)
                    + TODAY_DATE_NOW.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN) + "은 등교일이 아닙니다.");
        }
    }

    public static void attendCommand(){
        validateAttendDate();

        String attendCrewName = InputView.getCrewName();
        Crew findCrew = crews.findByName(attendCrewName);

        LocalDateTime attendTime = InputView.getAttendTime();
        validateAttendTime(attendTime);

        Attendance crewAttendance = findCrew.getAttendanceRecord();
        crewAttendance.addAttendance(attendTime);
        AttendanceStatus status = crewAttendance.findByLocalDate(LocalDate.from(attendTime)).getStatus();
        OutputView.printAddAttendance(attendTime,status);
    }

    private static void validateAttendTime(LocalDateTime attendTime){
        if(!isOnCampusOperatingTime(LocalTime.from(attendTime))){
            throw new IllegalArgumentException(attendTime.getHour() + "시 " + attendTime.getMinute() + "분은 캠퍼스 운영시간이 아닙니다.");
        }
    }

    public static void findCrewCommand(){
        String findCrewName = InputView.getCrewName();
        Crew findCrew = crews.findByName(findCrewName);

        OutputView.printCrewAttendance(findCrewName ,findCrew.getAttendanceRecord());
    }

    public static void editCrewAttendance(){
        String findCrewName = InputView.getEditCrewName();
        Crew findCrew = crews.findByName(findCrewName);
        Attendance crewRecord = findCrew.getAttendanceRecord();
        LocalDateTime editTime = InputView.getEditTime();
        AttendanceDate findDate = crewRecord.findByLocalDate(LocalDate.from(editTime));

        AttendanceDate oldRecord = new AttendanceDate(findDate.getAttendanceAt());
        crewRecord.editAttendance(editTime);

        OutputView.printEditResult(oldRecord, findDate);
    }

    public static void findWarningCrews(){
        List<Crew> warningCrews = crews.getSortedWarningCrews();
        OutputView.printWarningCrews(warningCrews);
    }

    private static void loadFile(){
        try {
            crews = FileInputView.loadInitFileData();
        }catch (IOException e){
            throw new IllegalArgumentException("IOException 발생");
        }
    }
}
