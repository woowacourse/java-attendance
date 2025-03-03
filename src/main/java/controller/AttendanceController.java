package controller;

import domain.AttendanceManager;
import domain.AttendanceRecord;
import domain.NickName;
import java.util.List;
import util.FileUtil;
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
        AttendanceManager attendanceManager = loadAttendanceManager();
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

    private AttendanceManager loadAttendanceManager() {
        AttendanceManager attendanceManager = new AttendanceManager();
        final String attendancesPath = "src/main/resources/attendances.csv";
        List<String> attendances = FileUtil.readlines(attendancesPath);
        attendances.stream()
                .filter(line -> !line.isBlank())
                .filter(line -> !line.startsWith("#"))
                .filter(line -> !line.startsWith("nickname,datetime"))
                .forEach(line -> parseAttendanceRecordAndInsert(attendanceManager, line));
        return attendanceManager;
    }

    private void parseAttendanceRecordAndInsert(AttendanceManager attendanceManager, String line) {
        String[] commaSplit = line.split(",");
        NickName nickName = new NickName(commaSplit[0]);
        String[] barSplit = commaSplit[1].split("-");
        String[] spaceSplit = barSplit[2].split(" ");
        String date = spaceSplit[0];
        String time = spaceSplit[1];
        attendanceManager.register(nickName);
        AttendanceRecord attendanceRecord = AttendanceRecord.of(date, time);
        attendanceManager.attend(nickName, attendanceRecord);
    }
}
