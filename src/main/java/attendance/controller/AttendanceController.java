package attendance.controller;

import attendance.domain.AttendanceBook;
import attendance.domain.AttendanceStatus;
import attendance.domain.AttendanceTimeStatus;
import attendance.domain.CrewAttendance;
import attendance.domain.CrewAttendanceRepository;
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

    public void run() {
        CrewAttendanceRepository crewAttendanceRepository = initData();
        AttendanceBook attendanceBook = new AttendanceBook(crewAttendanceRepository);
        initOperations(attendanceBook);
        String option;
        while (!(option = getInputOption()).equals("Q")) {
            operations.get(option).run();
        }
    }

    private CrewAttendanceRepository initData() {
        return new CrewAttendanceRepository(FileLoader.loadAll(DataFileReader.read()));
    }

    private void initOperations(AttendanceBook attendanceBook) {
        operations.put("1", () -> registerAttendance(attendanceBook));
        operations.put("2", () -> modifyAttendance(attendanceBook));
        operations.put("3", () -> queryAttendance(attendanceBook));
        operations.put("4", () -> queryWarningCrews(attendanceBook));
    }

    private String getInputOption() {
        OutputView.printOptions();
        return InputView.readOption();
    }

    private void registerAttendance(AttendanceBook attendanceBook) {
        String name = InputView.readNickName();
        final LocalTime localTime = InputView.readAttendanceTime();
        LocalDateTime localDateTime = LocalDateTime.of(LocalDate.now(), localTime);

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
        Map<LocalDate, AttendanceTimeStatus> attendances = attendanceBook.queryAttendancesByName(name, today);
        OutputView.printAttendances(name, attendances);

        Map<AttendanceStatus, Integer> attendanceStatusCounts = attendanceBook.queryAttendanceStatusByName(name, today);
        OutputView.printAttendanceStatuses(attendanceStatusCounts);

        WarningLevel warningLevel = attendanceBook.queryCrewWarningLevel(name, today);
        OutputView.printCrewWarningLevel(warningLevel);
    }

    private void queryWarningCrews(AttendanceBook attendanceBook) {
        LocalDate today = LocalDate.now(ZoneId.of("Asia/Seoul"));
        Map<WarningLevel, List<CrewAttendance>> crewsByWarningLevel = attendanceBook.queryCrewsByWarningLevel(today);

        OutputView.printWarningCrews(crewsByWarningLevel);
    }
}
