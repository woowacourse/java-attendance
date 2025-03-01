package controller;

import attendance.AttendanceHistory;
import attendance.AttendanceTime;
import attendance.AttendanceTimes;
import java.util.Map;
import view.InputView;

public class AttendanceController {

    private final InputView inputView;
    private final AttendanceHistory attendanceHistory;

    public AttendanceController(InputView inputView, AttendanceHistory attendanceHistory) {
        this.inputView = inputView;
        this.attendanceHistory = attendanceHistory;
    }

    public void start() {
        String inputNickname = inputView.inputNickname();
        attendanceHistory.isValidCrew(inputNickname);
        Map<String, AttendanceTimes> attendanceHistory1 = attendanceHistory.getAttendanceHistory();
        AttendanceTimes attendanceTimes = attendanceHistory1.get("이든");
        for (AttendanceTime attendanceTime : attendanceTimes.getAttendanceTimes()) {
            System.out.println(attendanceTime.getAttendanceDateTime());
        }

    }
}
