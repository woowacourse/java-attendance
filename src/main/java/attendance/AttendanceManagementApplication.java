package attendance;

import java.time.LocalDateTime;

import attendance.controller.AttendanceManagementController;
import attendance.view.InputView;
import attendance.view.ResultView;

public class AttendanceManagementApplication {

    public static void main(String[] args) {
        LocalDateTime today = LocalDateTime.of(2025, 2, 28, 10, 0);
        AttendanceManagementController attendanceManagementController = new AttendanceManagementController(
                new InputView(), new ResultView(), today
        );

        attendanceManagementController.run();
    }

}
