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
    private static final String WEEKEND_ERROR_MESSAGE = "[ERROR] 주말에는 출석을 할 수 없습니다.";
    private static final String TIME_FORMAT_ERROR_MESSAGE = "[ERROR] 올바르지 않은 시간 형식을 입력했습니다.";
    private static final String DATE_FORMAT_ERROR_MESSAGE = "[ERROR] 올바른 날짜 형식을 입력해주세요.";

    private static final String FILE_NAME = "attendances.csv";

    private static final String TIME_PATTERN = "(2[0-3]|[01][0-9]):[0-5][0-9]";
    private static final String DATE_PATTERN = "^([1-2][0-8])|([1-9])$";

    private static final int YEAR_VALUE = 2025;
    private static final int MONTH_VALUE = 2;
    private static final int WEEKEND_VALUE = 5;

    private static final LocalDate LOCAL_DATE_TODAY = LocalDate.now();

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
        do {
            command = repeatMenuCommand(command);
        } while (executeMenuCommand(command));
    }

    private void initDataFromCSV() {
        FileReader reader = new FileReader();
        List<List<String>> attendanceRecords = reader.readResource(FILE_NAME);

        crews.initCrews(attendanceRecords);
        attendances.initAttendances(crews, attendanceRecords);
    }

    private MenuCommand repeatMenuCommand(final MenuCommand command) {
        try {
            return MenuCommand.toCommand(getMenuOption());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        return command;
    }

    private String getMenuOption() {
        String month = String.valueOf(LOCAL_DATE_TODAY.getMonthValue());
        String day = String.valueOf(LOCAL_DATE_TODAY.getDayOfMonth());
        String dayOfWeek = LOCAL_DATE_TODAY.getDayOfWeek().getDisplayName(TextStyle.NARROW, Locale.KOREAN);

        return inputView.readCommand(month, day, dayOfWeek);
    }

    private boolean executeMenuCommand(final MenuCommand command) {
        if (command == null) {
            return true;
        }
        if (command.equals(MenuCommand.QUIT)) {
            return false;
        }
        executeCommandOptions(command);
        return true;
    }

    private void executeCommandOptions(final MenuCommand command) {
        if (command.equals(MenuCommand.ATTEND)) {
            checkCrewAttendance();
            return;
        }
        if (command.equals(MenuCommand.MODIFY)) {
            modifyCrewAttendance();
            return;
        }
        if (command.equals(MenuCommand.LOOKUP)) {
            lookupCrewAttendanceHistory();
            return;
        }
        if (command.equals(MenuCommand.EXPEL)) {
            lookupCrewsExpelStatus();
        }
    }

    private Crew findCrewByCrewName() {
        String crewName = inputView.readCrewName();
        return crews.findCrew(crewName);
    }

    private void validateWeekend() {
        if (LOCAL_DATE_TODAY.getDayOfWeek().getValue() > WEEKEND_VALUE) {
            throw new IllegalArgumentException(WEEKEND_ERROR_MESSAGE);
        }
    }

    private LocalDateTime createLocalDateTime(final LocalDate localDate, final String localTime) {
        return LocalDateTime.of(localDate, LocalTime.parse(localTime));
    }

    private void checkCrewAttendance() {
        validateWeekend();
        Crew crew = findCrewByCrewName();
        attendances.hasCheckedAttendance(crew, LOCAL_DATE_TODAY);

        LocalDateTime localDateTime = validatePresentTime();
        AttendanceType status = AttendanceType.of(localDateTime);
        Attendance todayAttendance = new Attendance(crew, localDateTime, status);
        attendances.add(todayAttendance);

        outputView.printTodayAttendance(todayAttendance.getInfo());
    }

    private LocalDateTime validatePresentTime() {
        String presentTime = inputView.readPresentTime();
        validateTimeFormat(presentTime);
        return createLocalDateTime(LOCAL_DATE_TODAY, presentTime);
    }

    private void validateTimeFormat(final String presentTime) {
        if (!presentTime.matches(TIME_PATTERN)) {
            throw new IllegalArgumentException((TIME_FORMAT_ERROR_MESSAGE));
        }
    }

    private void modifyCrewAttendance() {
        Crew crew = findCrewByCrewName();
        LocalDate localDate = validateModifyDate();
        String originalTime = attendances.findOriginalTime(crew, localDate);
        AttendanceType originalType = attendances.findOriginalType(crew, localDate);

        String modifyTime = inputView.readModifyTime();
        LocalDateTime localDateTime = createLocalDateTime(localDate, modifyTime);
        attendances.modifyAttendances(crew, localDateTime);
        Attendance newAttendance = attendances.findMatchCrewDate(crew, localDate);
        outputView.printModifiedAttendance(originalTime, originalType.toString(), newAttendance.getInfo());
    }

    private void validateDateFormat(final String date) {
        if (!date.matches(DATE_PATTERN)) {
            throw new IllegalArgumentException(DATE_FORMAT_ERROR_MESSAGE);
        }
    }

    private LocalDate validateModifyDate() {
        String date = inputView.readModifyDate();
        validateDateFormat(date);
        return LocalDate.of(YEAR_VALUE, MONTH_VALUE, Integer.parseInt(date));
    }

    private void lookupCrewAttendanceHistory() {
        Crew crew = findCrewByCrewName();
        List<Attendance> crewAttendances = attendances.findCrewAttendances(crew);
        CrewStatistic crewStatistic = new CrewStatistic(crew, crewAttendances);

        crewStatistic.resetCrewStatus();
        crewStatistic.initCrewsStatus();
        crewStatistic.calculatePenalty();

        outputView.printCrewAttendanceHistory(crew.getName(), crewStatistic.crewAttendanceHistoryInfo());
        outputView.printCrewStatisticStatus(crewStatistic.crewStatisticStatusInfo());
    }

    private void lookupCrewsExpelStatus() {
        List<CrewStatistic> crewStatistics = new ArrayList<>();

        for (Crew crew : crews.getCrews()) {
            List<Attendance> crewAttendances = attendances.findCrewAttendances(crew);
            CrewStatistic crewStatistic = new CrewStatistic(crew, crewAttendances);
            checkCrewStatistic(crewStatistic);
            crewStatistics.add(crewStatistic);
        }
        List<CrewStatistic> sortedCrewStatistics = sortCrewStatistics(crewStatistics);
        printExpelCrew(sortedCrewStatistics);
    }

    private void checkCrewStatistic(CrewStatistic crewStatistic) {
        crewStatistic.resetCrewStatus();
        crewStatistic.initCrewsStatus();
        crewStatistic.calculatePenalty();
    }

    private List<CrewStatistic> sortCrewStatistics(final List<CrewStatistic> crewStatistics) {
        return crewStatistics.stream()
                .sorted(Comparator.comparing(CrewStatistic::getPenaltyCount)
                        .reversed()
                        .thenComparing(CrewStatistic::getCrewName))
                .toList();
    }

    private void printExpelCrew(final List<CrewStatistic> sortedCrewStatistics) {
        outputView.printExpelCrewHead();
        for (CrewStatistic sortedCrewStatistic : sortedCrewStatistics) {
            outputView.printExpelCrewBody(sortedCrewStatistic.crewExpelExpectedInfo());
        }
        outputView.printNewLine();
    }
}
