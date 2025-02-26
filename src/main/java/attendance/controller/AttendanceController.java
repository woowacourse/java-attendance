package attendance.controller;

import attendance.domain.AttendanceBook;
import attendance.domain.AttendanceStatus;
import attendance.domain.AttendanceTimeStatus;
import attendance.domain.CrewAttendance;
import attendance.domain.WarningLevel;
import attendance.util.FileLoader;
import attendance.view.DataFileReader;
import attendance.view.InputView;
import attendance.view.OutputView;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttendanceController {
    private static final Map<String, Runnable> operations = new HashMap<>();

    private static final String REGISTER_KEY = "1";
    private static final String MODIFY_KEY = "2";
    private static final String CREW_ATTENDANCE_KEY = "3";
    private static final String WARNING_CREWS_KEY = "4";
    private static final String QUIT_KEY = "Q";

    public void run() {
        AttendanceBook attendanceBook = initData();
        initOperations(attendanceBook);
        String option;
        while (!(option = getInputOption()).equals(QUIT_KEY)) {
            operations.get(option).run();
        }
    }

    private AttendanceBook initData() {
        return new AttendanceBook(FileLoader.loadAll(DataFileReader.read()));
    }

    private void initOperations(AttendanceBook attendanceBook) {
        operations.put(REGISTER_KEY, () -> registerAttendance(attendanceBook));
        operations.put(MODIFY_KEY, () -> modifyAttendance(attendanceBook));
        operations.put(CREW_ATTENDANCE_KEY, () -> queryAttendance(attendanceBook));
        operations.put(WARNING_CREWS_KEY, () -> queryWarningCrews(attendanceBook));
    }

    private String getInputOption() {
        OutputView.printOptions();
        return InputView.readOption();
    }

    private void registerAttendance(AttendanceBook attendanceBook) {
        String name = InputView.readNickName();
        final LocalTime localTime = InputView.readAttendanceTime();
        LocalDateTime localDateTime = LocalDateTime.of(LocalDate.now(ZoneId.of("Asia/Seoul")), localTime);

        attendanceBook.add(name, localDateTime);

        OutputView.printAttendance(localDateTime);
    }

    private void modifyAttendance(AttendanceBook attendanceBook) {
        String name = InputView.readModifyNickName();
        final int day = InputView.readModifyDay();
        LocalDate targetDate = LocalDate.of(2024, 12, day);
        LocalTime newLocalTime = InputView.readModifyTime();
        LocalDateTime newLocalDateTime = LocalDateTime.of(targetDate, newLocalTime);

        List<AttendanceTimeStatus> modifiedResult = attendanceBook.modify(name, newLocalDateTime);
        OutputView.printModifiedResult(targetDate, modifiedResult);
    }

    private void queryAttendance(AttendanceBook attendanceBook) {
        LocalDate today = LocalDate.now(ZoneId.of("Asia/Seoul"));
        String name = InputView.readNickName();
        Map<LocalDate, AttendanceTimeStatus> attendances = attendanceBook.getAttendanceHistory(name, today);
        OutputView.printAttendances(name, attendances);

        Map<AttendanceStatus, Integer> attendanceStatusCounts = attendanceBook.getAttendanceStatusCounts(name, today);
        OutputView.printAttendanceStatuses(attendanceStatusCounts);

        WarningLevel warningLevel = attendanceBook.getCrewWarningLevel(name, today);
        OutputView.printCrewWarningLevel(warningLevel);
    }

    private void queryWarningCrews(AttendanceBook attendanceBook) {
        LocalDate today = LocalDate.now(ZoneId.of("Asia/Seoul"));
        Map<WarningLevel, List<CrewAttendance>> crewsByWarningLevel = attendanceBook.getCrewsByWarningLevel(today);

        OutputView.printWarningCrews(crewsByWarningLevel, today);
    }
}
