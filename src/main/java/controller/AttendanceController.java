package controller;

import dto.AttendanceCheckInRequest;
import dto.AttendanceHistoryRequest;
import dto.AttendanceOptionRequest;
import dto.AttendanceUpdateRequest;
import java.time.LocalDateTime;
import view.InputView;

public class AttendanceController {

    public void run() {
        LocalDateTime now = LocalDateTime.now();

        AttendanceOptionRequest attendanceOptionRequest = InputView.readAttendanceOptionRequest(now);

        AttendanceCheckInRequest attendanceCheckInRequest = InputView.readAttendanceCheckInRequest();

        AttendanceUpdateRequest attendanceUpdateRequest = InputView.readAttendanceUpdateRequest(now);

        AttendanceHistoryRequest attendanceHistoryRequest = InputView.readAttendanceHistoryRequest();
    }
}
