package controller;

import service.AttendanceRecordLoader;

public class AttendanceController {

    public void run() {
        AttendanceRecordLoader.loadAttendanceRecordsFromFile();
    }
}
