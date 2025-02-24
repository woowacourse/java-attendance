package controller;

import static domain.AttendanceStatus.ABSENCE;
import static domain.AttendanceStatus.ATTENDANCE;
import static domain.AttendanceStatus.LATENESS;
import static util.constant.Value.NOW_DAY;
import static util.constant.Value.NOW_MONTH;
import static util.constant.Value.NOW_YEAR;

import domain.Crews;
import domain.AttendanceStatistics;
import domain.Penalty;
import domain.Crew;
import domain.StatisticsResult;
import domain.TimeAndStatus;
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
            function = inputView.printFunction(currentDate);
            validateFunctions(function, functions.keySet());
            functions.getOrDefault(function, () -> {
            }).run();
        }
    }

    private void attendCrew() {
        handleException(() -> {
            String name = inputView.readName();
            InputValidator.checkNull(name);

            String time = inputView.readTime();
            InputValidator.checkNull(time);

            LocalDate today = DateTimeParser.parseIntegerToDate(NOW_YEAR, NOW_MONTH, NOW_DAY);
            LocalTime attendedTime = DateTimeParser.parseStringToTime(time);
            LocalDateTime dateTime = LocalDateTime.of(today, attendedTime);

            crews.attendCrew(name, dateTime);
            TimeAndStatus timeAndStatus = crews.findByName(name).findByDate(today);
            outputView.printAttendanceRecord(today, timeAndStatus);
        });
    }

    private void editCrewRecord() {
        handleException(() -> {
            String name = inputView.readEditName();
            InputValidator.checkNull(name);

            String dayOfMonth = inputView.readEditDayOfMonth();
            InputValidator.checkNull(dayOfMonth);
            InputValidator.checkInteger(dayOfMonth);

            String time = inputView.readEditTime();
            InputValidator.checkNull(time);

            LocalDate editedDate = DateTimeParser
                .parseIntegerToDate(NOW_YEAR, NOW_MONTH, Integer.parseInt(dayOfMonth));
            LocalTime attendedTime = DateTimeParser.parseStringToTime(time);
            LocalDateTime dateTime = LocalDateTime.of(editedDate, attendedTime);

            TimeAndStatus oldStatus = crews.editCrew(name, dateTime);
            TimeAndStatus newStatus = crews.findByName(name).findByDate(editedDate);
            outputView.printEditResult(editedDate, oldStatus, newStatus);
        });
    }

    private void checkCrewRecords() {
        handleException(() -> {
            String name = inputView.readName();
            InputValidator.checkNull(name);

            LocalDate currentDate = DateTimeParser.parseIntegerToDate(NOW_YEAR, NOW_MONTH, NOW_DAY);
            Crew crew = crews.findByName(name);

            StatisticsResult statistics = AttendanceStatistics.countStatus(currentDate, crew);
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
