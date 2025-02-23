package controller;

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
import view.InputView;
import view.OutputView;

public class AttendanceController {

    private static final int ATTENDANCE_YEAR = 2024;
    private static final int ATTENDANCE_MONTH = 12;
    private static final int ATTENDANCE_DAY_OF_MONTH = 13;
    private static final String INPUT_DATE_FORMAT = "%04d-%02d-%02d";
    private static final String INPUT_TIME_FORMAT = "%02d:%02d";
    private static final String TIME_DELIMITER = ":";

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private final InputView inputView;
    private final OutputView outputView;
    private final AttendanceManager attendanceManager;

    public AttendanceController(InputView inputView, OutputView outputView, AttendanceManager attendanceManager) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.attendanceManager = attendanceManager;
    }

    public void run() {
        LocalDate nowDate = formatNowDate();
        String functionNumber = "";

        do {
            functionNumber = inputView.printFunction(nowDate);
            switch (functionNumber) {
                case "1" -> attend();
                case "2" -> edit();
                case "3" -> check();
                case "4" -> checkExpelledWarning();
                default -> {
                    if (!List.of("1", "2", "3", "4", "q", "Q").contains(functionNumber)) {
                        System.out.println("유효하지 않은 번호입니다.");
                    }
                }
            }
        } while (!functionNumber.equals("q"));
    }

    private void attend() {
        try {
            String name = inputView.readName();
            attendanceManager.findByName(name);
            List<String> attendTime = List.of(inputView.readTime().split(TIME_DELIMITER));
            LocalDateTime attendDateTime = formatDateTime(ATTENDANCE_DAY_OF_MONTH, attendTime);

            TimeAndStatus timeAndStatus = attendanceManager.attendCrew(name, attendDateTime);
            outputView.printAttendanceRecord(attendDateTime.toLocalDate(), timeAndStatus);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void edit() {
        try {
            String name = inputView.readEditName();
            attendanceManager.findByName(name);
            int editDayOfMonth = Integer.parseInt(inputView.readEditDayOfMonth());
            List<String> editTime = List.of(inputView.readEditTime().split(TIME_DELIMITER));
            LocalDateTime editDateTime = formatDateTime(editDayOfMonth, editTime);
            LocalDate editDate = editDateTime.toLocalDate();

            TimeAndStatus oldTimeAndStatus = attendanceManager.findByName(name).findByDate(editDate);
            TimeAndStatus newTimeAndStatus = attendanceManager.editCrew(name, editDateTime);
            outputView.printEditResult(editDate, oldTimeAndStatus, newTimeAndStatus);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void check() {
        try {
            String name = inputView.readName();
            attendanceManager.findByName(name);
            LocalDate nowDate = formatNowDate();
            Records records = attendanceManager.findByName(name);
            StatisticsResult statisticsResult = AttendanceStatistics.countStatus(nowDate, records);

            outputView.printRecords(name, nowDate, records);
            outputView.printStatistics(
                    statisticsResult.getAttendanceCount(),
                    statisticsResult.getLatenessCount(),
                    statisticsResult.getAbsenceCount(),
                    statisticsResult.getPenalty()
            );
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void checkExpelledWarning() {
        LocalDate nowDate = formatNowDate();

        Map<String, StatisticsResult> sortedResult = attendanceManager.sortCrew(nowDate);
        outputView.printExpelledWarningResult(sortedResult);
    }

    private LocalDate formatNowDate() {
        String dateForm = String.format(INPUT_DATE_FORMAT, ATTENDANCE_YEAR, ATTENDANCE_MONTH, ATTENDANCE_DAY_OF_MONTH);
        return LocalDate.parse(dateForm, DATE_FORMATTER);
    }

    private LocalDateTime formatDateTime(int dayOfMonth, List<String> time) {
        String dateForm = String.format(INPUT_DATE_FORMAT, ATTENDANCE_YEAR, ATTENDANCE_MONTH, dayOfMonth);
        String timeForm = String.format(INPUT_TIME_FORMAT, Integer.parseInt(time.get(0)),
                Integer.parseInt(time.get(1)));
        return LocalDateTime.parse(dateForm + " " + timeForm, DATE_TIME_FORMATTER);
    }
}
