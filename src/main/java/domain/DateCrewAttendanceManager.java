package domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import strategy.NowDateStrategy;

public class DateCrewAttendanceManager {
    private final Map<LocalDate, CrewAttendance> dateCrewAttendances;
    private final NowDateStrategy nowDateStrategy;

    public DateCrewAttendanceManager(NowDateStrategy nowDateStrategy) {
        this.nowDateStrategy = nowDateStrategy;
        this.dateCrewAttendances = new HashMap<>();
    }

    public void addAttendance(LocalTime attendanceTime) {
        LocalDate attendanceDate = nowDateStrategy.now();
        dateCrewAttendances.put(attendanceDate, new CrewAttendance(attendanceTime, AttendanceStatus.ATTENDANCE));
    }

    public CrewAttendance crewAttendance(LocalDate date) {
        return dateCrewAttendances.get(date);
    }
}
