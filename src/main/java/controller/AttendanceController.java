package controller;

import static util.constant.Value.DATE_FORMAT;
import static util.constant.Value.NOW_DAY;
import static util.constant.Value.NOW_MONTH;
import static util.constant.Value.NOW_YEAR;
import static util.constant.Value.TIME_FORMAT;

import domain.AttendanceManager;
import domain.AttendanceStatistics;
import domain.Penalty;
import domain.Records;
import domain.StatisticsResult;
import domain.TimeAndStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Set;
import util.parser.DateTimeParser;
import util.validator.InputValidator;
import view.InputView;
import view.OutputView;

public class AttendanceController {

    private final InputView inputView;
    private final OutputView outputView;
    private final AttendanceManager attendanceManager;

    public AttendanceController(AttendanceManager attendanceManager) {
        this.inputView = new InputView();
        this.outputView = new OutputView();
        this.attendanceManager = attendanceManager;
    }

    public void run() {
        LocalDate currentDate = DateTimeParser.parseIntegerToDate(NOW_YEAR, NOW_MONTH, NOW_DAY);
        Map<String, Runnable> functions = Map.of(
            "1", this::attendCrew,
            "2", this::editCrewRecord,
            "3", this::checkCrewRecords,
            "4", this::checkExpelledWarningCrews
        );

        String functionNumber = "";
        while (!functionNumber.equalsIgnoreCase("Q")) {
            functionNumber = inputView.printFunction(currentDate);
            validateFunctions(functionNumber, functions.keySet());
            functions.getOrDefault(functionNumber, () -> {}).run();
        }
    }

    private void attendCrew() {
        try {
            String name = inputView.readName();
            attendanceManager.findByName(name);
            List<String> time = List.of(inputView.readTime().split(":"));

            String dateForm = String.format(DATE_FORMAT, NOW_YEAR, NOW_MONTH,
                NOW_DAY);
            String timeForm = formatTime(time);
            LocalDateTime dateTime = formatDateTime(dateForm, timeForm);

            TimeAndStatus timeAndStatus = attendanceManager.attendCrew(name, dateTime);
            outputView.printAttendanceRecord(dateTime.toLocalDate(), timeAndStatus);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void editCrewRecord() {
        try {
            String name = inputView.readEditName();
            attendanceManager.findByName(name);
            String dayOfMonth = inputView.readEditDayOfMonth();
            List<String> time = List.of(inputView.readEditTime().split(":"));

            String dateForm = String.format(DATE_FORMAT, NOW_YEAR, NOW_MONTH,
                Integer.parseInt(dayOfMonth));
            String timeForm = formatTime(time);
            LocalDateTime localDateTime = formatDateTime(dateForm, timeForm);
            LocalDate localDate = localDateTime.toLocalDate();

            TimeAndStatus oldTimeAndStatus = attendanceManager.findByName(name)
                .findByDate(localDate);
            TimeAndStatus newTimeAndStatus = attendanceManager.editCrew(name, localDateTime);
            outputView.printEditResult(localDate, oldTimeAndStatus, newTimeAndStatus);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void checkCrewRecords() {
        try {
            String name = inputView.readName();
            attendanceManager.findByName(name);

            LocalDate localDate = DateTimeParser.parseIntegerToDate(NOW_YEAR, NOW_MONTH, NOW_DAY);
            Records records = attendanceManager.findByName(name);

            StatisticsResult statisticsResult = AttendanceStatistics.countStatus(localDate,
                records);

            int attendanceCount = statisticsResult.getAttendanceCount();
            int latenessCount = statisticsResult.getLatenessCount();
            int absenceCount = statisticsResult.getAbsenceCount();
            Penalty penaltyResult = statisticsResult.getPenalty();

            outputView.printRecords(name, localDate, records);
            outputView.printStatistics(attendanceCount, latenessCount, absenceCount, penaltyResult);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void checkExpelledWarningCrews() {
        LocalDate localDate = DateTimeParser.parseIntegerToDate(NOW_YEAR, NOW_MONTH, NOW_DAY);

        Map<String, StatisticsResult> sortedResult = attendanceManager.sortCrew(localDate);
        outputView.printExpelledWarningResult(sortedResult);
    }

    private String formatTime(List<String> time) {
        return String.format(TIME_FORMAT, Integer.parseInt(time.get(0)),
            Integer.parseInt(time.get(1)));
    }

    private LocalDateTime formatDateTime(String dateForm, String timeForm) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(dateForm + " " + timeForm, formatter);
    }

    private void validateFunctions(String functionNumber, Set<String> functions) {
        try {
            InputValidator.checkFunctions(functionNumber, functions);
        } catch (IllegalArgumentException e) {
            outputView.printErrorMessage(e);
        }
    }
}
