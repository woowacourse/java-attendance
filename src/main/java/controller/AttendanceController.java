package controller;

import service.AttendanceRecordLoader;
import service.AttendanceService;
import view.InputView;

public class AttendanceController {
    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    public void run() {
        AttendanceRecordLoader.loadAttendanceRecordsFromFile();
//        saveAttendanceRecord();

    }

    private void saveAttendanceRecord() {
        String nickname = InputView.scanNickname();
        String time = InputView.scanAttendanceTime();

//        OutputView.printSavedAttendanceRecord(new SavedAttendanceRecord(
//                LocalDateTime.of(2025, 2, 3, 10, 0),
//                AttendanceStatus.ATTENDANCE
//        ));
    }
}
