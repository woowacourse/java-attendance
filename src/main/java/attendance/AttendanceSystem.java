package attendance;

import attendance.config.AppConfig;
import attendance.domain.Attendance;
import attendance.domain.AttendanceInit;
import attendance.domain.AttendanceManager;
import attendance.domain.AttendanceStatus;
import attendance.domain.Holiday;
import attendance.utility.DateGenerator;
import attendance.utility.DateTimeParser;
import attendance.view.InputView;
import attendance.view.OutputView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import static attendance.AttendanceMenu.CHECK;
import static attendance.AttendanceMenu.QUIT;
import static attendance.AttendanceMenu.RECORD_SEARCH;
import static attendance.AttendanceMenu.RISK_SEARCH;
import static attendance.AttendanceMenu.UPDATE;

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
        attendanceInit.initAttendances();

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
        processRecordSearch(menu);
        processRiskSearch(menu);
    }

    private void processCheck(final AttendanceMenu menu, final LocalDate today) {
        if (menu == CHECK) {
            holiday.validateHoliday(today);

            String nickname = validateAndReadNickname(false);
            LocalTime time = parseTime(false);
            LocalDateTime dateTime = LocalDateTime.of(today, time);

            Attendance attendanceCheck = attendanceManager.processAttendanceCheck(dateTime, nickname);
            outputView.printAttendanceRecords(attendanceCheck);
        }
    }

    private void processUpdate(final AttendanceMenu menu, final LocalDate today) {
        if (menu == UPDATE) {
            String nickname = validateAndReadNickname(true);

            int day = inputView.readDateForUpdate();
            LocalDate date = DateTimeParser.parseDateByDay(today, day);
            LocalTime time = parseTime(true);
            LocalDateTime dateTime = LocalDateTime.of(date, time);

            List<Attendance> attendanceUpdate = attendanceManager.processAttendanceUpdate(dateTime, nickname);
            outputView.printAttendUpdateResult(attendanceUpdate);
        }
    }

    private void processRecordSearch(final AttendanceMenu menu) {
        if (menu == RECORD_SEARCH) {
            String nickname = validateAndReadNickname(false);

            List<Attendance> attendanceRecords = attendanceManager.getAttendanceRecord(nickname);
            outputView.printAttendanceRecords(attendanceRecords, nickname);

            AttendanceStatus attendanceStatus = attendanceManager.getAttendanceStatus(nickname);
            outputView.printAttendanceStatus(attendanceStatus);
        }
    }

    private void processRiskSearch(final AttendanceMenu menu) {
        if (menu == RISK_SEARCH) {
            Map<String, AttendanceStatus> riskCrews = attendanceManager.getAttendanceRiskCrew();
            outputView.printAttendanceRiskCrews(riskCrews);
        }
    }

    private String validateAndReadNickname(final boolean isForUpdated) {
        String nickname = inputView.readNickname(isForUpdated);
        attendanceManager.validateNicknameExists(nickname);
        return nickname;
    }

    private LocalTime parseTime(final boolean isForUpdated) {
        String time = inputView.readAttendanceTime(isForUpdated);
        return DateTimeParser.parseTime(time);
    }
}
