package controller;

import dto.AttendanceModifyRequest;
import dto.AttendanceRequest;
import dto.OptionRequest;
import service.AttendanceService;
import util.ExceptionHandler;
import view.InputView;
import view.OutputView;

public class AttendanceController {
    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
        ResourceLoader.loadCrewRepository();
    }

    public void run() {
        boolean isRunning = true;
        do {
            isRunning = executeMainMenu();
        } while (isRunning);
    }

    private boolean executeMainMenu() {
        boolean isRunning = true;
        OptionRequest optionRequest = InputView.scanOption();
        switch (optionRequest.option()) {
            case "1" -> insertAttendanceRecord();
            case "2" -> modifyAttendanceRecord();
            case "3" -> printMonthAttendanceRecords();
            case "4" -> printCrewsAlmostExpelled();
            case "q", "Q" -> isRunning = false;
            default -> System.out.println("존재하지 않는 옵션입니다.");
        }
        return isRunning;
    }

    private void insertAttendanceRecord() {
        ExceptionHandler.printErrorMessageWithoutExitSystem(() -> {
            AttendanceRequest request = InputView.scanAttendance();
            OutputView.printAttendanceResult(attendanceService.insertAttendanceRecord(request));
        });
    }

    private void modifyAttendanceRecord() {
        ExceptionHandler.printErrorMessageWithoutExitSystem(() -> {
            AttendanceModifyRequest request = InputView.scanModify();
            OutputView.printModifiedResult(attendanceService.modifyAttendanceRecord(request));
        });
    }

    private void printMonthAttendanceRecords() {
        ExceptionHandler.printErrorMessageWithoutExitSystem(() -> {
            String nickname = InputView.scanNickname();
            OutputView.printMonthAttendanceRecords(attendanceService.getMonthAttendanceRecordsResult(nickname));
        });
    }

    private void printCrewsAlmostExpelled() {
        ExceptionHandler.printErrorMessageWithoutExitSystem(() -> {
            OutputView.printCrewsAlmostExpelled(attendanceService.getCrewsAlmostExpelled());
        });
    }
}
