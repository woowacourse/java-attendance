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
import util.DateValidator;
import view.InputView;
import view.OutputView;
import view.dto.AlertCrewDto;
import view.dto.AlertCrewsDto;
import view.dto.AttendanceLogDto;
import view.dto.ChangeAttendanceLogDto;
import view.dto.CrewAttendancesDto;

public class Controller {
    private final InputView inputView;
    private final OutputView outputView;

    public Controller(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        LocalDateTime today = LocalDateTime.of(2024, 12, 18, 10, 0);
        CrewLoader crewLoader = new CrewLoader();
        CrewGroup crewGroup = crewLoader.loadCrews(today);

        try {
            runCycle(today, crewGroup);
        } catch (Exception e) {
            outputView.printError(e.getMessage());
        }
    }

    private void runCycle(LocalDateTime today, CrewGroup crewGroup) {
        while (true) {
            String option = inputView.insertMenuOption(today);
            if (isExit(option)) {
                return;
            }
            operateMenuOption(option, crewGroup, today);
        }
    }

    private void operateMenuOption(String option, CrewGroup crewGroup, LocalDateTime today) {
        MenuOption menuOption = getMenuOption(option);
        if (menuOption.equals(CHECK_ATTENDANCE)) {
            attendanceCheck(crewGroup, today);
        }
        if (menuOption.equals(CHANGE_ATTENDANCE)) {
            changeAttendance(crewGroup, today);
        }
        if (menuOption.equals(SHOW_CREW_ATTENDANCES)) {
            showCrewAttendance(crewGroup);
        }
        if (menuOption.equals(SHOW_ALERT_CREW)) {
            showAlertCrews(crewGroup);
        }
    }

    private void attendanceCheck(CrewGroup crewGroup, LocalDateTime today) {
        DateValidator.validateAttendanceCheckDate(today);

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

    private void changeAttendance(CrewGroup crewGroup, LocalDateTime today) {
        String rawName = inputView.insertChangeDateNickname();
        Crew crew = crewGroup.searchCrew(rawName);

        int date = inputView.insertChangeDate();
        DateValidator.validateAttendanceChangeDate(date, today);

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
