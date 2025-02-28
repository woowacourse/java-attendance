package domain;

import static util.Constants.*;

import dto.AttendanceLog;
import dto.ModifyResult;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class AttendanceRecord {
    private static final String ATTENDANCE_ALREADY_EXISTED_ERROR = "이미 출석 기록이 존재합니다. 수정 메뉴를 이용해주세요.";
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

    public int calculateCountOf(AttendanceStatus attendanceStatus, LocalDate yesterday) {
        return (int) getSortedAllValueUntil(yesterday).stream()
                .filter(attendance -> AttendanceStatus.from(attendance) == attendanceStatus)
                .count();
    }

    public int calculateConsideredAbsentCount(LocalDate yesterday) {
        return calculateCountOf(AttendanceStatus.ABSENT, yesterday)
                + (calculateCountOf(AttendanceStatus.LATE, yesterday) / ABSENT_CONSIDERING_UNIT);
    }

    public void add(Attendance attendance) {
        if(contains(attendance)) {
            throw new IllegalArgumentException(ERROR_HEADER + ATTENDANCE_ALREADY_EXISTED_ERROR);
        }
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
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
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
            addWhenNotExistedTo(targetDate);
        }
    }

    private void addWhenNotExistedTo(LocalDate targetDate) {
        try {
            Attendance attendanceCandidate = new Attendance(targetDate, ABSENT_CONSIDERING_TIME);
            if (contains(attendanceCandidate)) return;
            add(attendanceCandidate);
        }
        catch (IllegalArgumentException ignored) {}
    }
}
