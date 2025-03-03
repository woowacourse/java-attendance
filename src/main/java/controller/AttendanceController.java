package controller;

import domain.AttendanceDateTime;
import domain.AttendanceDateTimes;
import domain.AttendanceHistories;
import domain.Crew;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import view.FileInputView;
import view.InputView;
import view.Menu;
import view.OutputView;

public class AttendanceController {
    private final OutputView outputView = new OutputView();
    private final InputView inputView = new InputView();
    private final FileInputView fileInputView = new FileInputView();
    private final Map<Menu, Runnable> menuTable = new EnumMap<>(Menu.class);

    {
        menuTable.put(Menu.CHECK_IN, this::checkIn);
        menuTable.put(Menu.UPDATE_ATTENDANCE, this::updateAttendance);
        menuTable.put(Menu.CHECK_ATTENDANCE_RECORDS, this::checkAttendanceRecords);
        menuTable.put(Menu.CHECK_DISCIPLINED_CREWS, this::checkAttendanceRecords);
        menuTable.put(Menu.QUIT, this::quit);
    }

    public void run(LocalDate today) {
        AttendanceHistories attendanceHistories = loadCsvData();
        do {
            outputView.displayMenu(today);
            Menu menuInput = inputView.readMenu();
            menuTable.get(menuInput).run();
        } while (true);
    }

    private AttendanceHistories loadCsvData() {
        Map<String, List<LocalDateTime>> rawAttendanceData = fileInputView.readAttendanceFile();
        Map<Crew, AttendanceDateTimes> rawAttendanceHistoryData = new HashMap<>();
        for (Entry<String, List<LocalDateTime>> attendanceEntry : rawAttendanceData.entrySet()) {
            Crew crew = new Crew(attendanceEntry.getKey());
            AttendanceDateTimes attendanceDateTimes = new AttendanceDateTimes(attendanceEntry.getValue().stream()
                    .map(AttendanceDateTime::new)
                    .toList());
            rawAttendanceHistoryData.put(crew, attendanceDateTimes);
        }
        return new AttendanceHistories(rawAttendanceHistoryData);
    }

    private void checkIn() {
        System.out.println("1. 출석 확인");
    }

    private void updateAttendance() {
        System.out.println("2. 출석 수정");
    }

    private void checkAttendanceRecords() {
        System.out.println("3. 크루별 출석 기록 확인");
    }

    private void checkDisciplinedCrews() {
        System.out.println("4. 제적 위험자 확인");
    }

    private void quit() {
        System.exit(0);
    }

//    private void retryUntilSuccess(Runnable runnable) {
//        try {
//            runnable.run();
//        } catch (IllegalArgumentException e) {
//            System.out.printf("%s%n", e.getMessage());
//            retryUntilSuccess(runnable);
//        }
//    }
}
