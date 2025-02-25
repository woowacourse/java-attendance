package attendance.controller;

import attendance.domain.Attendance;
import attendance.domain.Crews;
import attendance.util.DataLoader;
import attendance.view.DataFileReader;
import attendance.view.InputView;
import attendance.view.OutputView;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class AttendanceController {
    private static final Map<Character, Runnable> operations = new HashMap<>();
    public static final char REGISTER_ATTENDANCE_OPERATION = '1';
    public static final char MODIFY_ATTENDANCE_OPERATION = '2';
    public static final char QUERY_ATTENDANCE_OPERATION = '3';
    public static final char QUERY_WARNING_CREW_OPERATION = '4';
    public static final char QUIT_APPLICATION_OPERATION = 'Q';

    public void run() {
        Crews crews = initData();
        initOperations(crews);
        char option;
        while ((option = getInputOption()) != QUIT_APPLICATION_OPERATION) {
            runCommand(option);
        }
    }

    private void runCommand(char option) {
        try {
            operations.get(option).run();
        } catch (IllegalArgumentException e) {
            OutputView.printErrorMessage(e);
        }
    }

    private Crews initData() {
        return new Crews(DataLoader.loadAll(DataFileReader.read()));
    }

    private void initOperations(Crews crews) {
        operations.put(REGISTER_ATTENDANCE_OPERATION, () -> registerAttendance(crews));
        operations.put(MODIFY_ATTENDANCE_OPERATION, () -> modifyAttendance(crews));
        operations.put(QUERY_ATTENDANCE_OPERATION, () -> queryAttendance(crews));
        operations.put(QUERY_WARNING_CREW_OPERATION, () -> queryWarningCrews(crews));
    }

    private char getInputOption() {
        OutputView.printOptions();
        try {
            char option = InputView.readOption();
            validateOption(option);
            return option;
        } catch (IllegalArgumentException e) {
            OutputView.printErrorMessage(e);
            return getInputOption();
        }
    }

    private void validateOption(char option) {
        if (option != QUIT_APPLICATION_OPERATION && !operations.containsKey(option)) {
            throw new IllegalArgumentException(("존재하지 않는 기능입니다. 다시 입력해 주세요."));
        }
    }

    private void registerAttendance(Crews crews) {
        String name = InputView.readNickName();
        LocalTime localTime = InputView.readAttendanceTime();
        LocalDateTime localDateTime = LocalDateTime.of(LocalDate.of(2024, 12, LocalDate.now().getDayOfMonth()),
                localTime);
        crews.attend(name, localDateTime);
        OutputView.printAddedAttendance(localDateTime);
    }

    private void modifyAttendance(Crews crews) {
        String name = InputView.readModifyNickName();
        final int day = InputView.readModifyDay();
        LocalTime newTime = InputView.readModifyTime();
        LocalDateTime localDateTime = LocalDateTime.of(LocalDate.of(2024, 12, day), newTime);
        Attendance prevAttendance = crews.update(name, localDateTime);
        OutputView.printModifiedAttendance(prevAttendance, localDateTime);
    }

    private void queryAttendance(Crews crews) {
        String name = InputView.readNickName();
        OutputView.printQueryAttendance(name, crews);
    }

    private void queryWarningCrews(Crews crews) {
        OutputView.printWarningCrews(crews);
    }
}
