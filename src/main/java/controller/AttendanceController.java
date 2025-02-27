package controller;

import static util.Day.validateDay;
import static view.UserCommandType.ALERT_CREW_CHECK;
import static view.UserCommandType.ATTENDANCE_CHANGE;
import static view.UserCommandType.ATTENDANCE_CHECK;
import static view.UserCommandType.ATTENDANCE_SHOW;
import static view.UserCommandType.QUIT;
import static view.UserCommandType.getUserCommand;

import domain.Attendance;
import domain.AttendanceStatistics;
import domain.Crew;
import domain.CrewGroup;
import domain.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import service.CrewLoader;
import util.Day;
import view.InputView;
import view.OutputView;
import view.UserCommandType;
import view.dto.AlertCrewDTO;
import view.dto.AlertCrewsDTO;
import view.dto.AttendanceLogDTO;
import view.dto.ChangeAttendanceLogDTO;
import view.dto.CrewAttendancesDTO;

public class AttendanceController {
    private final InputView inputView;
    private final OutputView outputView;

    public AttendanceController(InputView inputView, OutputView outputView) {
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
                UserCommandType userCommandType = getUserCommand(rawFunction);
                if (userCommandType.equals(QUIT)) {
                    break;
                }

                runCycle(userCommandType, crewGroup, today);
            }
        } catch (Exception e) {
            outputView.printError(e.getMessage());
        }
    }

    private void runCycle(UserCommandType userCommandType, CrewGroup crewGroup, LocalDateTime today) {
        if (userCommandType.equals(ATTENDANCE_CHECK)) {
            checkAttendance(crewGroup, today);
            return;
        }
        if (userCommandType.equals(ATTENDANCE_CHANGE)) {
            changeAttendance(crewGroup, today);
            return;
        }
        if (userCommandType.equals(ATTENDANCE_SHOW)) {
            showCrewAttendance(crewGroup);
            return;
        }
        if (userCommandType.equals(ALERT_CREW_CHECK)) {
            showAlertCrews(crewGroup);
        }
    }

    private void checkAttendance(CrewGroup crewGroup, LocalDateTime today) {
        validateDay(today.getDayOfMonth(), today);
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

    private void changeAttendance(CrewGroup crewGroup, LocalDateTime today) {
        String rawName = inputView.insertChangeDateNickname();
        Crew crew = crewGroup.searchCrew(rawName);

        int changeDate = inputView.insertChangeDate();

        validateDay(changeDate, today);
        String rawTime = inputView.insertChangeTime();
        Time time = new Time(rawTime);

        LocalDate changeLocalDate = Day.toLocalDate(changeDate, today);

        Attendance originalAttendance = crew.getSpecificAttendance(changeLocalDate);
        Attendance copy = new Attendance(originalAttendance.getDate());
        Attendance changedAttendance = crew.changeAttendance(changeLocalDate, time);

        outputView.printChangeLog(ChangeAttendanceLogDTO.from(copy, changedAttendance));
    }

    private void showCrewAttendance(CrewGroup crewGroup) {
        String rawName = inputView.insertNickname();
        Crew crew = crewGroup.searchCrew(rawName);
        AttendanceStatistics crewAttendanceStatistics = new AttendanceStatistics();
        crewAttendanceStatistics.updateStatus(crew.getAttendances());

        CrewAttendancesDTO crewAttendancesDTO = CrewAttendancesDTO.from(crew, crewAttendanceStatistics);

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
