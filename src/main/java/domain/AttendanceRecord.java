package domain;

import static util.Constants.*;

import dto.AttendanceCount;
import dto.AttendanceLog;
import dto.ModifyResult;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import util.DateTimeManager;

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
        return new AttendanceLog(getSortedAllValueUntil(yesterday));
    }

    public AttendanceCount calculateCount(LocalDate yesterday) {
        List<Attendance> allSortedUntil = getSortedAllValueUntil(yesterday);
        int attendCount = 0;
        int lateCount = 0;
        int absentCount = 0;

        for (Attendance attendance : allSortedUntil) {
            if (AttendanceStatus.from(attendance) == AttendanceStatus.ATTEND) {
                attendCount++;
            }
            if (AttendanceStatus.from(attendance) == AttendanceStatus.LATE) {
                lateCount++;
            }
            if (AttendanceStatus.from(attendance) == AttendanceStatus.ABSENT) {
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

    private List<Attendance> getSortedAllValueUntil(LocalDate yesterday) {
        updateUntil(yesterday);
        return value.stream()
                .sorted(Comparator.comparing(Attendance::getDate))
                .toList();
    }

    private void updateUntil(LocalDate yesterday) {
        for (int i = 1; i <= yesterday.getDayOfMonth(); i++) {
            LocalDate targetDate = LocalDate.of(START_YEAR, START_MONTH, i);
            Attendance attendanceCandidate = new Attendance(targetDate, ABSENT_CONSIDERING_TIME);
            if (DateTimeManager.isHoliday(targetDate) || contains(attendanceCandidate)) {
                continue;
            }
            value.add(attendanceCandidate);
        }
    }
}
