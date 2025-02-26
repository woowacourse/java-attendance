package domain;

import dto.AttendanceCount;
import dto.AttendanceLog;
import dto.ModifyResult;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class AttendanceRecord {
    private final Set<Attendance> value;

    public AttendanceRecord() {
        this.value = new HashSet<>();
    }

    public boolean contains(Attendance targetAttendance) {
        return value.stream()
                .anyMatch(attendance ->
                        attendance.isSameDateWith(targetAttendance));
    }

    public AttendanceLog findAllSortedUntil(LocalDate yesterday) {
        return new AttendanceLog(getAllSortedUntil(yesterday));
    }

    private List<Attendance> getAllSortedUntil(LocalDate yesterday) {
        updateUntil(yesterday);
        return value.stream()
                .sorted(Comparator.comparing(Attendance::getDate))
                .toList();
    }

    private void updateUntil(LocalDate yesterday) {
        for(int i=1; i<=yesterday.getDayOfMonth(); i++) {
            DayOfWeek dayOfWeek = LocalDate.of(2024, 12, i).getDayOfWeek();
            if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY || i == 25) {
                continue;
            }
            Attendance attendanceCandidate = new Attendance(LocalDateTime.of(2024, 12, i, 15, 0));
            if (contains(attendanceCandidate)) {
                continue;
            }
            value.add(attendanceCandidate);
        }
    }

    public AttendanceCount calculateCount(LocalDate yesterday) {
        List<Attendance> allSortedUntil = getAllSortedUntil(yesterday);
        int attendCount = 0;
        int lateCount = 0;
        int absentCount = 0;

        for (Attendance attendance : allSortedUntil) {
            if(AttendanceStatus.from(attendance) == AttendanceStatus.ATTEND) {
                attendCount++;
            }
            if(AttendanceStatus.from(attendance) == AttendanceStatus.LATE) {
                lateCount++;
            }
            if(AttendanceStatus.from(attendance) == AttendanceStatus.ABSENT) {
                absentCount++;
            }
        }
        return new AttendanceCount(attendCount, lateCount, absentCount);
    }

    public void add(Attendance attendance) {
        value.add(attendance);
    }

    public ModifyResult modify(Attendance newAttendance) {
        Attendance originalAttendance = findSameDateAttendanceBy(newAttendance);
        value.remove(originalAttendance);
        value.add(newAttendance);
        return new ModifyResult(originalAttendance, newAttendance);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        AttendanceRecord other = (AttendanceRecord) object;
        return Objects.equals(value, other.value);
    }

    private Attendance findSameDateAttendanceBy(Attendance targetAttendance) {
        return value.stream()
                .filter(attendance ->
                        attendance.isSameDateWith(targetAttendance))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }
}
