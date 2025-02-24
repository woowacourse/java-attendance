package attendance.controller;

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

import static attendance.controller.AttendanceMenu.CHECK;
import static attendance.controller.AttendanceMenu.QUIT;
import static attendance.controller.AttendanceMenu.SEARCH;
import static attendance.controller.AttendanceMenu.UPDATE;
import static attendance.controller.AttendanceMenu.WARNED_CREW;
import static attendance.controller.AttendanceMenu.find;
import static attendance.utility.DateTimeParser.parseDateByDay;

public class AttendanceController {

    private final InputView inputView;
    private final OutputView outputView;
    private final DateGenerator dateGenerator;
    private final Holiday holiday;
    private final AttendanceManager attendanceManager;

    public AttendanceController(AppConfig appConfig) {
        this.inputView = appConfig.getInputView();
        this.outputView = appConfig.getOutputView();
        this.dateGenerator = appConfig.getDateGenerator();
        this.holiday = appConfig.getHoliday();
        this.attendanceManager = appConfig.getAttendanceManager();
    }

    public void run() {
        AttendanceInit attendanceInit = new AttendanceInit(attendanceManager);
        attendanceInit.initAttendances();

        while (true) {
            LocalDate today = dateGenerator.now();
            AttendanceMenu menu = selectMenu(today);

            processAttendance(menu, today);
            if (menu == QUIT) {
                return;
            }
        }
    }

    private void processAttendance(AttendanceMenu menu, LocalDate today) {
        processAttendanceCheck(menu, today);
        processAttendanceUpdate(menu, today);
        processAttendanceSearch(menu);
        processAttendanceWarnedCrews(menu);
    }

    private void processAttendanceCheck(AttendanceMenu menu, LocalDate today) {
        if (menu == CHECK) {
            holiday.validateHoliday(today);

            String nickname = inputView.readNickname(false);
            attendanceManager.validateNicknameExists(nickname);

            LocalDateTime dateTime = LocalDateTime.of(dateGenerator.now(), parseTime(false));

            Attendance attendance = attendanceManager.processAttendanceCheck(dateTime, nickname);
            outputView.printAttendanceRecord(attendance);
        }
    }

    private void processAttendanceUpdate(AttendanceMenu menu, LocalDate today) {
        if (menu == UPDATE) {
            String nickname = inputView.readNickname(true);
            attendanceManager.validateNicknameExists(nickname);

            int day = inputView.readDateForUpdate();
            LocalDate date = parseDateByDay(today, day);
            LocalTime time = parseTime(true);

            LocalDateTime dateTime = LocalDateTime.of(date, time);
            List<Attendance> updateAttendances = attendanceManager.processAttendanceUpdate(dateTime, nickname);

            outputView.printAttendUpdateResult(updateAttendances);
        }
    }

    private void processAttendanceSearch(AttendanceMenu menu) {
        if (menu == SEARCH) {
            String nickname = inputView.readNickname(false);
            attendanceManager.validateNicknameExists(nickname);

            List<Attendance> attendances = attendanceManager.getAttendanceRecord(nickname);
            outputView.printAttendanceSearch(attendances, nickname);

            AttendanceStatus attendanceStatus = attendanceManager.getAttendanceStatus(nickname);
            outputView.printAttendanceStatus(attendanceStatus);
        }
    }

    private void processAttendanceWarnedCrews(AttendanceMenu menu) {
        if (menu == WARNED_CREW) {
            outputView.printAttendanceWarnedCrews(attendanceManager.getAttendanceWarnedCrews());
        }
    }

    private AttendanceMenu selectMenu(LocalDate today) {
        outputView.printMenu(today);
        return find(inputView.readMenuCommand());
    }

    private LocalTime parseTime(boolean isForUpdated) {
        String time = inputView.readAttendanceTime(isForUpdated);
        return DateTimeParser.parseTime(time);
    }
}
