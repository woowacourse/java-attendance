package controller.menu;

import controller.dto.AttendanceLogDtoConverter;
import domain.attendance.AttendanceBook;
import domain.attendance.AttendanceLog;
import exception.ExceptionHandler;
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
        String crewName = fetchAttendanceEditCrewName();
        LocalDate editDate = fetchAttendanceEditAttendDate(runDate);
        LocalTime editTime = fetchAttendanceEditAttendTime();
        AttendanceLog oldAttendanceLog = attendanceBook.findCrewAttendanceLog(crewName, editDate);
        AttendanceLog newAttendanceLog = attendanceBook.editCrewAttendanceLog(crewName, editDate, editTime);
        OutputView.printAttendanceEditLog(AttendanceLogDtoConverter.toDto(oldAttendanceLog), AttendanceLogDtoConverter.toDto(newAttendanceLog));
    }

    private String fetchAttendanceEditCrewName() {
        return ExceptionHandler.repeatUntilSuccess(() -> {
            String crewName = InputView.readAttendanceEditCrewName();
            attendanceBook.findCrew(crewName);
            return crewName;
        });
    }

    private LocalDate fetchAttendanceEditAttendDate(LocalDate runDate) {
        int editAttendDay = ExceptionHandler.repeatUntilSuccess(InputView::readAttendanceEditAttendDay);
        return LocalDate.of(runDate.getYear(), runDate.getMonth(), editAttendDay);
    }

    private LocalTime fetchAttendanceEditAttendTime() {
        return ExceptionHandler.repeatUntilSuccess(InputView::readAttendanceEditAttendTime);
    }
}
