package domain;

import java.time.LocalDate;
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

    public AttendanceRecord findMatchingAttendanceDate(final AttendanceDateTime attendanceDateTime) {
        final LocalDate localDate = attendanceDateTime.getDateTime().toLocalDate();
        return attendanceRecords.stream()
                .filter(attendanceRecord -> attendanceRecord.hasAttendanceDate(localDate))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 출석 dateTime 입니다."));
    }

    public void editAttendanceDateTime(final AttendanceRecord before, final AttendanceRecord after) {
        attendanceRecords.remove(before);
        attendanceRecords.add(after);
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

    public AttendanceRecord findAttendanceRecord(final AttendanceDate attendanceDate) {
        final LocalDate date = attendanceDate.getDate();
        return attendanceRecords.stream()
                .filter(attendanceRecord -> attendanceRecord.hasAttendanceDate(date))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 날쩨에는 출석 기록이 존재하지 않습니다."));
    }
}
