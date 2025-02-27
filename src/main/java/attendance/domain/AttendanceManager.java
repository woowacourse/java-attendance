package attendance.domain;

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

    public void addCrew(final Crew crew) {
        attendanceBook.put(crew, new AttendanceHistory());
    }

    public Attendance addAttendance(final Crew crew, final LocalDate attendanceDate, final LocalTime attendanceTime) {
        String status = AttendancePolicy.calculateAttendanceStatus(attendanceDate.getDayOfWeek(), attendanceTime);
        AttendanceHistory attendanceHistory = attendanceBook.get(crew);
        Attendance attendance = new Attendance(attendanceDate, attendanceTime, status);
        return attendanceHistory.addAttendance(attendance);
    }

    public Optional<Attendance> findAttendance(final Crew crew, final LocalDate attendanceDate) {
        AttendanceHistory attendanceHistory = attendanceBook.get(crew);
        return attendanceHistory.findAttendance(attendanceDate);
    }
}
