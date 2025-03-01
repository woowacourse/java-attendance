package controller;

import domain.Crew;
import domain.CrewGroup;
import domain.attendance.Attendance;
import domain.attendance.AttendanceDate;
import domain.attendance.AttendanceStatus;
import domain.attendance.TimeTable;
import view.FileInputView;
import view.InputView;
import view.OutputView;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

import static controller.Command.*;
import static domain.attendance.TimeTable.*;
import static util.DateTimeUtils.*;

public class AttendanceController {
    private CrewGroup crews;
    private Command currentCommand;

    public AttendanceController() {
        crews = new CrewGroup();
    }

    public void run(){
        loadFile();
        OutputView.printWelcomeMessage();
        while((currentCommand = InputView.getCommand()) != EXIT){
            operateCommand();
            OutputView.printWelcomeMessage();
        }
    }

    private void operateCommand(){
        try{
            if(currentCommand == ATTEND){
                attendCommand();
            }
            if(currentCommand == EDIT){
                editCrewAttendance();
            }
            if(currentCommand == FIND_CREW_RECORD){
                findCrewCommand();
            }
            if(currentCommand == FIND_WARNING_CREWS){
                findWarningCrews();
            }
        }catch (IllegalArgumentException e){
            OutputView.printErrorMessage(e.getMessage());
        }
    }

    private void validateAttendDate(){
        if(!isAttendanceDay(TODAY_DATE_NOW)){
            throw new IllegalArgumentException(TODAY_DATE_NOW.format(localDayFormatter)
                    + TODAY_DATE_NOW.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN) + "은 등교일이 아닙니다.");
        }
    }

    private void attendCommand(){
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

    private void validateAttendTime(LocalDateTime attendTime){
        if(!isOnCampusOperatingTime(LocalTime.from(attendTime))){
            throw new IllegalArgumentException(attendTime.getHour() + "시 " + attendTime.getMinute() + "분은 캠퍼스 운영시간이 아닙니다.");
        }
    }

    private void findCrewCommand(){
        String findCrewName = InputView.getCrewName();
        Crew findCrew = crews.findByName(findCrewName);

        OutputView.printCrewAttendance(findCrewName ,findCrew.getAttendanceRecord());
    }

    private void editCrewAttendance(){
        String findCrewName = InputView.getEditCrewName();
        Crew findCrew = crews.findByName(findCrewName);
        Attendance crewRecord = findCrew.getAttendanceRecord();
        LocalDateTime editTime = InputView.getEditTime();
        AttendanceDate findDate = crewRecord.findByLocalDate(LocalDate.from(editTime));

        AttendanceDate oldRecord = new AttendanceDate(findDate.getAttendanceAt());
        crewRecord.editAttendance(editTime);

        OutputView.printEditResult(oldRecord, findDate);
    }

    private void findWarningCrews(){
        List<Crew> warningCrews = crews.getSortedWarningCrews();
        OutputView.printWarningCrews(warningCrews);
    }

    private void loadFile(){
        try {
            crews = FileInputView.loadInitFileData();
        }catch (IOException e){
            throw new IllegalArgumentException("IOException 발생");
        }
    }
}
