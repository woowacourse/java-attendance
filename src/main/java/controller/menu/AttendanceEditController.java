package controller.menu;

import domain.attendance.AttendanceBook;
import domain.attendance.AttendanceLog;
import java.time.LocalDate;
import java.time.LocalTime;
import view.InputView;
import view.OutputView;

public class AttendanceEditController implements AttendanceMenuController {

    private final AttendanceBook attendanceBook;

    public AttendanceEditController(AttendanceBook attendanceBook) {
        this.attendanceBook = attendanceBook;
    }

    @Override
    public void run(LocalDate runDate) {
        String crewName = InputView.readAttendanceEditCrewName();
        attendanceBook.findCrew(crewName);

        int editAttendDay = InputView.readAttendanceEditAttendDay();
        LocalDate editDate = LocalDate.of(runDate.getYear(), runDate.getMonth(), editAttendDay);
        LocalTime editTime = InputView.readAttendanceEditAttendTime();
        AttendanceLog oldAttendanceLog = attendanceBook.findCrewAttendanceLog(crewName, editDate);
        AttendanceLog newAttendanceLog = attendanceBook.editCrewAttendanceLog(crewName, editDate, editTime);
        OutputView.printAttendanceEditLog(oldAttendanceLog.toDto(), newAttendanceLog.toDto());
    }
}
