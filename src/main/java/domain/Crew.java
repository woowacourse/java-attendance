package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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

    public AttendanceRecord putAttendance(final LocalDateTime localDateTime) {
        final AttendanceRecord attendanceRecord = AttendanceRecord.of(localDateTime);
        attendanceMap.put(localDateTime.toLocalDate(), attendanceRecord);
        return attendanceRecord;
    }

    public boolean existAttendance(final LocalDate localDate) {
        return attendanceMap.containsKey(localDate);
    }

    public AttendanceRecord getAttendanceRecordByDate(final LocalDate localDate) {
        return attendanceMap.get(localDate);
    }

    public List<AttendanceRecord> getAttendanceRecords() {
        return attendanceMap.values().stream().toList();
    }
}
