package attendance.controller;

import attendance.domain.Attendance;
import attendance.domain.AttendanceType;
import attendance.domain.Attendances;
import attendance.domain.Crew;
import attendance.domain.CrewStatistic;
import attendance.domain.Crews;
import attendance.domain.MenuCommand;
import attendance.util.FileReader;
import attendance.view.InputView;
import attendance.view.OutputView;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class AttendanceController {
    private final InputView inputView;
    private final OutputView outputView;
    private final Crews crews;
    private final Attendances attendances;

    public AttendanceController() {
        this.inputView = new InputView();
        this.outputView = new OutputView();
        this.crews = new Crews();
        this.attendances = new Attendances();
    }

    public void run() {
        initDataFromCSV();

        MenuCommand command = null;
        while (!MenuCommand.QUIT.equals(command)) {
            command = MenuCommand.toCommand(getMenuOption());
            executeCommand(command);
        }
    }

    private void initDataFromCSV() {
        FileReader reader = new FileReader();
        List<List<String>> attendanceRecords = reader.readResource("attendances.csv");

        crews.initCrews(attendanceRecords);
        attendances.initAttendances(crews, attendanceRecords);

    }

    private String getMenuOption() {
        LocalDate currentDate = LocalDate.now();

        String month = String.valueOf(currentDate.getMonthValue());
        String day = String.valueOf(currentDate.getDayOfMonth());
        String dayOfWeek = currentDate.getDayOfWeek().getDisplayName(TextStyle.NARROW, Locale.KOREAN);

        return inputView.readCommand(month, day, dayOfWeek);
    }

    private void executeCommand(final MenuCommand command) {
        if (command.equals(MenuCommand.ATTEND)) {
            checkCrewAttendance();
        }
        if (command.equals(MenuCommand.MODIFY)) {
            modifyCrewAttendance();
        }
        if (command.equals(MenuCommand.LOOKUP)) {
            lookupCrewAttendanceHistory();
        }
        if (command.equals(MenuCommand.EXPEL)) {
            lookupCrewsExpelStatus();
        }
    }

    private void checkCrewAttendance() {
        String crewName = inputView.readCrewName();
        Crew crew = crews.findCrew(crewName);

        String presentTime = inputView.readPresentTime();
        validateTimeFormat(presentTime);

        LocalTime localTime = LocalTime.parse(presentTime);
        LocalDate localDate = LocalDate.now();
        LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);

        AttendanceType status = AttendanceType.of(localDateTime);
        Attendance todayAttendance = new Attendance(crew, localDateTime, status);
        attendances.add(todayAttendance);
        outputView.printTodayAttendance(todayAttendance.getInfo());
    }

    private void validateTimeFormat(final String presentTime) {
        final String TIME_PATTERN = "(2[0-3]|[01][0-9]):[0-5][0-9]";
        if (!presentTime.matches(TIME_PATTERN)) {
            throw new IllegalArgumentException(("[ERROR] 올바르지 않은 시간 형식을 입력했습니다."));
        }
    }

    private void modifyCrewAttendance() {
        String crewName = inputView.readCrewName();
        Crew crew = crews.findCrew(crewName);

        String date = inputView.readModifyDate();
        validateDateFormat(date);
        LocalDate localDate = LocalDate.of(2025, 2, Integer.parseInt(date));

        String originalTime = attendances.findOriginalTime(crew, localDate);
        AttendanceType originalType = attendances.findOriginalType(crew, localDate);

        String modifyTime = inputView.readModifyTime();
        LocalTime localTime = LocalTime.parse(modifyTime);
        LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);

        attendances.modifyAttendances(crew, localDateTime);
        Attendance newAttendance = attendances.findMatchCrewDate(crew, localDate);

        outputView.printModifiedAttendance(originalTime, originalType.toString(), newAttendance.getInfo());
    }

    private void validateDateFormat(final String date) {
        final String DATE_PATTERN = "^([1-2][0-8])|([1-9])$";

        if (!date.matches(DATE_PATTERN)) {
            throw new IllegalArgumentException("[ERROR] 올바른 형식의 날짜가 아닙니다.");
        }
    }

    private void lookupCrewAttendanceHistory() {
        String crewName = inputView.readCrewName();
        Crew crew = crews.findCrew(crewName);

        List<Attendance> crewAttendances = attendances.findCrewAttendances(crew);
        CrewStatistic crewStatistic = new CrewStatistic(crew, crewAttendances);

        crewStatistic.initCrewStatus();
        crewStatistic.calculatePenalty();

        outputView.printCrewAttendanceHistory(crew.getName(), crewStatistic.getCrewAttendanceHistory());
        outputView.printCrewStatisticStatus(crewStatistic.getCrewStatisticStatus());
    }


    private void lookupCrewsExpelStatus() {
        List<CrewStatistic> crewStatistics = new ArrayList<>();

        for (Crew crew : crews.getCrews()) {
            List<Attendance> crewAttendances = attendances.findCrewAttendances(crew);
            CrewStatistic crewStatistic = new CrewStatistic(crew, crewAttendances);

            crewStatistic.initCrewStatus();
            crewStatistic.calculatePenalty();
            crewStatistics.add(crewStatistic);
        }

        List<CrewStatistic> sortedCrewStatistics = getSortedCrewStatistics(crewStatistics);
        printExpelCrew(sortedCrewStatistics);
    }

    private static List<CrewStatistic> getSortedCrewStatistics(final List<CrewStatistic> crewStatistics) {
        return crewStatistics.stream()
                .sorted(Comparator.comparing(CrewStatistic::getPenaltyCount)
                        .reversed()
                        .thenComparing(CrewStatistic::getCrewName))
                .toList();
    }

    private void printExpelCrew(final List<CrewStatistic> sortedCrewStatistics) {
        outputView.printExpelCrewHead();
        for (CrewStatistic sortedCrewStatistic : sortedCrewStatistics) {
            outputView.printExpelCrew(sortedCrewStatistic.crewExpelExpectedInfo());
        }
        outputView.printNewLine();
    }
}
