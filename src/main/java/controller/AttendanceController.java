package controller;

import domain.AttendanceManager;
import domain.AttendanceRecord;
import domain.Attendances;
import domain.NickName;
import domain.WarningCrews;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
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
        commands.put("4", this::checkWarningCrewProcess);
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
        try {
            LocalTime time = LocalTime.parse(attendTime);
            return new AttendanceRecord(Current.getToday(), time);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("올바른 시간 범위 및 형식이 아닙니다. 예시) 09:05");
        }
    }

    private void editProcess(AttendanceManager attendanceManager) {
        NickName nickName = inputEditNickName(attendanceManager);
        AttendanceRecord editAttendanceRecord = inputEditAttendanceRecord();
        AttendanceRecord beforeAttendanceRecord = attendanceManager.getAttendanceRecordOfSameDate(nickName,
                editAttendanceRecord);
        attendanceManager.edit(nickName, editAttendanceRecord);
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
        LocalDate editDate = inputEditDate();
        LocalTime editLocalTime = inputEditTime();
        return new AttendanceRecord(editDate, editLocalTime);
    }

    private LocalDate inputEditDate() {
        String editDate = inputView.inputEditDate();
        try {
            int editDateInt = Integer.parseInt(editDate);
            return LocalDate.of(Current.getYearOfToday(), Current.getMonthOfToday(), editDateInt);
        } catch (RuntimeException e) {
            throw new IllegalArgumentException("올바른 날짜 범위 또는 형식이 아닙니다. 예시) 1");
        }
    }

    private LocalTime inputEditTime() {
        String editTime = inputView.inputEditTime();
        try {
            return LocalTime.parse(editTime);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("올바른 시간 범위 또는 형식이 아닙니다. 예시) 09:05");
        }
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

    private void checkWarningCrewProcess(AttendanceManager attendanceManager) {
        List<Integer> checkingDates = DateUtil.getAttendAbleDates(Current.getDayOfYesterday());
        WarningCrews warningCrews = attendanceManager.findWarningCrews(checkingDates);
        outputView.printWarningCrews(warningCrews);
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
        attendanceManager.register(nickName);
        String[] spaceSplit = commaSplit[1].split(" ");
        LocalDate localDate = LocalDate.parse(spaceSplit[0]);
        LocalTime localTime = LocalTime.parse(spaceSplit[1]);
        AttendanceRecord attendanceRecord = new AttendanceRecord(localDate, localTime);
        attendanceManager.attend(nickName, attendanceRecord);
    }
}
