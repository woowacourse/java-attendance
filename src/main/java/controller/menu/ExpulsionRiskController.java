package controller.menu;

import domain.attendance.AttendanceBook;
import java.time.LocalDate;

public class ExpulsionRiskController implements AttendanceMenuController {

    private final AttendanceBook attendanceBook;

    public ExpulsionRiskController(AttendanceBook attendanceBook) {
        this.attendanceBook = attendanceBook;
    }

    @Override
    public void run(LocalDate runDate) {

    }
}
