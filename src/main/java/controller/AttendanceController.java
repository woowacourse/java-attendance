package controller;

import domain.AttendanceManager;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import view.InputView;
import view.OutputView;

public class AttendanceController {

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
            attendanceManager.attendCrew(name, dateTime);
            outputView.printAttendanceRecord(dateTime);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void edit(){
        String name = inputView.readEditName();
        String dayOfMonth = inputView.readEditDayOfMonth();
        String time = inputView.readTime();
    }
}
