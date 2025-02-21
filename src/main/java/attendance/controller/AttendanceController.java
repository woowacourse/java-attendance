package attendance.controller;

import attendance.domain.AttendanceRepository;
import attendance.domain.HourMinute;
import attendance.util.FileLoader;
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
        AttendanceRepository attendanceRepository = initData();
        initOperations(attendanceRepository);
        char option;
        while ((option = getInputOption()) != QUIT_APPLICATION_OPERATION) {
            operations.get(option).run();
        }
    }

    private AttendanceRepository initData() {
        return new AttendanceRepository(FileLoader.loadAll(DataFileReader.read()));
    }

    private void initOperations(AttendanceRepository attendanceRepository) {
        operations.put(REGISTER_ATTENDANCE_OPERATION, () -> registerAttendance(attendanceRepository));
        operations.put(MODIFY_ATTENDANCE_OPERATION, () -> modifyAttendance(attendanceRepository));
        operations.put(QUERY_ATTENDANCE_OPERATION, () -> queryAttendance(attendanceRepository));
        operations.put(QUERY_WARNING_CREW_OPERATION, () -> queryWarningCrews(attendanceRepository));
    }

    private char getInputOption() {
        OutputView.printOptions();
        return InputView.readOption();
    }

    private void registerAttendance(AttendanceRepository attendanceRepository) {
        String name = InputView.readNickName();
        final LocalTime localTime = InputView.readAttendanceTime();
        LocalDateTime localDateTime = LocalDateTime.of(LocalDateTime.now().toLocalDate(), localTime);
        attendanceRepository.add(name, localDateTime);
        OutputView.printAddedAttendance(localDateTime);
    }

    private void modifyAttendance(AttendanceRepository attendanceRepository) {
        String name = InputView.readModifyNickName();
        final int day = InputView.readModifyDay();
        LocalTime newTime = InputView.readModifyTime();
        LocalDateTime localDateTime = LocalDateTime.of(LocalDate.of(2024, 12, day), newTime);
        HourMinute prevHourMinute = attendanceRepository.update(name, localDateTime);
        OutputView.printModifiedAttendance(prevHourMinute, localDateTime);
    }

    private void queryAttendance(AttendanceRepository attendanceRepository) {
        String name = InputView.readNickName();
        OutputView.printQueryAttendance(name, attendanceRepository);
    }

    private void queryWarningCrews(AttendanceRepository attendanceRepository) {
        OutputView.printWarningCrews(attendanceRepository);
    }
}
