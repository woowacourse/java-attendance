package attendance;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class AttendanceManager {
    private final Map<Crew, AttendanceHistory> attendanceBook = new HashMap<>();

    public boolean isCrewExists(final Crew crew) {
        return attendanceBook.containsKey(crew);
    }

    public void addCrew(Crew crew) {
        attendanceBook.put(crew, new AttendanceHistory());
    }

    public Attendance addAttendance(final Crew crew, final LocalDate attendanceDate, final LocalTime attendanceTime) {
        String status = AttendancePolicy.calculateAttendanceStatus(attendanceDate.getDayOfWeek(), attendanceTime);
        AttendanceHistory attendanceHistory = attendanceBook.get(crew);
        Attendance attendance = new Attendance(attendanceDate, status);
        attendanceHistory.addAttendance(attendance);
        return new Attendance(attendanceDate, status);
    }

    public Optional<Attendance> findAttendanceByCrewAndDate(final Crew crew, final LocalDate attendanceDate) {
        AttendanceHistory attendanceHistory = attendanceBook.get(crew);
        return attendanceHistory.findAttendanceByDate(attendanceDate);
    }
}
