package controller;

import domain.AttendanceManager;
import domain.AttendanceStatistics;
import domain.Function;
import domain.Records;
import domain.StatisticsResult;
import domain.TimeAndStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Map;
import view.InputView;
import view.OutputView;

public class AttendanceController {

    private static final int ATTENDANCE_YEAR = 2024;
    private static final int ATTENDANCE_MONTH = 12;
    private static final int ATTENDANCE_DAY_OF_MONTH = 13;
    private static final String INPUT_DATE_FORMAT = "%04d-%02d-%02d";

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    private final InputView inputView;
    private final OutputView outputView;
    private final AttendanceManager attendanceManager;

    public AttendanceController(InputView inputView, OutputView outputView, AttendanceManager attendanceManager) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.attendanceManager = attendanceManager;
    }

    public void run() {
        try {
            processUserCommand();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (DateTimeParseException e) {
            System.out.println("시간 형식이 유효하지 않습니다.");
        }
    }

    private void processUserCommand() {
        LocalDate nowDate = parseDate(ATTENDANCE_DAY_OF_MONTH);
        String functionNumber;
        do {
            functionNumber = inputView.printFunction(nowDate);
            Function function = Function.from(functionNumber);
            executeFunction(function, nowDate);
        } while (Function.from(functionNumber) != Function.QUIT);
    }

    private void executeFunction(Function function, LocalDate nowDate) {
        switch (function) {
            case ATTEND -> attend(nowDate);
            case EDIT -> edit();
            case CHECK -> check(nowDate);
            case CHECK_EXPELLED_WARNING -> checkExpelledWarning(nowDate);
        }
    }

    private void attend(LocalDate nowDate) {
        String name = inputView.readAttendName();
        attendanceManager.hasCrew(name);

        String attendTime = inputView.readTime();
        LocalTime parsedTime = parseTime(attendTime);
        LocalDateTime attendDateTime = LocalDateTime.of(nowDate, parsedTime);

        TimeAndStatus timeAndStatus = attendanceManager.attendCrew(name, attendDateTime);
        outputView.printAttendanceRecord(attendDateTime.toLocalDate(), timeAndStatus);
    }

    private void edit() {
        String name = inputView.readEditName();
        attendanceManager.hasCrew(name);
        int editDayOfMonth = Integer.parseInt(inputView.readEditDayOfMonth());

        String editTime = inputView.readEditTime();
        LocalTime parsedTime = parseTime(editTime);
        LocalDate editDate = parseDate(editDayOfMonth);
        LocalDateTime editDateTime = LocalDateTime.of(editDate, parsedTime);

        TimeAndStatus oldTimeAndStatus = attendanceManager.findByName(name).findByDate(editDate);
        TimeAndStatus newTimeAndStatus = attendanceManager.editCrew(name, editDateTime);
        outputView.printEditResult(editDate, oldTimeAndStatus, newTimeAndStatus);
    }

    private void check(LocalDate nowDate) {
        String name = inputView.readAttendName();
        Records records = attendanceManager.findByName(name);
        StatisticsResult statisticsResult = AttendanceStatistics.countStatus(nowDate, records);

        outputView.printRecords(name, nowDate, records);
        outputView.printStatistics(
                statisticsResult.getAttendanceCount(),
                statisticsResult.getLatenessCount(),
                statisticsResult.getAbsenceCount(),
                statisticsResult.getPenalty()
        );
    }

    private void checkExpelledWarning(LocalDate nowDate) {
        Map<String, StatisticsResult> sortedResult = attendanceManager.sortCrew(nowDate);
        outputView.printExpelledWarningResult(sortedResult);
    }

    private LocalTime parseTime(String time) {
        return LocalTime.parse(time, TIME_FORMATTER);
    }

    private LocalDate parseDate(int dayOfMonth) {
        String dateForm = String.format(INPUT_DATE_FORMAT, ATTENDANCE_YEAR, ATTENDANCE_MONTH, dayOfMonth);
        return LocalDate.parse(dateForm, DATE_FORMATTER);
    }
}
