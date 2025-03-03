package attendance.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CrewAttendance {
    private final List<Attendance> attendances;

    public CrewAttendance() {
        this.attendances = new ArrayList<>();
    }

    public void add(final LocalDateTime attendance) {
        validateDuplicateDate(attendance);
        attendances.add(Attendance.of(attendance));
    }

    private void validateDuplicateDate(final LocalDateTime attendance) {
        if (isExistDay(attendance)) {
            throw new IllegalArgumentException("[ERROR] 이미 출석 기록이 존재합니다. 출석 수정 기능을 이용해주세요.");
        }
    }

    public boolean isExistDay(final LocalDateTime targetDateTime) {
        return attendances.stream()
                .anyMatch(attendance -> attendance.isSameDay(Attendance.of(targetDateTime)));
    }

    public void modify(final LocalDateTime newAttendance) {
        Attendance prevAttendance = getAttendanceOn(LocalDate.from(newAttendance));
        attendances.remove(prevAttendance);
        attendances.add(Attendance.of(newAttendance));
    }

    public Attendance getAttendanceOn(final LocalDateTime targetDay) {
        return getAttendanceOn(LocalDate.from(targetDay));
    }

    public Attendance getAttendanceOn(final LocalDate targetDay) {
        return attendances.stream()
                .filter(attendance -> attendance.record().date().equals(targetDay))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 날짜의 출석 기록이 존재하지 않습니다."));
    }

    public Map<AttendanceStatus, Integer> countAttendanceStatusesBefore(final LocalDateTime today) {
        Map<AttendanceStatus, Integer> attendanceStatusCounts = new EnumMap<>(AttendanceStatus.class);

        for (AttendanceStatus attendanceStatus : AttendanceStatus.values()) {
            int count = countAttendanceStatusBefore(today, attendanceStatus);
            attendanceStatusCounts.put(attendanceStatus, count);
        }

        return attendanceStatusCounts;
    }

    private int countAttendanceStatusBefore(final LocalDateTime today, final AttendanceStatus attendanceStatus) {
        if (attendanceStatus.equals(AttendanceStatus.ABSENT)) {
            return countAbsent(today);
        }
        return (int) attendances.stream()
                .filter(attendance -> !attendance.isSameDay(Attendance.of(today)))
                .filter(attendance -> attendance.status().equals(attendanceStatus))
                .count();
    }

    private int countAbsent(final LocalDateTime today) {
        int count = 0;
        for (int day = 1; day < today.getDayOfMonth(); day++) {
            LocalDateTime targetDay = LocalDateTime.of(2024, 12, day, 0, 0);
            count = updateAbsentCount(targetDay, count);
        }
        return count;
    }

    private int updateAbsentCount(final LocalDateTime targetDay, final int count) {
        if (!Campus.isOffDay(targetDay) && !isExistDay(targetDay)) {
            return count + 1;
        }
        return count;
    }

    public Map<LocalDate, AttendanceStatus> getAttendanceStatusesBefore(final LocalDateTime today) {
        Map<LocalDate, AttendanceStatus> attendanceStatuses = new HashMap<>();
        for (int day = 1; day < today.getDayOfMonth(); day++) {
            LocalDateTime targetDay = today.minusDays(day);
            update(attendanceStatuses, targetDay);
        }
        return Collections.unmodifiableMap(attendanceStatuses);
    }

    private void update(final Map<LocalDate, AttendanceStatus> attendanceStatuses, final LocalDateTime targetDay) {
        if (!Campus.isOffDay(targetDay)) {
            attendanceStatuses.put(LocalDate.from(targetDay), getAttendanceStatusOn(targetDay));
        }
    }

    private AttendanceStatus getAttendanceStatusOn(final LocalDateTime targetDay) {
        Campus.validateOperationDay(targetDay);
        if (isExistDay(targetDay)) {
            return getAttendanceOn(targetDay).status();
        }
        return AttendanceStatus.ABSENT;
    }
}
