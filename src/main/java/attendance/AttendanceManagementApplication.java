package attendance;

import java.time.LocalDateTime;
import java.time.ZoneId;

import attendance.controller.AttendanceManagementController;
import attendance.view.InputView;
import attendance.view.ResultView;

public class AttendanceManagementApplication {

    public static void main(String[] args) {
        LocalDateTime today = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        AttendanceManagementController attendanceManagementController = new AttendanceManagementController(
                new InputView(), new ResultView(), today
        );

        attendanceManagementController.run();
    }

}
