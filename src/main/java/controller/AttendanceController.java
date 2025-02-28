package controller;

import controller.dto.ModifyAttendanceRequest;
import controller.dto.MonthAttendanceStatisticsRequest;
import controller.dto.RiskCrewsRequest;
import controller.dto.SaveAttendanceRequest;
import java.time.LocalDate;
import service.AttendanceService;
import service.dto.ModifyAttendanceRecordResponse;
import service.dto.MonthAttendanceStatisticsResponse;
import service.dto.RiskCrewsResponse;
import service.dto.SaveAttendanceRecordResponse;
import util.DateTimeUtil;
import view.InputView;
import view.OutputView;

public class AttendanceController {
    private final AttendanceService attendanceService;
    private boolean isRunning;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;

        isRunning = true;
    }

    public void run() {
        while (isRunning) {
            Command command = Command.from(InputView.scanMenuCommand());
            command.run(this);
        }
    }

    public void saveAttendanceRecord() {
        String nickname = InputView.scanNickname();
        LocalDate today = DateTimeUtil.nowDate();
        String time = InputView.scanAttendanceTime();
        SaveAttendanceRequest request = SaveAttendanceRequest.of(nickname, today, time);
        SaveAttendanceRecordResponse response = attendanceService.saveAttendanceRecord(request);

        OutputView.printSavedAttendanceRecord(response);
    }

    public void modifyAttendanceRecord() {
        String nickname = InputView.scanNicknameToModify();
        LocalDate today = DateTimeUtil.nowDate();
        int dayToModify = InputView.scanDayToModify();
        String timeToModify = InputView.scanTimeToModify();
        ModifyAttendanceRequest request = ModifyAttendanceRequest.of(nickname, today, dayToModify, timeToModify);
        ModifyAttendanceRecordResponse response = attendanceService.modifyAttendanceRecord(request);

        OutputView.printModifiedAttendanceRecord(response);
    }

    public void printMonthAttendanceStatistics() {
        String nickname = InputView.scanNickname();
        LocalDate today = DateTimeUtil.nowDate();
        MonthAttendanceStatisticsRequest request = new MonthAttendanceStatisticsRequest(
                nickname, today);
        MonthAttendanceStatisticsResponse response = attendanceService.bringMonthAttendanceStatistics(request);

        OutputView.printMonthAttendanceRecords(response.attendanceRecords());
        OutputView.printMonthAttendanceStatusCount(response.attendanceStatusCount());
        OutputView.printRiskRank(response.riskRank());
    }

    public void printRiskCrews() {
        LocalDate today = DateTimeUtil.nowDate();
        RiskCrewsRequest request = new RiskCrewsRequest(today);
        RiskCrewsResponse response = attendanceService.bringRiskCrews(request);

        OutputView.printRiskCrews(response);
    }

    public void quit() {
        isRunning = false;
    }

    public void printRetryMessage() {
        System.out.println("존재하지 않는 커맨드입니다. 다시 입력해주세요.");
    }
}
