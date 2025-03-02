package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

public class Crew {
    private final Long id;
    private final String name;
    private final Map<LocalDate, AttendanceRecord> attendanceMap;

    public Crew(final Long id, final String name, final Map<LocalDate, AttendanceRecord> attendanceMap) {
        this.id = id;
        this.name = name;
        this.attendanceMap = attendanceMap;
    }

    public String getName() {
        return name;
    }

    public void putAttendance(final LocalDateTime localDateTime) {
        attendanceMap.put(localDateTime.toLocalDate(), AttendanceRecord.of(localDateTime));
    }

    public boolean existAttendance(final LocalDateTime localDateTime) {
        return attendanceMap.containsKey(localDateTime.toLocalDate())
                && !Objects.equals(attendanceMap.get(localDateTime.toLocalDate()).attendanceTime(), null);
    }

    public Map<LocalDate, AttendanceRecord> getAttendanceMap() {
        return Collections.unmodifiableMap(attendanceMap);
    }
}
