package domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import strategy.NowDateStrategy;

public class DateCrewAttendanceManager {

    private final Map<AttendanceDate, CrewAttendance> dateCrewAttendances;
    private final NowDateStrategy nowDateStrategy;

    public DateCrewAttendanceManager(NowDateStrategy nowDateStrategy) {
        this.nowDateStrategy = nowDateStrategy;
        this.dateCrewAttendances = new HashMap<>();
    }

    public void addAttendance(LocalTime time) {
        AttendanceDate attendanceDate = new AttendanceDate(nowDateStrategy.now());
        AttendanceTime attendanceTime = new AttendanceTime(time);
        dateCrewAttendances.put(attendanceDate, new CrewAttendance(attendanceDate, attendanceTime));
    }

    public CrewAttendance crewAttendance(LocalDate date) {
        AttendanceDate attendanceDate = new AttendanceDate(date);
        return dateCrewAttendances.get(attendanceDate);
    }
}
