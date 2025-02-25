package attendance;

import attendance.config.AppConfig;
import attendance.domain.Attendance;
import attendance.domain.AttendanceInit;
import attendance.domain.AttendanceManager;
import attendance.domain.AttendanceStatus;
import attendance.domain.Holiday;
import attendance.utility.DateGenerator;
import attendance.utility.DateTimeParser;
import attendance.view.AttendanceMenu;
import attendance.view.InputView;
import attendance.view.OutputView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import static attendance.view.AttendanceMenu.CHECK;
import static attendance.view.AttendanceMenu.QUIT;
import static attendance.view.AttendanceMenu.RECORD_SEARCH;
import static attendance.view.AttendanceMenu.RISK_SEARCH;
import static attendance.view.AttendanceMenu.UPDATE;

public class AttendanceSystem {

    private final InputView inputView;
    private final OutputView outputView;
    private final DateGenerator dateGenerator;
    private final Holiday holiday;
    private final AttendanceManager attendanceManager;
    private final AttendanceInit attendanceInit;

    public AttendanceSystem(AppConfig appConfig) {
        this.inputView = appConfig.getInputView();
        this.outputView = appConfig.getOutputView();
        this.dateGenerator = appConfig.getDateGenerator();
        this.holiday = appConfig.getHoliday();
        this.attendanceManager = appConfig.getAttendanceManager();
        this.attendanceInit = appConfig.getAttendanceInit();
    }

    public void run() {
        attendanceInit.initSystem();

        while (true) {
            LocalDate today = dateGenerator.now();
            AttendanceMenu menu = selectMenu(today);

            processSystem(menu, today);
            if (menu == QUIT) {
                return;
            }
        }
    }

    private AttendanceMenu selectMenu(final LocalDate today) {
        outputView.printMenu(today);
        return AttendanceMenu.find(inputView.readMenuCommand());
    }

    private void processSystem(final AttendanceMenu menu, final LocalDate today) {
        processCheck(menu, today);
        processUpdate(menu, today);
        processRecordSearch(menu, today);
        processRiskSearch(menu, today);
    }

    private void processCheck(final AttendanceMenu menu, final LocalDate today) {
        if (menu == CHECK) {
            holiday.validateHoliday(today);

            String nickname = validateAndReadNickname();
            LocalTime time = parseTime();
            LocalDateTime dateTime = LocalDateTime.of(today, time);

            Attendance attendanceCheck = attendanceManager.processAttendanceCheck(dateTime, nickname);
            outputView.printAttendanceRecord(attendanceCheck);
        }
    }

    private void processUpdate(final AttendanceMenu menu, final LocalDate today) {
        if (menu == UPDATE) {
            String nickname = validateAndReadNicknameForUpdate();

            int day = inputView.readDateForUpdate();
            LocalDate date = DateTimeParser.parseDateByDay(today, day);
            LocalTime time = parseTimeForUpdate();
            LocalDateTime dateTime = LocalDateTime.of(date, time);

            List<Attendance> attendanceUpdate = attendanceManager.processAttendanceUpdate(dateTime, nickname);
            outputView.printAttendUpdateResult(attendanceUpdate);
        }
    }

    private void processRecordSearch(final AttendanceMenu menu, final LocalDate today) {
        if (menu == RECORD_SEARCH) {
            String nickname = validateAndReadNickname();

            List<Attendance> attendanceRecords = attendanceManager.getAttendanceRecord(today, nickname);
            outputView.printAttendanceRecords(attendanceRecords, nickname);

            AttendanceStatus attendanceStatus = attendanceManager.getAttendanceStatus(today, nickname);
            outputView.printAttendanceStatus(attendanceStatus);
        }
    }

    private void processRiskSearch(final AttendanceMenu menu, final LocalDate today) {
        if (menu == RISK_SEARCH) {
            Map<String, AttendanceStatus> riskCrews = attendanceManager.getAttendanceRiskCrew(today);
            outputView.printAttendanceRiskCrews(riskCrews);
        }
    }

    private String validateAndReadNickname() {
        String nickname = inputView.readNickname();
        attendanceManager.validateNicknameExists(nickname);
        return nickname;
    }

    private String validateAndReadNicknameForUpdate() {
        String nickname = inputView.readNicknameForUpdate();
        attendanceManager.validateNicknameExists(nickname);
        return nickname;
    }

    private LocalTime parseTime() {
        String time = inputView.readAttendanceTime();
        return DateTimeParser.parseTime(time);
    }

    private LocalTime parseTimeForUpdate() {
        String time = inputView.readAttendanceTimeForUpdate();
        return DateTimeParser.parseTime(time);
    }
}
