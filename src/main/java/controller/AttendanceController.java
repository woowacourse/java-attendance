package controller;

import controller.dto.SaveAttendanceRequest;
import service.AttendanceRecordLoader;
import service.AttendanceService;
import util.DateTimeUtil;
import view.InputView;
import view.OutputView;

public class AttendanceController {
    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        init();
        this.attendanceService = attendanceService;
    }

    private void init() {
        AttendanceRecordLoader.loadAttendanceRecordsFromFile();
    }

    public void run() {
        boolean isRunning = true;
        while (isRunning) {
            String option = InputView.scanMainMenuOption();
            switch (MainMenuCommand.from(option)) {
                case SAVE_ATTENDANCE_RECORD -> saveAttendanceRecord();
                case MODIFY_ATTENDANCE_RECORD -> modifyAttendanceRecord();
                case PRINT_MONTH_ATTENDANCE_STATISTICS -> printMonthAttendanceStatistics();
                case PRINT_CREWS_ON_RISK_OF_EXPELLED -> printCrewsOnRiskOfExpelled();
                case QUIT -> isRunning = false;
            }
        }
    }

    private void saveAttendanceRecord() {
        String nickname = InputView.scanNickname();
        String time = InputView.scanAttendanceTime();
        OutputView.printSavedAttendanceRecord(
                attendanceService.saveAttendanceRecord(
                        SaveAttendanceRequest.of(nickname, DateTimeUtil.nowDate(), time)));
    }

    private void modifyAttendanceRecord() {
    }

    private void printMonthAttendanceStatistics() {
    }

    private void printCrewsOnRiskOfExpelled() {
    }
}
