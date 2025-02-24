package attendance.controller;

import attendance.config.AppConfig;
import attendance.domain.AttendanceSystem;
import attendance.domain.intializer.AttendanceSystemInitializer;
import attendance.domain.record.AttendanceRecord;
import attendance.domain.risk.RiskStatistic;
import attendance.dto.UpdateResult;
import attendance.view.InputView;
import attendance.view.OutputView;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class AttendanceController {

    private final InputView inputView;
    private final OutputView outputView;
    private final AttendanceSystem attendanceSystem;
    private final AttendanceSystemInitializer initializer;

    public AttendanceController(AppConfig appConfig) {
        this.inputView = appConfig.getInputView();
        this.outputView = appConfig.getOutputView();
        this.attendanceSystem = appConfig.getAttendanceSystem();
        this.initializer = appConfig.getInitializer();
        initializer.initialize();
    }

    public void run() {
        while (true) {
            LocalDate nowDate = LocalDate.now();
            AttendanceMenu menu = selectMenu(nowDate);
            if (menu == AttendanceMenu.ADD) {
                saveAttendanceRecord(nowDate);
            }
            if (menu == AttendanceMenu.UPDATE) {
                updateAttendanceRecord(nowDate);
            }
            if (menu == AttendanceMenu.SEARCH) {
                searchAttendanceRecordsByCrew(nowDate);
            }
            if (menu == AttendanceMenu.RISK) {
                searchRiskStatistics(nowDate);
            }
            if (menu == AttendanceMenu.QUIT) {
                return;
            }
        }
    }

    private AttendanceMenu selectMenu(LocalDate nowDate) {
        while (true) {
            try {
                outputView.printMenu(nowDate);
                return AttendanceMenu.parse(inputView.readMenuCommand());
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private void saveAttendanceRecord(LocalDate nowDate) {
        try {
            String nickname = inputView.readNickname();
            LocalTime arrivalTime = inputView.readArrivalTime();
            LocalDateTime arrivalDateTime = LocalDateTime.of(nowDate, arrivalTime);
            AttendanceRecord savedRecord = attendanceSystem.saveAttendanceRecord(nickname, arrivalDateTime);
            outputView.printAttendanceRecord(savedRecord);
        } catch (IllegalArgumentException e) {
            outputView.printErrorMessage(e.getMessage());
        }
    }

    private void updateAttendanceRecord(LocalDate nowDate) {
        try {
            String nickname = inputView.readNicknameForUpdate();
            LocalDate date = nowDate.withDayOfMonth(inputView.readDateForUpdate());
            LocalTime newArrivalTime = inputView.readArrivalTimeForUpdate();
            UpdateResult updateResult = attendanceSystem.updateAttendanceRecord(nickname, date, newArrivalTime);
            outputView.printAttendUpdateResult(updateResult);
        } catch (IllegalArgumentException e) {
            outputView.printErrorMessage(e.getMessage());
        }
    }

    private void searchAttendanceRecordsByCrew(LocalDate nowDate) {
        try {
            String nickname = inputView.readNickname();
            List<AttendanceRecord> records = attendanceSystem.searchAttendanceRecordsByCrew(nickname, nowDate);
            RiskStatistic state = attendanceSystem.searchRiskStatistic(
                    nickname, makeFistDateInMonth(nowDate), nowDate);
            outputView.printRecordsInMonth(records);
            outputView.printAttendanceState(state);
        } catch (IllegalArgumentException e) {
            outputView.printErrorMessage(e.getMessage());
        }
    }

    private void searchRiskStatistics(LocalDate nowDate) {
        try {
            List<RiskStatistic> riskStatistics =
                    attendanceSystem.searchRiskStatistics(makeFistDateInMonth(nowDate), nowDate);
            outputView.printRiskStatistics(riskStatistics);
        } catch (IllegalArgumentException e) {
            outputView.printErrorMessage(e.getMessage());
        }
    }

    private LocalDate makeFistDateInMonth(LocalDate nowDate) {
        return nowDate.withDayOfMonth(1);
    }
}
