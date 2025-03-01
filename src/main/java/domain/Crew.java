package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

public class Crew {
    private final Long id;
    private final String name;
    private final Map<LocalDate, Attendance> attendanceMap;

    public Crew(final Long id, final String name, final Map<LocalDate, Attendance> attendanceMap) {
        this.id = id;
        this.name = name;
        this.attendanceMap = attendanceMap;
    }

    public String getName() {
        return name;
    }

    public void putAttendance(final LocalDateTime localDateTime) {
        attendanceMap.put(localDateTime.toLocalDate(), Attendance.of(localDateTime));
    }
}
