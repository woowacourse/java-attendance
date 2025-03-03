package controller;

import domain.AttendanceHistories;
import domain.AttendanceHistoryGenerator;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
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

    private void checkIn() {
        String nickname = inputView.readNickname();
        LocalTime time = inputView.readCheckInTime();
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

    private AttendanceHistories loadCsvData() {
        Map<String, List<LocalDateTime>> rawAttendanceData = fileInputView.readAttendanceFile();
        return AttendanceHistoryGenerator.generate(rawAttendanceData);
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
