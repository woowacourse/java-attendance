package attendance.controller;

import attendance.CurrentDate;
import attendance.domain.AttendanceHistory;
import attendance.domain.AttendanceTime;
import attendance.domain.AttendanceType;
import attendance.view.InputView;
import attendance.view.OutputView;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AttendanceController {

    private final InputView inputView;
    private final OutputView outputView;
    private final AttendanceHistory attendanceHistory;
    private final CurrentDate currentDate;

    public AttendanceController(InputView inputView, OutputView outputView, AttendanceHistory attendanceHistory,
        CurrentDate currentDate) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.attendanceHistory = attendanceHistory;
        this.currentDate = currentDate;
    }

    public void start() {
        String inputNickname = inputView.inputNickname();
        attendanceHistory.isValidCrew(inputNickname);
        LocalDate nowDate = currentDate.now();
        LocalTime nowTime = inputView.inputAttendanceTime();
        AttendanceTime attendanceTime = AttendanceTime.from(LocalDateTime.of(nowDate, nowTime));
        attendanceHistory.add(inputNickname, attendanceTime);
        outputView.printAttendanceInfo(attendanceTime, AttendanceType.decideAttendanceType(attendanceTime));
    }


}
