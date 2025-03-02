package controller;

import domain.*;
import exception.CustomException;
import view.InputView;
import view.OutputView;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class AttendanceCheckController implements Controller {
    private final InputView inputView;
    private final OutputView outputView;
    private final CrewAttendanceStorage crewAttendanceStorage;

    public AttendanceCheckController(
            InputView inputView,
            OutputView outputView,
            CrewAttendanceStorage crewAttendanceStorage
    ) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.crewAttendanceStorage = crewAttendanceStorage;
    }

    @Override
    public void run() {
        try {
            LocalDate now = CustomDate.now();
            AttendanceDate attendanceDate = new AttendanceDate(now);

            String name = inputView.readName();
            LocalTime time = inputView.readTime();

            registerAttendance(name, attendanceDate.getValue(), time);
        } catch (CustomException e) {
            outputView.printExceptionMessage(e.getMessage());
        } catch (DateTimeParseException e) {
            outputView.printExceptionMessage("");
        }
    }

    private void registerAttendance(String name, LocalDate date, LocalTime time) {
        crewAttendanceStorage.register(name, date, time);
        Attendance attendance = crewAttendanceStorage.findAttendance(name, date);
        AttendanceStatus status = attendance.getStatus();
        outputView.printAttendanceResult(date, attendance.getTime(), status);
    }
}
