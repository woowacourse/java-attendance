package controller;

import static domain.MenuOption.CHANGE_ATTENDANCE;
import static domain.MenuOption.CHECK_ATTENDANCE;
import static domain.MenuOption.SHOW_ALERT_CREW;
import static domain.MenuOption.SHOW_CREW_ATTENDANCES;
import static domain.MenuOption.getMenuOption;
import static domain.MenuOption.isExit;

import domain.Attendance;
import domain.Crew;
import domain.CrewGroup;
import domain.MenuOption;
import domain.Time;
import java.time.LocalDateTime;
import java.util.List;
import service.CrewLoader;
import service.DateValidator;
import view.InputView;
import view.OutputView;
import view.dto.AlertCrewDto;
import view.dto.AlertCrewsDto;
import view.dto.AttendanceLogDto;
import view.dto.ChangeAttendanceLogDto;
import view.dto.CrewAttendancesDto;

public class Controller {
    private static final LocalDateTime today = LocalDateTime.of(2024, 12, 18, 10, 0);
    private final DateValidator dateValidator;
    private final InputView inputView;
    private final OutputView outputView;

    public Controller(DateValidator dateValidator, InputView inputView, OutputView outputView) {
        this.dateValidator = dateValidator;
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        CrewLoader crewLoader = new CrewLoader();
        CrewGroup crewGroup = crewLoader.loadCrews(today);

        try {
            runCycle(crewGroup);
        } catch (Exception e) {
            outputView.printError(e.getMessage());
        }
    }

    private void runCycle(CrewGroup crewGroup) {
        while (true) {
            String option = inputView.insertMenuOption(today);
            if (isExit(option)) {
                return;
            }
            operateMenuOption(option, crewGroup);
        }
    }

    private void operateMenuOption(String option, CrewGroup crewGroup) {
        MenuOption menuOption = getMenuOption(option);
        if (menuOption.equals(CHECK_ATTENDANCE)) {
            attendanceCheck(crewGroup);
        }
        if (menuOption.equals(CHANGE_ATTENDANCE)) {
            changeAttendance(crewGroup);
        }
        if (menuOption.equals(SHOW_CREW_ATTENDANCES)) {
            showCrewAttendance(crewGroup);
        }
        if (menuOption.equals(SHOW_ALERT_CREW)) {
            showAlertCrews(crewGroup);
        }
    }

    private void attendanceCheck(CrewGroup crewGroup) {
        dateValidator.validateAttendanceCheckDate(today);

        String rawName = inputView.insertNickname();
        Crew crew = crewGroup.searchCrew(rawName);
        if (crew.isAlreadyChecked(today)) {
            outputView.printAlreadyCheckedGuide();
            return;
        }

        String rawTime = inputView.insertTime();
        Time time = new Time(rawTime);
        Attendance attendance = crew.addAttendance(
                LocalDateTime.of(today.getYear(), today.getMonth(), today.getDayOfMonth(),
                        time.getHour(), time.getMinute()));

        outputView.printAttendanceLog(AttendanceLogDto.from(attendance));
    }

    private void changeAttendance(CrewGroup crewGroup) {
        String rawName = inputView.insertChangeDateNickname();
        Crew crew = crewGroup.searchCrew(rawName);

        int date = inputView.insertChangeDate();
        dateValidator.validateAttendanceChangeDate(date, today);

        String rawTime = inputView.insertChangeTime();
        Time time = new Time(rawTime);

        Attendance originalAttendanceCopy = new Attendance(crew.getSpecificAttendance(date).getDate());
        Attendance changedAttendance = crew.changeAttendance(date, time);

        outputView.printChangeLog(ChangeAttendanceLogDto.from(originalAttendanceCopy, changedAttendance));
    }

    private void showCrewAttendance(CrewGroup crewGroup) {
        String rawName = inputView.insertNickname();
        Crew crew = crewGroup.searchCrew(rawName);

        CrewAttendancesDto crewAttendancesDTO = CrewAttendancesDto.from(crew);

        outputView.printAttendancesLog(crewAttendancesDTO);
    }

    private void showAlertCrews(CrewGroup crewGroup) {
        List<AlertCrewDto> alertCrewDTDs = crewGroup.getAllAttendanceAlertLevel()
                .stream()
                .map(AlertCrewDto::from)
                .toList();

        AlertCrewsDto alertCrewsDTO = AlertCrewsDto.from(alertCrewDTDs);

        outputView.printAlertCrews(alertCrewsDTO);
    }
}
