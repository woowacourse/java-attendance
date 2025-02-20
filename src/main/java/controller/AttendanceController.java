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
import java.util.Map;
import view.InputView;
import view.OutputView;

public class AttendanceController {

    private final String DEFAULT_YEAR = "2024";
    private final String DEFAULT_MONTH = "12";
    private final String DEFAULT_DAY = "13";
    private final InputView inputView;
    private final OutputView outputView;
    private final AttendanceManager attendanceManager;

    public AttendanceController(InputView inputView, OutputView outputView,
        AttendanceManager attendanceManager) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.attendanceManager = attendanceManager;
    }

    public void run() {
        String date = DEFAULT_YEAR + "-" + DEFAULT_MONTH + "-" + DEFAULT_DAY;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(date, formatter);

        while (true) {
            String functionNumber = inputView.printFunction(localDate);

            if (functionNumber.equals("1")) {
                attend();
            }
            if (functionNumber.equals("2")) {
                edit();
            }
            if (functionNumber.equals("3")) {
                check();
            }
            if (functionNumber.equals("4")) {
                checkExpelledWarning();
            }
            if (functionNumber.equalsIgnoreCase("q")) {
                break;
            }
        }
    }

    private void attend() {
        String name = inputView.readName();
        String time = inputView.readTime();
        String date = DEFAULT_YEAR + "-" + DEFAULT_MONTH + "-" + DEFAULT_DAY;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(date + " " + time, formatter);

        try {
            TimeAndStatus timeAndStatus = attendanceManager.attendCrew(name, dateTime);
            outputView.printAttendanceRecord(dateTime.toLocalDate(), timeAndStatus);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void edit() {
        String name = inputView.readEditName();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String dayOfMonth = inputView.readEditDayOfMonth();
        String time = inputView.readTime();
        LocalDateTime localDateTime = LocalDateTime.parse(
            DEFAULT_YEAR + "-" + DEFAULT_MONTH + "-" + dayOfMonth + " " + time, formatter);
        LocalDate localDate = localDateTime.toLocalDate();
        TimeAndStatus oldTimeAndStatus = attendanceManager.findByName(name).findByDate(localDate);

        try {
            TimeAndStatus newTimeAndStatus = attendanceManager.editCrew(name, localDateTime);
            outputView.printEditResult(localDate, oldTimeAndStatus, newTimeAndStatus);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void check() {
        String name = inputView.readName();
        LocalDate localDate = calculateTodayDate();
        Records records = attendanceManager.findByName(name);
        StatisticsResult statisticsResult = AttendanceStatistics.countStatus(localDate, records);
        int attendanceCount = statisticsResult.getAttendanceCount();
        int latenessCount = statisticsResult.getLatenessCount();
        int absenceCount = statisticsResult.getAbsenceCount();
        Penalty penaltyResult = statisticsResult.getPenalty();
        outputView.printRecords(name, localDate, records);
        outputView.printStatistics(attendanceCount, latenessCount, absenceCount, penaltyResult);
    }

    private void checkExpelledWarning() {
        LocalDate localDate = calculateTodayDate();

        Map<String, StatisticsResult> sortedResult = attendanceManager.sortCrew(localDate);
        outputView.printExpelledWarningResult(sortedResult);
    }

    private LocalDate calculateTodayDate() {
        String date = DEFAULT_YEAR + "-" + DEFAULT_MONTH + "-" + DEFAULT_DAY;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(date, formatter);
    }
}
