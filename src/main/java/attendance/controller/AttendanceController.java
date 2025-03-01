package attendance.controller;

import attendance.CurrentDate;
import attendance.domain.AttendanceHistory;
import attendance.view.InputView;
import java.time.LocalDate;

public class AttendanceController {

    private final InputView inputView;
    private final AttendanceHistory attendanceHistory;
    private final CurrentDate currentDate;

    public AttendanceController(InputView inputView, AttendanceHistory attendanceHistory, CurrentDate currentDate) {
        this.inputView = inputView;
        this.attendanceHistory = attendanceHistory;
        this.currentDate = currentDate;
    }

    public void start() {
        String inputNickname = inputView.inputNickname();
        attendanceHistory.isValidCrew(inputNickname);
        LocalDate nowDate = currentDate.now();

    }


}
