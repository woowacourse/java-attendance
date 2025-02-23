package controller;

import domain.Attendance;
import domain.Crew;
import domain.CrewGroup;
import util.Day;
import domain.Function;
import domain.Time;
import java.time.LocalDateTime;
import java.util.List;
import service.CrewLoader;
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
        LocalDateTime today = LocalDateTime.of(2024, 12, 7, 10, 0);
        CrewLoader crewLoader = new CrewLoader();
        CrewGroup crewGroup = crewLoader.loadCrews(today);
        try {
            while (true) {
                String rawFunction = inputView.insertFunction(today);
                Function function = new Function(rawFunction);
                runCycle(function, crewGroup, today);
                if (function.equals("Q")) {
                    return;
                }
            }
        } catch (Exception e) {
            outputView.printError(e.getMessage());
        }
    }

    private void runCycle(Function function, CrewGroup crewGroup, LocalDateTime today) {
        if (function.equals("1")) {
            attendanceCheck(crewGroup, today);
        }
        if (function.equals("2")) {
            changeAttendance(crewGroup);
        }
        if (function.equals("3")) {
            showCrewAttendance(crewGroup);
        }
        if (function.equals("4")) {
            showAlertCrews(crewGroup);
        }
    }

    private void attendanceCheck(CrewGroup crewGroup, LocalDateTime today) {
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
