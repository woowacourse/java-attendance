package domain;

import static util.Constants.*;

import dto.AttendanceLog;
import dto.ModifyingResult;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class AttendanceRecord {
    private static final String ATTENDANCE_ALREADY_EXISTED_ERROR = "이미 출석 기록이 존재합니다. 수정 메뉴를 이용해주세요.";
    private static final LocalDate YESTERDAY = TODAY.minusDays(1);
    private static final LocalTime ABSENT_CONSIDERING_TIME = LocalTime.of(15, 0);
    private static final int ABSENT_CONSIDERING_UNIT = 3;

    private final Set<Attendance> attendanceRecord;

    public AttendanceRecord() {
        this.attendanceRecord = new HashSet<>();
    }

    public boolean contains(Attendance targetAttendance) {
        return attendanceRecord.stream()
                .anyMatch(attendance ->
                        attendance.isSameDateWith(targetAttendance));
    }

    public int countByAttendanceStatus(AttendanceStatus attendanceStatus) {
        return (int) getSortedAllAttendanceRecordUntilYesterday().stream()
                .filter(attendance -> AttendanceStatus.from(attendance) == attendanceStatus)
                .count();
    }

    public int countConsideredAbsent() {
        return countByAttendanceStatus(AttendanceStatus.ABSENT)
                + (countByAttendanceStatus(AttendanceStatus.LATE) / ABSENT_CONSIDERING_UNIT);
    }

    public void add(Attendance attendance) {
        if (contains(attendance)) {
            throw new IllegalArgumentException(ERROR_HEADER + ATTENDANCE_ALREADY_EXISTED_ERROR);
        }
        attendanceRecord.add(attendance);
    }

    public ModifyingResult modify(Attendance newAttendance) {
        Attendance originalAttendance = findSameDateAttendanceBy(newAttendance);
        attendanceRecord.remove(originalAttendance);
        attendanceRecord.add(newAttendance);
        return new ModifyingResult(originalAttendance, newAttendance);
    }

    public AttendanceLog findAllSortedUntilYesterday() {
        return new AttendanceLog(getSortedAllAttendanceRecordUntilYesterday());
    }

    private Attendance findSameDateAttendanceBy(Attendance targetAttendance) {
        return attendanceRecord.stream()
                .filter(attendance ->
                        attendance.isSameDateWith(targetAttendance))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    private List<Attendance> getSortedAllAttendanceRecordUntilYesterday() {
        updateUntilYesterday();
        return attendanceRecord.stream()
                .sorted(Comparator.comparing(Attendance::getDate))
                .toList();
    }

    private void updateUntilYesterday() {
        for (int i = 1; i <= YESTERDAY.getDayOfMonth(); i++) {
            LocalDate targetDate = LocalDate.of(TODAY.getYear(), TODAY.getMonth(), i);
            addWhenNotExistedTo(targetDate);
        }
    }

    private void addWhenNotExistedTo(LocalDate targetDate) {
        Attendance attendanceCandidate = new Attendance(targetDate, ABSENT_CONSIDERING_TIME);
        if (contains(attendanceCandidate) || Attendance.isHoliday(targetDate)) {
            return;
        }
        add(attendanceCandidate);
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
        return Objects.equals(attendanceRecord, other.attendanceRecord);
    }
}
