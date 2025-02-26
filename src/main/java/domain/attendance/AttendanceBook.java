package domain.attendance;

import domain.crew.Crew;
import exception.ErrorException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;

public class AttendanceBook {

    private final Map<Crew, AttendanceLogs> attendanceBook;

    public AttendanceBook(Map<Crew, AttendanceLogs> attendanceBook) {
        this.attendanceBook = attendanceBook;
    }

    public AttendanceLog registerAttendanceLog(String crewName, LocalDateTime attendDateTime) {
        AttendanceLogs attendanceLogs = findAttendanceLogs(crewName);
        return attendanceLogs.registerLog(attendDateTime);
    }

    public AttendanceLog editAttendanceLog(String crewName, LocalDate editDate, LocalTime editTime) {
        AttendanceLogs attendanceLogs = findAttendanceLogs(crewName);
        return attendanceLogs.editLog(editDate, editTime);
    }

    public AttendanceLogs findAttendanceLogs(String crewName) {
        Crew crew = findCrew(crewName);
        return attendanceBook.get(crew);
    }

    private Crew findCrew(String crewName) {
        return attendanceBook.keySet().stream()
                .filter(crew -> crew.isCrew(crewName))
                .findFirst()
                .orElseThrow(() -> new ErrorException("존재하지 않은 크루입니다."));
    }
}
