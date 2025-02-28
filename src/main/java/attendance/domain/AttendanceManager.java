package attendance.domain;

import static attendance.domain.AttendancePolicy.calculateAttendanceStatus;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
        AttendanceStatus status = calculateAttendanceStatus(attendanceDate.getDayOfWeek(), attendanceTime);
        AttendanceHistory attendanceHistory = attendanceBook.get(crew);
        Attendance attendance = new Attendance(attendanceDate, attendanceTime, status);
        return attendanceHistory.addAttendance(attendance);
    }

    public Optional<Attendance> findAttendance(final Crew crew, final LocalDate attendanceDate) {
        AttendanceHistory attendanceHistory = attendanceBook.get(crew);
        return attendanceHistory.findAttendance(attendanceDate);
    }

    public Attendance modifyAttendance(final Crew crew,
                                       final LocalDate dateToModify,
                                       final LocalTime modificationTime) {
        AttendanceStatus modificationStatus = calculateAttendanceStatus(dateToModify.getDayOfWeek(), modificationTime);
        AttendanceHistory attendanceHistory = attendanceBook.get(crew);
        Attendance modifiedAttendance = new Attendance(dateToModify, modificationTime, modificationStatus);
        return attendanceHistory.modifyAttendance(modifiedAttendance);
    }

    public List<Attendance> getMonthlyAttendances(final LocalDate today, final Crew crew) {
        AttendanceHistory attendanceHistory = attendanceBook.get(crew);
        return attendanceHistory.getMonthlyAttendances(today);
    }

    public AttendanceStatistics getAttendanceStatistics(final LocalDate today, final Crew crew) {
        AttendanceHistory attendanceHistory = attendanceBook.get(crew);
        return attendanceHistory.getAttendanceStatistics(today);
    }

    public Map<Crew, AttendanceStatistics> getDangerousCrews(final LocalDate today) {
        Map<Crew, AttendanceStatistics> dangerousCrewsStatistics = new HashMap<>();
        for (Entry<Crew, AttendanceHistory> entry : attendanceBook.entrySet()) {
            AttendanceStatistics attendanceStatistics = entry.getValue().getAttendanceStatistics(today);
            CrewStatus crewStatus = attendanceStatistics.calculateCrewStatus();
            if (!crewStatus.equals(CrewStatus.NONE)) {
                dangerousCrewsStatistics.put(entry.getKey(), attendanceStatistics);
            }
        }
        return dangerousCrewsStatistics;
    }
}
