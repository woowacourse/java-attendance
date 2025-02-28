package controller.menu;

import domain.attendance.AttendanceBook;
import domain.attendance.AttendanceLog;
import exception.ExceptionHandler;
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
        String crewName = fetchAttendanceRegisterCrewName();
        LocalTime attendTime = fetchAttendanceRegisterAttendTime();
        LocalDateTime attendDateTime = LocalDateTime.of(runDate, attendTime);
        AttendanceLog attendanceLog = attendanceBook.registerCrewAttendanceLog(crewName, attendDateTime);
        OutputView.printAttendanceRegisterLog(attendanceLog.toDto());
    }

    private String fetchAttendanceRegisterCrewName() {
        return ExceptionHandler.repeatUntilSuccess(() -> {
            String crewName = InputView.readAttendanceRegisterCrewName();
            attendanceBook.findCrew(crewName);
            return crewName;
        });
    }

    private LocalTime fetchAttendanceRegisterAttendTime() {
        return ExceptionHandler.repeatUntilSuccess(InputView::readAttendanceRegisterAttendTime);
    }
}
