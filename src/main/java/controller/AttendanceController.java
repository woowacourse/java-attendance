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

    private final int DEFAULT_YEAR = 2024;
    private final int DEFAULT_MONTH = 12;
    private final int DEFAULT_DAY = 13;
    private final String INPUT_DATE_FORMAT = "%04d-%02d-%02d";
    private final String INPUT_TIME_FORMAT = "%02d:%02d";
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
        LocalDate localDate = formatDate();
        String functionNumber = "";
        do {
            functionNumber = inputView.printFunction(localDate);
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
            if (!List.of("1", "2", "3", "4", "q", "Q").contains(functionNumber)) {
                System.out.println("유효하지 않은 번호입니다.");
            }
        } while (!functionNumber.equals("q"));
    }

    private void attend() {
        try {
            String name = inputView.readName();
            attendanceManager.findByName(name);
            List<String> time = List.of(inputView.readTime().split(":"));

            String dateForm = String.format(INPUT_DATE_FORMAT, DEFAULT_YEAR, DEFAULT_MONTH,
                DEFAULT_DAY);
            String timeForm = formatTime(time);
            LocalDateTime dateTime = formatDateTime(dateForm, timeForm);

            TimeAndStatus timeAndStatus = attendanceManager.attendCrew(name, dateTime);
            outputView.printAttendanceRecord(dateTime.toLocalDate(), timeAndStatus);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void edit() {
        try {
            String name = inputView.readEditName();
            attendanceManager.findByName(name);
            String dayOfMonth = inputView.readEditDayOfMonth();
            List<String> time = List.of(inputView.readEditTime().split(":"));

            String dateForm = String.format(INPUT_DATE_FORMAT, DEFAULT_YEAR, DEFAULT_MONTH,
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

    private void check() {
        try {
            String name = inputView.readName();
            attendanceManager.findByName(name);

            LocalDate localDate = formatDate();
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

    private void checkExpelledWarning() {
        LocalDate localDate = formatDate();

        Map<String, StatisticsResult> sortedResult = attendanceManager.sortCrew(localDate);
        outputView.printExpelledWarningResult(sortedResult);
    }

    private String formatTime(List<String> time) {
        return String.format(INPUT_TIME_FORMAT, Integer.parseInt(time.get(0)),
            Integer.parseInt(time.get(1)));
    }


    private LocalDate formatDate() {
        String date = DEFAULT_YEAR + "-" + DEFAULT_MONTH + "-" + DEFAULT_DAY;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(date, formatter);
    }

    private LocalDateTime formatDateTime(String dateForm, String timeForm) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(dateForm + " " + timeForm, formatter);
    }

}
