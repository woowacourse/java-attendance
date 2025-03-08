package domain.record;

import domain.AttendanceStatus;
import domain.DisciplinaryStatus;
import domain.dateTime.AttendanceDateTime;
import java.time.LocalDate;
import java.util.EnumMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.stream.Collectors;

public class AttendanceRecords {
    private final SortedSet<AttendanceRecord> attendanceRecords;
    private AttendanceStatusCounts attendanceStatusCounts;

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

    public AttendanceRecord findAttendanceRecord(final LocalDate date) {
        return attendanceRecords.stream()
                .filter(attendanceRecord -> attendanceRecord.hasAttendanceDate(date))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 날짜에는 출석 기록이 존재하지 않습니다."));
    }

    public AttendanceRecord findByDate(final LocalDate date) {
        return attendanceRecords.stream()
                .filter(attendanceRecord -> attendanceRecord.hasAttendanceDate(date))
                .findAny()
                .orElse(null);
    }

    public void editAttendanceDateTime(final AttendanceRecord before, final AttendanceRecord after) {
        attendanceRecords.remove(before);
        attendanceRecords.add(after);
        updateCountAttendanceStatus();
    }

    public void updateCountAttendanceStatus() {
        attendanceStatusCounts = this.countAttendanceStatus();
    }

    public boolean hasAttendanceDateTime(final AttendanceDateTime attendanceDateTime) {
        return attendanceRecords.stream()
                .anyMatch(attendanceRecord -> attendanceRecord.hasAttendanceDateTime(attendanceDateTime));
    }


    public void add(final AttendanceRecord attendanceRecord) {
        attendanceRecords.add(attendanceRecord);
        updateCountAttendanceStatus();
    }


    public AttendanceStatusCounts getAttendanceStatusCounts() {
        return attendanceStatusCounts;
    }

    public DisciplinaryStatus findDisciplinaryStatus() {
        final int absence = attendanceStatusCounts.getAbsence();
        final int late = attendanceStatusCounts.getLate();
        return DisciplinaryStatus.findByAbsenceAndLatenessCount(absence, late);
    }

    public void updateAttendanceRecord(final AttendanceDateTime attendanceDateTime) {
        if (hasAttendanceDateTime(attendanceDateTime)) {
            throw new IllegalArgumentException("이미 출석을 했습니다. 다시 출석을 할 수 없으며 수정은 원할 시 수정기능을 사용해주세요.");
        }
        final AttendanceRecord attendanceRecord = new AttendanceRecord(attendanceDateTime);

        attendanceRecords.remove(attendanceRecord);
        attendanceRecords.add(attendanceRecord);
        updateCountAttendanceStatus();
    }
}
