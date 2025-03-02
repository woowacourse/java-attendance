package controller;

import domain.AttendanceManager;
import domain.AttendanceRecord;
import domain.NickName;
import view.InputView;
import view.OutputView;

public class AttendanceController {
    private final InputView inputView;
    private final OutputView outputView;

    public AttendanceController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void start() {
        AttendanceManager attendanceManager = new AttendanceManager();
        String command = inputView.inputCommand();
        if (command.equals("1")) {
            attendProcess(attendanceManager);
        }
    }

    private void attendProcess(AttendanceManager attendanceManager) {
        String inputNickName = inputView.inputAttendNickName();
        NickName nickName = new NickName(inputNickName);
        String attendingTime = inputView.inputAttendingTime();
        AttendanceRecord attendanceRecord = AttendanceRecord.timeOf(attendingTime);
        attendanceManager.attend(nickName, attendanceRecord);
        outputView.printAttend(attendanceRecord);
    }
}
