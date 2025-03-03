package controller;

import domain.AttendanceManager;
import domain.AttendanceRecord;
import domain.Attendances;
import domain.NickName;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import util.Current;
import util.DateUtil;
import util.FileUtil;
import view.InputView;
import view.OutputView;

public class AttendanceController {
    private final InputView inputView;
    private final OutputView outputView;
    private final Map<String, Consumer<AttendanceManager>> commands;

    public AttendanceController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
        commands = new HashMap<>();
        registerCommand();
    }

    // 이곳에 각 명령별 함수형 인터페이스를 등록합니다.
    private void registerCommand() {
        commands.put("1", this::attendProcess);
        commands.put("2", this::editProcess);
        commands.put("3", this::checkAttendanceProcess);
    }

    public void start() {
        AttendanceManager attendanceManager = loadAttendanceManager();
        commandLoop(attendanceManager);
    }

    private void commandLoop(AttendanceManager attendanceManager) {
        boolean isLoopContinue = true;
        while (isLoopContinue) {
            String command = inputView.inputCommand();
            isLoopContinue = commandProcessErrorHandler(attendanceManager, command);
        }
    }

    private boolean commandProcessErrorHandler(AttendanceManager attendanceManager, String command) {
        try {
            return commandProcess(attendanceManager, command);
        } catch (RuntimeException e) {
            outputView.printErrorMessage(e);
            return true;
        }
    }

    private boolean commandProcess(AttendanceManager attendanceManager, String command) {
        if (command.equalsIgnoreCase("Q")) {
            return false;
        }
        if (!commands.containsKey(command)) {
            throw new IllegalArgumentException("잘못된 기능입니다. 다시 입력해 주세요.");
        }
        Consumer<AttendanceManager> commandConsumer = commands.get(command);
        commandConsumer.accept(attendanceManager);
        return true;
    }

    private void attendProcess(AttendanceManager attendanceManager) {
        NickName nickName = inputAttendNickName(attendanceManager);
        AttendanceRecord attendanceRecord = inputAttendTime();
        validateIsNotAttended(attendanceManager, nickName, attendanceRecord);
        attendanceManager.attend(nickName, attendanceRecord);
        outputView.printAttend(attendanceRecord);
    }

    private NickName inputAttendNickName(AttendanceManager attendanceManager) {
        String inputNickName = inputView.inputAttendNickName();
        NickName nickName = new NickName(inputNickName);
        if (!attendanceManager.isRegistered(nickName)) {
            throw new IllegalArgumentException("등록되지 않은 닉네임입니다.");
        }
        return nickName;
    }

    private AttendanceRecord inputAttendTime() {
        String attendTime = inputView.inputAttendingTime();
        return AttendanceRecord.timeOf(attendTime);
    }

    private void validateIsNotAttended(AttendanceManager attendanceManager, NickName nickName,
                                       AttendanceRecord attendanceRecord) {
        if (attendanceManager.isAttended(nickName, attendanceRecord)) {
            throw new IllegalArgumentException("이미 출석한 닉네임입니다. 수정 기능을 사용해 주세요.");
        }
    }

    private void editProcess(AttendanceManager attendanceManager) {
        NickName nickName = inputEditNickName(attendanceManager);
        AttendanceRecord editAttendanceRecord = inputEditAttendanceRecord();
        AttendanceRecord beforeAttendanceRecord = attendanceManager.getAttendanceRecordOfSameDate(nickName,
                editAttendanceRecord);
        outputView.printEdit(beforeAttendanceRecord, editAttendanceRecord);
    }

    private NickName inputEditNickName(AttendanceManager attendanceManager) {
        String inputNickName = inputView.inputEditNickName();
        NickName nickName = new NickName(inputNickName);
        if (!attendanceManager.isRegistered(nickName)) {
            throw new IllegalArgumentException("등록되지 않은 닉네임입니다.");
        }
        return nickName;
    }

    private AttendanceRecord inputEditAttendanceRecord() {
        String editDate = inputView.inputEditDate();
        String editTime = inputView.inputEditTime();
        return AttendanceRecord.of(editDate, editTime);
    }

    private void checkAttendanceProcess(AttendanceManager attendanceManager) {
        NickName nickName = inputCheckAttendaceNickName(attendanceManager);
        List<Integer> checkingDates = DateUtil.getAttendAbleDates(Current.getDayOfYesterday());
        Attendances attendances = attendanceManager.checkAttendance(nickName, checkingDates);
        outputView.printCheckAttendance(nickName, attendances);
    }

    private NickName inputCheckAttendaceNickName(AttendanceManager attendanceManager) {
        String inputNickName = inputView.inputCheckAttendanceNickName();
        NickName nickName = new NickName(inputNickName);
        if (!attendanceManager.isRegistered(nickName)) {
            throw new IllegalArgumentException("등록되지 않은 닉네임입니다.");
        }
        return nickName;
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
