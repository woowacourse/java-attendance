package domain;

import java.util.EnumMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.stream.Collectors;

public class AttendanceRecords {
    private final SortedSet<AttendanceRecord> attendanceRecords;
    private final AttendanceStatusCounts attendanceStatusCounts;

    public AttendanceRecords(final SortedSet<AttendanceRecord> attendanceRecords) {
        this.attendanceRecords = attendanceRecords;
        this.attendanceStatusCounts = this.countAttendanceStatus();
    }

    private AttendanceStatusCounts countAttendanceStatus() {
        final Map<AttendanceStatus, Long> counts = attendanceRecords.stream()
                .collect(Collectors.groupingBy(
                        AttendanceRecord::getAttendanceStatus,
                        () -> new EnumMap<>(AttendanceStatus.class),
                        Collectors.counting()
                ));
        final int attendanceCount = counts.getOrDefault(AttendanceStatus.PRESENT, 0L).intValue();
        final int lateCount = counts.getOrDefault(AttendanceStatus.LATE, 0L).intValue();
        final int absenceCount = counts.getOrDefault(AttendanceStatus.ABSENT, 0L).intValue();

        return new AttendanceStatusCounts(absenceCount, lateCount, attendanceCount);
    }

    public boolean hasAttendanceDateTime(final AttendanceDateTime attendanceDateTime) {
        return attendanceRecords.stream()
                .anyMatch(attendanceRecord -> attendanceRecord.hasAttendanceDateTime(attendanceDateTime));
    }

    public void add(final AttendanceRecord attendanceRecord) {
        attendanceRecords.add(attendanceRecord);
    }

    public AttendanceStatusCounts getAttendanceStatusCounts() {
        return attendanceStatusCounts;
    }
}
