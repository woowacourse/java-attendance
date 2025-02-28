package controller;

import controller.dto.SaveAttendanceRequest;
import java.util.HashMap;
import java.util.Map;
import service.AttendanceService;
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
        initCommandHandler();
    }

    private void initCommandHandler() {
        COMMAND_HANDLER.put(MenuCommand.SAVE_ATTENDANCE_RECORD, this::saveAttendanceRecord);
//        COMMAND_HANDLER.put(MenuCommand.MODIFY_ATTENDANCE_RECORD, this::modifyAttendanceRecord);
//        COMMAND_HANDLER.put(MenuCommand.PRINT_MONTH_ATTENDANCE_STATISTICS, this::printMonthAttendanceStatistics);
//        COMMAND_HANDLER.put(MenuCommand.PRINT_RISK_CREWS, this::printRiskCrews);
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
            SaveAttendanceRequest request = SaveAttendanceRequest.of(
                    InputView.scanNickname(), DateTimeUtil.nowDate(), InputView.scanAttendanceTime());
            SaveAttendanceRecordResponse response = attendanceService.saveAttendanceRecord(request);

            OutputView.printSavedAttendanceRecord(response);
        });
    }

//    private void modifyAttendanceRecord() {
//        ExceptionHandler.printErrorMessageWithoutExitProgram(() -> {
//            ModifyAttendanceRequest request = ModifyAttendanceRequest.of(
//                    InputView.scanNicknameToModify(), DateTimeUtil.nowDate(),
//                    InputView.scanDayToModify(), InputView.scanTimeToModify());
//            ModifyAttendanceRecordResponse response = attendanceService.modifyAttendanceRecord(request);
//
//            OutputView.printModifiedAttendanceRecord(response);
//        });
//    }
//
//    private void printMonthAttendanceStatistics() {
//        ExceptionHandler.printErrorMessageWithoutExitProgram(() -> {
//            MonthAttendanceStatisticsRequest request = new MonthAttendanceStatisticsRequest(
//                    InputView.scanNickname(), DateTimeUtil.nowDate());
//            MonthAttendanceStatisticsResponse response = attendanceService.getMonthAttendanceStatistics(request);
//
//            OutputView.printMonthAttendanceRecords(response.attendanceRecords());
//            OutputView.printMonthAttendanceStatusCount(response.attendanceStatusCount());
//            OutputView.printRiskRank(response.riskRank());
//        });
//    }
//
//    private void printRiskCrews() {
//        ExceptionHandler.printErrorMessageWithoutExitProgram(() -> {
//            RiskCrewsRequest request = new RiskCrewsRequest(DateTimeUtil.nowDate());
//            RiskCrewsResponse response = attendanceService.getRiskCrews(request);
//
//            OutputView.printRiskCrews(response);
//        });
//    }

    private void exitController() {
        isRunning = false;
    }

    private void printRetryMessage() {
        System.out.println("존재하지 않는 커맨드입니다. 다시 입력해주세요.");
    }
}
