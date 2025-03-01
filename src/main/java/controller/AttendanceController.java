package controller;

import dto.AttendanceCheckInRequest;
import dto.AttendanceHistoryRequest;
import dto.AttendanceOptionRequest;
import dto.AttendanceUpdateRequest;
import java.time.LocalDateTime;
import util.DateTimeGenerator;
import view.InputView;

public class AttendanceController {

    private final DateTimeGenerator dateTimeGenerator;

    public AttendanceController(DateTimeGenerator dateTimeGenerator) {
        this.dateTimeGenerator = dateTimeGenerator;
    }

    public void run() {
        LocalDateTime now = LocalDateTime.now();

        AttendanceOptionRequest attendanceOptionRequest = InputView.readAttendanceOptionRequest(dateTimeGenerator);

        AttendanceCheckInRequest attendanceCheckInRequest = InputView.readAttendanceCheckInRequest();

        AttendanceUpdateRequest attendanceUpdateRequest = InputView.readAttendanceUpdateRequest(dateTimeGenerator);

        AttendanceHistoryRequest attendanceHistoryRequest = InputView.readAttendanceHistoryRequest();
    }
}
