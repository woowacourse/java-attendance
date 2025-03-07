package controller;

import domain.AttendanceDateTime;
import domain.AttendanceDateTimes;
import domain.AttendanceHistories;
import domain.AttendanceHistoryGenerator;
import domain.Crew;
import domain.DisciplinaryStatus;
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
    private final AttendanceHistories attendanceHistories;
    private LocalDate today = LocalDate.now();

    public AttendanceController() {
        this.attendanceHistories = loadCsvData();
        menuTable.put(Menu.CHECK_IN, this::checkIn);
        menuTable.put(Menu.UPDATE_ATTENDANCE, this::updateAttendance);
        menuTable.put(Menu.CHECK_ATTENDANCE_RECORDS, this::checkAttendanceRecords);
        menuTable.put(Menu.CHECK_DISCIPLINED_CREWS, this::checkDisciplinedCrews);
        menuTable.put(Menu.QUIT, this::quit);
    }

    public void run() {
        do {
            today = LocalDate.now();
            retryUntilSuccess(() -> {
                outputView.displayMenu(today);
                Menu menuInput = inputView.readMenu();
                menuTable.get(menuInput).run();
            });
        } while (true);
    }

    private void checkIn() {
        String nickname = inputView.readNickname();
        LocalTime inputTime = inputView.readCheckInTime();
        LocalDateTime checkInDateTime = today.atTime(inputTime);
        attendanceHistories.addAttendanceHistory(new Crew(nickname), checkInDateTime);
        outputView.displayAttendanceRecord(checkInDateTime);
    }

    private void updateAttendance() {
        String nickname = inputView.readUpdateNickname();
        LocalDate updateDate = inputView.readUpdateDate();
        LocalTime updateTime = inputView.readUpdateTime();
        LocalDateTime newAttendanceDateTime = LocalDateTime.of(updateDate, updateTime);
        AttendanceDateTime oldAttendanceDateTime = attendanceHistories.replaceAttendanceHistory(new Crew(nickname),
                newAttendanceDateTime);
        outputView.displayUpdateResult(oldAttendanceDateTime, newAttendanceDateTime);
    }

    private void checkAttendanceRecords() {
        String nickname = inputView.readNickname();
        Crew crew = new Crew(nickname);
        AttendanceDateTimes attendanceDateTimes = attendanceHistories.getAttendanceDateTimes(crew);
        outputView.displayAttendanceDateTimes(crew, attendanceDateTimes, today);
        outputView.displayAttendanceCount(crew, attendanceHistories, today);
        DisciplinaryStatus disciplinaryStatus = attendanceHistories.getDisciplinaryStatusOf(crew, today);
        if (disciplinaryStatus != DisciplinaryStatus.NONE) {
            outputView.displayDisciplinedStatus(disciplinaryStatus);
        }
    }

    private void checkDisciplinedCrews() {
        List<Crew> disciplinedCrews = attendanceHistories.getDisciplinedCrews(today);
        outputView.displayDisciplinedCrews(disciplinedCrews, attendanceHistories, today);
    }

    private void quit() {
        System.exit(0);
    }

    private AttendanceHistories loadCsvData() {
        Map<String, List<LocalDateTime>> rawAttendanceData = fileInputView.readAttendanceFile();
        return AttendanceHistoryGenerator.generate(rawAttendanceData);
    }

    private void retryUntilSuccess(Runnable runnable) {
        try {
            runnable.run();
        } catch (IllegalArgumentException e) {
            System.out.printf("%s%n", e.getMessage());
            retryUntilSuccess(runnable);
        }
    }
}
