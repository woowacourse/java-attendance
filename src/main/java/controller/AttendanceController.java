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
        commandLoop(attendanceManager);
    }

    private void commandLoop(AttendanceManager attendanceManager) {
        boolean isLoopContinue = true;
        while (isLoopContinue) {
            String command = inputView.inputCommand();
            isLoopContinue = commandProcess(attendanceManager, command);
        }
    }

    private boolean commandProcess(AttendanceManager attendanceManager, String command) {
        if (command.equals("1")) {
            attendProcess(attendanceManager);
            return true;
        }
        if (command.equalsIgnoreCase("Q")) {
            return false;
        }
        outputView.printInvalidCommand();
        return true;
    }

    private void attendProcess(AttendanceManager attendanceManager) {
        String inputNickName = inputView.inputAttendNickName();
        NickName nickName = new NickName(inputNickName);
        String attendingTime = inputView.inputAttendingTime();
        AttendanceRecord attendanceRecord = AttendanceRecord.timeOf(attendingTime);
        if (attendanceManager.isAttended(nickName, attendanceRecord)) {
            outputView.printAlreadyAttended();
            return;
        }
        attendanceManager.attend(nickName, attendanceRecord);
        outputView.printAttend(attendanceRecord);
    }
}
