package attendance;

import attendance.domain.Attendance;
import attendance.domain.AttendanceRecord;
import attendance.domain.AttendanceUpdate;
import attendance.domain.CrewAttendanceManager;
import attendance.util.DateGenerator;
import attendance.util.DateTimeParser;
import attendance.view.InputView;
import attendance.view.Menu;
import attendance.view.OutputView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class AttendanceSystem {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    private final InputView inputView;
    private final OutputView outputView;
    private final DateGenerator dateGenerator;
    private final CrewAttendanceManager crewAttendanceManager;

    public AttendanceSystem(final InputView inputView, final OutputView outputView, final DateGenerator dateGenerator, final CrewAttendanceManager crewAttendanceManager) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.dateGenerator = dateGenerator;
        this.crewAttendanceManager = crewAttendanceManager;
    }

    public void run() {
        crewAttendanceManager.initAttendanceFromFile();

        Menu menu = displayMenuAndReadCommand();

        processCheck(menu);
        processUpdate(menu);
    }

    private Menu displayMenuAndReadCommand() {
        outputView.printMenu(dateGenerator.generate());
        return inputView.readMenuCommand();
    }

    private void processCheck(final Menu menu) {
        if (menu.equals(Menu.CHECK)) {
            String nickname = inputView.readNickname();
            crewAttendanceManager.validateNicknameExists(nickname);

            LocalTime time = readAndParseAttendanceTime();

            Attendance attendance = crewAttendanceManager.processAttendanceCheck(nickname, time);
            outputView.printAttendanceResult(attendance);
        }
    }

    private void processUpdate(final Menu menu) {
        if (menu.equals(Menu.UPDATE)) {
            String nickname = inputView.readNicknameForUpdate();
            crewAttendanceManager.validateNicknameExists(nickname);

            int day = inputView.readAttendanceDayForUpdate();
            LocalDate date = DateTimeParser.parseDay(day, dateGenerator.generate());

            String time = inputView.readAttendanceTimeForUpdate();
            LocalTime time1 = DateTimeParser.parseTime(time, TIME_FORMATTER);

            LocalDateTime dateTime = LocalDateTime.of(date, time1);

            AttendanceUpdate attendanceUpdate = crewAttendanceManager.processAttendanceUpdate(nickname, dateTime);
            outputView.printAttendanceUpdate(attendanceUpdate);
        }
    }

    private void processRecordSearch(final Menu menu) {
        if (menu.equals(Menu.RECORD_SEARCH)) {
            String nickname = inputView.readNickname();
            crewAttendanceManager.validateNicknameExists(nickname);

            AttendanceRecord attendanceRecord = crewAttendanceManager.getAttendanceRecord(nickname);
            outputView.printAttendanceRecord(attendanceRecord);
        }
    }

    private LocalTime readAndParseAttendanceTime() {
        String attendanceTime = inputView.readAttendanceTime();
        return DateTimeParser.parseTime(attendanceTime, TIME_FORMATTER);
    }
}
