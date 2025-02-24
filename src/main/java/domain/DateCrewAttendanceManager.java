package domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class DateCrewAttendanceManager {
    private final Map<LocalDate, CrewAttendance> dateCrewAttendances;

    public DateCrewAttendanceManager() {
        this.dateCrewAttendances = new HashMap<>();
    }

    public void addAttendance(LocalTime attendanceTime, LocalDate attendanceDate) {
        dateCrewAttendances.put(attendanceDate, new CrewAttendance(attendanceTime, AttendanceStatus.ATTENDANCE));
    }

    public CrewAttendance crewAttendance(LocalDate date) {
        return dateCrewAttendances.get(date);
    }
}
