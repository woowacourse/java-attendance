package controller;

import domain.Attendance;
import domain.AttendanceStatus;
import domain.AttendanceSystem;
import domain.Crew;
import domain.ExpulsionStatus;
import dto.AttendanceResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import util.FileManager;
import view.InputView;
import view.OutputView;

public class AttendanceController {
    private static final String ATTENDANCE_HISTORY_FILE_NAME = "attendances.csv";

    private final InputView inputView;
    private final OutputView outputView;
    private final AttendanceSystem attendanceSystem;

    public AttendanceController(final InputView inputView, final OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
        final List<String> attendanceLines = FileManager.readFileLines(ATTENDANCE_HISTORY_FILE_NAME);
        attendanceLines.remove(0);
        attendanceSystem = AttendanceSystem.of(attendanceLines, LocalDate.of(2024, 12, 26));
    }

    public void run() {
//        responseCrewAttendanceHistory();
//        attendance();
        updateAttendance();
    }

    private void attendance() {
        outputView.printAddAttendanceCrewName();
        final String crewName = inputView.readCrewName();
        attendanceSystem.validateCrewByName(crewName);
        outputView.printAddAttendanceDate();
        final LocalTime localDateTime = inputView.readTime();
        final Attendance attendance = attendanceSystem.attendance(crewName, convertLocalDateTime(localDateTime));
        final AttendanceResponse attendanceResponse = convertAttendanceToResponse(attendance);
        outputView.printCrewAttendances(List.of(attendanceResponse));
    }

    private LocalDateTime convertLocalDateTime(final LocalTime time) {
        return LocalDateTime.of(LocalDate.of(2024, 12, LocalDate.now().getDayOfMonth()), time);
    }

    private void updateAttendance() {
        outputView.printUpdateAttendanceCrewName();
        final String crewName = inputView.readCrewName();
        attendanceSystem.validateCrewByName(crewName);
        outputView.printUpdateAttendanceDayOfMonth();
        final int dayOfMonth = inputView.readDayOfMonth();
        attendanceSystem.validateUpdateAttendanceDay(crewName, dayOfMonth);
        outputView.printUpdateAttendanceDate();
        final LocalTime targetTime = inputView.readTime();
        final Attendance beforeAttendance = attendanceSystem.findAttendanceByDate(crewName, dayOfMonth);
        final Attendance afterAttendance = attendanceSystem.updateAttendanceByCrewNameAndDay(targetTime, crewName,
                dayOfMonth);
        outputView.printUpdateAttendanceResult(convertAttendanceToResponse(beforeAttendance),
                convertAttendanceToResponse(afterAttendance));
    }


    private void responseCrewAttendanceHistory() {
        final String crewName = inputView.readCrewName();
        final Crew crew = attendanceSystem.findCrewByName(crewName);
        outputView.printAttendanceHistoryTitle(crewName);
        final List<AttendanceResponse> attendanceResponses = convertAttendancesToResponses(crew.getAttendances());
        outputView.printCrewAttendances(attendanceResponses);
        final Map<AttendanceStatus, Integer> attendanceStatistics = crew.calculateAttendanceStatistics();
        outputView.printAttendancesStatistics(attendanceStatistics);
        final ExpulsionStatus expulsionStatus = crew.calculateExpulsionStatus();
        outputView.printCrewExpulsionStatus(expulsionStatus);

    }

    private List<AttendanceResponse> convertAttendancesToResponses(final List<Attendance> attendances) {
        return attendances.stream()
                .map(this::convertAttendanceToResponse)
                .toList();
    }

    private AttendanceResponse convertAttendanceToResponse(final Attendance attendance) {
        return new AttendanceResponse(attendance.getDateTime(), attendance.calculateStatus(), attendance.isEmpty());
    }

}
