package controller;

import static domain.AttendanceStatus.ABSENCE;
import static domain.AttendanceStatus.ATTENDANCE;
import static domain.AttendanceStatus.LATENESS;

import domain.AttendanceStatus;
import domain.Crews;
import domain.Penalty;
import domain.Crew;
import domain.StatisticsResult;
import domain.DailyRecord;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;
import java.util.Set;
import util.parser.DateTimeParser;
import util.validator.InputValidator;
import view.InputView;
import view.OutputView;

public class AttendanceController {

    public static final int NOW_YEAR = 2024;
    public static final int NOW_MONTH = 12;
    public static final int NOW_DAY = 13;

    private final InputView inputView;
    private final OutputView outputView;
    private final Crews crews;

    public AttendanceController(Crews crews) {
        this.inputView = new InputView();
        this.outputView = new OutputView();
        this.crews = crews;
    }

    public void run() {
        LocalDate currentDate = DateTimeParser.parseIntegerToDate(NOW_YEAR, NOW_MONTH, NOW_DAY);
        Map<String, Runnable> functions = Map.of(
            "1", this::attendCrew,
            "2", this::editCrewRecord,
            "3", this::checkCrewRecords,
            "4", this::checkExpelledWarningCrews
        );

        String function = "";
        while (!function.equalsIgnoreCase("Q")) {
            function = inputView.readFunction(currentDate);
            validateFunctions(function, functions.keySet());
            functions.getOrDefault(function, () -> {
            }).run();
        }
    }

    private void attendCrew() {
        handleException(() -> {
            String name = inputView.readName();
            String time = inputView.readTime();

            LocalDate today = DateTimeParser.parseIntegerToDate(NOW_YEAR, NOW_MONTH, NOW_DAY);
            LocalTime attendedTime = DateTimeParser.parseStringToTime(time);
            LocalDateTime dateTime = LocalDateTime.of(today, attendedTime);

            crews.attendCrew(name, dateTime);
            DailyRecord dailyRecord = crews.findCrewByName(name).findRecordByDate(today);
            outputView.printAttendanceRecord(today, dailyRecord);
        });
    }

    private void editCrewRecord() {
        handleException(() -> {
            String name = inputView.readEditName();
            String dayOfMonth = inputView.readEditDayOfMonth();
            String time = inputView.readEditTime();

            LocalDate editedDate = DateTimeParser
                .parseIntegerToDate(NOW_YEAR, NOW_MONTH, Integer.parseInt(dayOfMonth));
            LocalTime attendedTime = DateTimeParser.parseStringToTime(time);
            LocalDateTime dateTime = LocalDateTime.of(editedDate, attendedTime);

            DailyRecord oldStatus = crews.editCrew(name, dateTime);
            DailyRecord newStatus = crews.findCrewByName(name).findRecordByDate(editedDate);
            outputView.printEditResult(editedDate, oldStatus, newStatus);
        });
    }

    private void checkCrewRecords() {
        handleException(() -> {
            String name = inputView.readName();

            LocalDate currentDate = DateTimeParser.parseIntegerToDate(NOW_YEAR, NOW_MONTH, NOW_DAY);
            Crew crew = crews.findCrewByName(name);

            StatisticsResult statistics = AttendanceStatus.countStatus(currentDate, crew);
            int attendanceCount = statistics.getCount(ATTENDANCE);
            int latenessCount = statistics.getCount(LATENESS);
            int absenceCount = statistics.getCount(ABSENCE);
            Penalty penaltyResult = statistics.getPenalty();

            outputView.printRecords(name, currentDate, crew);
            outputView.printStatistics(attendanceCount, latenessCount, absenceCount, penaltyResult);
        });
    }

    private void checkExpelledWarningCrews() {
        handleException(() -> {
            LocalDate currentDate = DateTimeParser.parseIntegerToDate(NOW_YEAR, NOW_MONTH, NOW_DAY);

            Map<String, StatisticsResult> sortedResult = crews.findWarningCrews(currentDate);
            outputView.printExpelledWarningResult(sortedResult);
        });
    }

    private void validateFunctions(String functionNumber, Set<String> functions) {
        try {
            InputValidator.checkFunctions(functionNumber, functions);
        } catch (IllegalArgumentException e) {
            outputView.printErrorMessage(e);
        }
    }

    private void handleException(Runnable action) {
        try {
            action.run();
        } catch (IllegalArgumentException e) {
            outputView.printErrorMessage(e);
        }
    }
}
