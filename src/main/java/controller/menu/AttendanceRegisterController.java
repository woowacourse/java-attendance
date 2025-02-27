package controller.menu;

import domain.attendance.AttendanceBook;
import domain.attendance.AttendanceLog;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import view.InputView;
import view.OutputView;

public class AttendanceRegisterController implements AttendanceMenuController {

    private final AttendanceBook attendanceBook;

    public AttendanceRegisterController(AttendanceBook attendanceBook) {
        this.attendanceBook = attendanceBook;
    }

    @Override
    public void run(LocalDate runDate) {
        String crewName = InputView.readAttendanceRegisterCrewName();
        attendanceBook.findCrew(crewName);

        LocalTime attendTime = InputView.readAttendanceRegisterAttendTime();
        LocalDateTime attendDateTime = LocalDateTime.of(runDate, attendTime);
        AttendanceLog attendanceLog = attendanceBook.registerAttendanceLog(crewName, attendDateTime);
        OutputView.printAttendanceRegisterLog(attendanceLog.toDto());
    }
}
