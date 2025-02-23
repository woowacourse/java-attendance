package controller;

import static util.constant.Value.NOW_DAY;
import static util.constant.Value.NOW_MONTH;
import static util.constant.Value.NOW_YEAR;

import domain.AttendanceManager;
import domain.AttendanceStatistics;
import domain.Penalty;
import domain.Records;
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
        String name = inputView.readName();
        InputValidator.checkNull(name);

        String time = inputView.readTime();
        InputValidator.checkNull(time);

        LocalDate currentDate = DateTimeParser.parseIntegerToDate(NOW_YEAR, NOW_MONTH, NOW_DAY);
        LocalTime attendedTime = DateTimeParser.parseStringToTime(time);
        LocalDateTime dateTime = LocalDateTime.of(currentDate, attendedTime);

        TimeAndStatus timeAndStatus = attendanceManager.attendCrew(name, dateTime);
        outputView.printAttendanceRecord(currentDate, timeAndStatus);
    }

    private void editCrewRecord() {
        String name = inputView.readEditName();
        String dayOfMonth = inputView.readEditDayOfMonth();
        String time = inputView.readEditTime();

        LocalDate editedDate = DateTimeParser.parseIntegerToDate(NOW_YEAR, NOW_MONTH, Integer.parseInt(dayOfMonth));
        LocalTime attendedTime = DateTimeParser.parseStringToTime(time);
        LocalDateTime dateTime = LocalDateTime.of(editedDate, attendedTime);

        TimeAndStatus oldStatus = attendanceManager.findByName(name).findByDate(editedDate);
        TimeAndStatus newStatus = attendanceManager.editCrew(name, dateTime);
        outputView.printEditResult(editedDate, oldStatus, newStatus);
    }

    private void checkCrewRecords() {
        String name = inputView.readName();
        LocalDate currentDate = DateTimeParser.parseIntegerToDate(NOW_YEAR, NOW_MONTH, NOW_DAY);
        Records records = attendanceManager.findByName(name);

        StatisticsResult statistics = AttendanceStatistics.countStatus(currentDate, records);
        int attendanceCount = statistics.getAttendanceCount();
        int latenessCount = statistics.getLatenessCount();
        int absenceCount = statistics.getAbsenceCount();
        Penalty penaltyResult = statistics.getPenalty();

        outputView.printRecords(name, currentDate, records);
        outputView.printStatistics(attendanceCount, latenessCount, absenceCount, penaltyResult);
    }

    private void checkExpelledWarningCrews() {
        LocalDate currentDate = DateTimeParser.parseIntegerToDate(NOW_YEAR, NOW_MONTH, NOW_DAY);

        Map<String, StatisticsResult> sortedResult = attendanceManager.sortCrew(currentDate);
        outputView.printExpelledWarningResult(sortedResult);
    }

    private void validateFunctions(String functionNumber, Set<String> functions) {
        try {
            InputValidator.checkFunctions(functionNumber, functions);
        } catch (IllegalArgumentException e) {
            outputView.printErrorMessage(e);
        }
    }
}
