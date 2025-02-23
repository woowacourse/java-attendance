package controller;

import static domain.UserCommandType.ALERT_CREW_CHECK;
import static domain.UserCommandType.ATTENDANCE_CHANGE;
import static domain.UserCommandType.ATTENDANCE_CHECK;
import static domain.UserCommandType.ATTENDANCE_SHOW;
import static domain.UserCommandType.QUIT;
import static domain.UserCommandType.getUserCommand;
import static domain.UserCommandType.validateUserCommand;

import domain.Attendance;
import domain.Crew;
import domain.CrewGroup;
import domain.Time;
import domain.UserCommandType;
import java.time.LocalDateTime;
import java.util.List;
import service.CrewLoader;
import util.Day;
import view.InputView;
import view.OutputView;
import view.dto.AlertCrewDTO;
import view.dto.AlertCrewsDTO;
import view.dto.AttendanceLogDTO;
import view.dto.ChangeAttendanceLogDTO;
import view.dto.CrewAttendancesDTO;

public class Controller {
    private final InputView inputView;
    private final OutputView outputView;

    public Controller(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        LocalDateTime today = LocalDateTime.of(2024, 12, 13, 10, 0);
        CrewLoader crewLoader = new CrewLoader();
        CrewGroup crewGroup = crewLoader.loadCrews(today);
        try {
            while (true) {
                String rawFunction = inputView.insertFunction(today);
                validateUserCommand(rawFunction);

                runCycle(getUserCommand(rawFunction), crewGroup, today);
                if (getUserCommand(rawFunction).equals(QUIT)) {
                    return;
                }
            }
        } catch (Exception e) {
            outputView.printError(e.getMessage());
        }
    }

    private void runCycle(UserCommandType userCommandType, CrewGroup crewGroup, LocalDateTime today) {
        if (userCommandType.equals(ATTENDANCE_CHECK)) {
            checkAttendance(crewGroup, today);
        }
        if (userCommandType.equals(ATTENDANCE_CHANGE)) {
            changeAttendance(crewGroup);
        }
        if (userCommandType.equals(ATTENDANCE_SHOW)) {
            showCrewAttendance(crewGroup);
        }
        if (userCommandType.equals(ALERT_CREW_CHECK)) {
            showAlertCrews(crewGroup);
        }
    }

    private void checkAttendance(CrewGroup crewGroup, LocalDateTime today) {
        Day.validateDay(today.getDayOfMonth(), today);
        String rawName = inputView.insertNickname();
        Crew crew = crewGroup.searchCrew(rawName);

        if (crew.isAlreadyChecked(today)) {
            outputView.printGuide();
            return;
        }

        String rawTime = inputView.insertTime();
        Time time = new Time(rawTime);
        LocalDateTime attendanceTime = LocalDateTime.of(today.getYear(), today.getMonth(), today.getDayOfMonth(),
                time.getHour(), time.getMinute());
        Attendance attendance = crew.addAttendance(attendanceTime);

        outputView.printAttendanceLog(AttendanceLogDTO.from(attendance));
    }

    private void changeAttendance(CrewGroup crewGroup) {
        String rawName = inputView.insertChangeDateNickname();
        Crew crew = crewGroup.searchCrew(rawName);

        int changeDate = inputView.insertChangeDate();
        String rawTime = inputView.insertChangeTime();
        Time time = new Time(rawTime);

        Attendance originalAttendance = crew.getSpecificAttendance(changeDate);
        Attendance copy = new Attendance(originalAttendance.getDate());
        Attendance changedAttendance = crew.changeAttendance(changeDate, time);

        outputView.printChangeLog(ChangeAttendanceLogDTO.from(copy, changedAttendance));
    }

    private void showCrewAttendance(CrewGroup crewGroup) {
        String rawName = inputView.insertNickname();
        Crew crew = crewGroup.searchCrew(rawName);

        CrewAttendancesDTO crewAttendancesDTO = CrewAttendancesDTO.from(crew);

        outputView.printAttendancesLog(crewAttendancesDTO);
    }

    private void showAlertCrews(CrewGroup crewGroup) {
        List<AlertCrewDTO> alertCrewDTDs = crewGroup.getAllAttendanceAlertLevel()
                .stream()
                .map(AlertCrewDTO::from)
                .toList();

        AlertCrewsDTO alertCrewsDTO = AlertCrewsDTO.from(alertCrewDTDs);

        outputView.printAlertCrews(alertCrewsDTO);
    }
}
