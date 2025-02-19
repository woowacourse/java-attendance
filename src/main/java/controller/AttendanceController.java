package controller;

import domain.AttendanceManager;
import domain.TimeAndStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import view.InputView;
import view.OutputView;

public class AttendanceController {

    private final String DEFAULT_YEAR = "2025";
    private final String DEFAULT_MONTH = "02";
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
        attend();
        edit();
    }

    private void attend() {
        String name = inputView.readName();
        String time = inputView.readTime();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = LocalDateTime.now().format(formatter);
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
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
}
