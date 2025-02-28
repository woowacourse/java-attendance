package controller.menu;

import domain.attendance.AttendanceBook;
import domain.attendance.AttendanceLog;
import domain.attendance.AttendanceResult;
import dto.AttendanceLogDto;
import dto.AttendanceResultDto;
import java.time.LocalDate;
import java.util.List;
import view.InputView;
import view.OutputView;

public class CrewAttendanceController implements AttendanceMenuController {

    private final AttendanceBook attendanceBook;

    public CrewAttendanceController(AttendanceBook attendanceBook) {
        this.attendanceBook = attendanceBook;
    }

    @Override
    public void run(LocalDate runDate) {
        String crewName = InputView.readCrewAttendanceCrewName();
        attendanceBook.findCrew(crewName);

        List<AttendanceLog> attendanceLogHistory = attendanceBook.findCrewAttendanceLogHistory(crewName);
        List<AttendanceLogDto> attendanceLogDtos = attendanceLogHistory.stream()
                .map(AttendanceLog::toDto)
                .toList();
        AttendanceResult attendanceResult = attendanceBook.calculateCrewAttendanceResult(crewName, runDate);
        AttendanceResultDto attendanceResultDto = attendanceResult.toDto();
        OutputView.printCrewAttendance(crewName, attendanceLogDtos, attendanceResultDto, runDate);
    }
}
