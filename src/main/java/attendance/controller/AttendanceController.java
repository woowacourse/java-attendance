package attendance.controller;

import attendance.Initializer;
import attendance.domain.AttendanceBook;
import java.time.LocalDate;

public class AttendanceController {

    private final AttendanceBook attendanceBook;
    private final LocalDate systemDate;

    public AttendanceController(Initializer initializer) {
        this.attendanceBook = initializer.initAttendanceBook();
        this.systemDate = initializer.initSystemDate();
    }

    public void run() {
    }
}
