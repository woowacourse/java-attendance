package controller;

import controller.dto.ModifyAttendanceRequest;
import controller.dto.MonthAttendanceStatisticsRequest;
import controller.dto.RiskCrewsRequest;
import controller.dto.SaveAttendanceRequest;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import service.AttendanceRecordLoader;
import service.AttendanceService;
import service.dto.ModifyAttendanceRecordResponse;
import service.dto.MonthAttendanceStatisticsResponse;
import service.dto.RiskCrewsResponse;
import service.dto.SaveAttendanceRecordResponse;
import util.DateTimeUtil;
import util.ExceptionHandler;
import view.InputView;
import view.OutputView;

public class AttendanceController {
    private static final Map<MenuCommand, Runnable> COMMAND_HANDLER = new HashMap<>();
    private final AttendanceService attendanceService;
    private boolean isRunning;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;

        isRunning = true;
        AttendanceRecordLoader.loadAttendanceRecordsFromFile();
        initCommandHandler();
    }

    private void initCommandHandler() {
        COMMAND_HANDLER.put(MenuCommand.SAVE_ATTENDANCE_RECORD, this::saveAttendanceRecord);
        COMMAND_HANDLER.put(MenuCommand.MODIFY_ATTENDANCE_RECORD, this::modifyAttendanceRecord);
        COMMAND_HANDLER.put(MenuCommand.PRINT_MONTH_ATTENDANCE_STATISTICS, this::printMonthAttendanceStatistics);
        COMMAND_HANDLER.put(MenuCommand.PRINT_RISK_CREWS, this::printRiskCrews);
        COMMAND_HANDLER.put(MenuCommand.QUIT, this::exitController);
        COMMAND_HANDLER.put(MenuCommand.QUIT_SMALL_CASE, this::exitController);
        COMMAND_HANDLER.put(MenuCommand.NONE, this::printRetryMessage);
    }

    public void run() {
        while (isRunning) {
            MenuCommand command = MenuCommand.from(InputView.scanMenuCommand());
            COMMAND_HANDLER.get(command).run();
        }
    }

    private void saveAttendanceRecord() {
        ExceptionHandler.printErrorMessageWithoutExitProgram(() -> {
            String nickname = InputView.scanNickname();
            String time = InputView.scanAttendanceTime();
            SaveAttendanceRequest request = SaveAttendanceRequest.of(nickname, DateTimeUtil.nowDate(), time);
            SaveAttendanceRecordResponse response = attendanceService.saveAttendanceRecord(request);

            OutputView.printSavedAttendanceRecord(response);
        });
    }

    private void modifyAttendanceRecord() {
        ExceptionHandler.printErrorMessageWithoutExitProgram(() -> {
            String nickname = InputView.scanNicknameToModify();
            LocalDate today = DateTimeUtil.nowDate();
            int dayToModify = InputView.scanDayToModify();
            String timeToModify = InputView.scanTimeToModify();
            ModifyAttendanceRequest request = ModifyAttendanceRequest.of(nickname, today, dayToModify, timeToModify);
            ModifyAttendanceRecordResponse response = attendanceService.modifyAttendanceRecord(request);

            OutputView.printModifiedAttendanceRecord(response);
        });
    }

    private void printMonthAttendanceStatistics() {
        ExceptionHandler.printErrorMessageWithoutExitProgram(() -> {
            String nickname = InputView.scanNickname();
            LocalDate today = DateTimeUtil.nowDate();
            MonthAttendanceStatisticsRequest request = new MonthAttendanceStatisticsRequest(nickname, today);
            MonthAttendanceStatisticsResponse response = attendanceService.getMonthAttendanceStatistics(request);

            OutputView.printMonthAttendanceRecords(response.attendanceRecords());
            OutputView.printMonthAttendanceStatusCount(response.attendanceStatusCount());
            OutputView.printRiskRank(response.riskRank());
        });
    }

    private void printRiskCrews() {
        ExceptionHandler.printErrorMessageWithoutExitProgram(() -> {
            LocalDate today = DateTimeUtil.nowDate();
            RiskCrewsRequest request = new RiskCrewsRequest(today);
            RiskCrewsResponse response = attendanceService.getRiskCrews(request);

            OutputView.printRiskCrews(response);
        });
    }

    private void exitController() {
        isRunning = false;
    }

    private void printRetryMessage() {
        System.out.println("존재하지 않는 커맨드입니다. 다시 입력해주세요.");
    }
}
