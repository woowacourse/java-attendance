package attendance.controller;

import attendance.config.AppConfig;
import attendance.domain.AttendanceRecord;
import attendance.domain.AttendanceSystem;
import attendance.domain.AttendanceSystemInitializer;
import attendance.domain.RiskStatistic;
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
            AttendanceMenu menu = MenuTemplate.run(this::selectMenu, LocalDate.now(), outputView);
            processMenuFunction(menu);
            if (menu == AttendanceMenu.QUIT) {
                return;
            }
        }
    }

    private AttendanceMenu selectMenu(LocalDate nowDate) {
        outputView.printMenu(nowDate);
        return AttendanceMenu.parse(inputView.readMenuCommand());
    }

    private void processMenuFunction(AttendanceMenu menu) {
        MenuTemplate.run(this::saveAttendanceRecord, menu, outputView);
        MenuTemplate.run(this::updateAttendanceRecord, menu, outputView);
        MenuTemplate.run(this::searchAttendanceRecordsByCrew, menu, outputView);
        MenuTemplate.run(this::searchRiskStatistics, menu, outputView);
    }

    private void saveAttendanceRecord(AttendanceMenu menu) {
        LocalDate nowDate = LocalDate.now();
        if (menu == AttendanceMenu.ADD) {
            String nickname = inputView.readNickname();
            LocalTime arrivalTime = inputView.readArrivalTime();
            LocalDateTime arrivalDateTime = LocalDateTime.of(nowDate, arrivalTime);
            AttendanceRecord savedRecord = attendanceSystem.saveAttendanceRecord(nickname, arrivalDateTime);
            outputView.printAttendanceRecord(savedRecord);
        }
    }

    private void updateAttendanceRecord(AttendanceMenu menu) {
        LocalDate nowDate = LocalDate.now();
        if (menu == AttendanceMenu.UPDATE) {
            String nickname = inputView.readNicknameForUpdate();
            LocalDate date = nowDate.withDayOfMonth(inputView.readDateForUpdate());
            LocalTime newArrivalTime = inputView.readArrivalTimeForUpdate();
            UpdateResult updateResult = attendanceSystem.updateAttendanceRecord(nickname, date, newArrivalTime);
            outputView.printAttendUpdateResult(updateResult);
        }
    }

    private void searchAttendanceRecordsByCrew(AttendanceMenu menu) {
        LocalDate nowDate = LocalDate.now();
        if (menu == AttendanceMenu.SEARCH) {
            String nickname = inputView.readNickname();
            List<AttendanceRecord> records = attendanceSystem.searchAttendanceRecordsByCrew(nickname, nowDate);
            RiskStatistic state = attendanceSystem.searchRiskStatistic(
                    nickname, makeFistDateInMonth(nowDate), nowDate);
            outputView.printRecordsInMonth(records);
            outputView.printAttendanceState(state);
        }
    }

    private void searchRiskStatistics(AttendanceMenu menu) {
        LocalDate nowDate = LocalDate.now();
        if (menu == AttendanceMenu.RISK) {
            List<RiskStatistic> riskStatistics =
                    attendanceSystem.searchRiskStatistics(makeFistDateInMonth(nowDate), nowDate);
            outputView.printRiskStatistics(riskStatistics);
        }
    }

    private LocalDate makeFistDateInMonth(LocalDate nowDate) {
        return nowDate.withDayOfMonth(1);
    }
}
