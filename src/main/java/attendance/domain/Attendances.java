package attendance.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class Attendances {
    private final Map<LocalDate, Attendance> attendances;
    private final AttendanceChecker checker;

    public Attendances() {
        this.attendances = new HashMap<>();
        this.checker = new RegularAttendanceChecker();
    }

    public void attend(final LocalDateTime attendTime) {
        if (isAttended(attendTime.toLocalDate())) {
            throw new IllegalArgumentException("출석 기록이 존재합니다. 출석 수정 기능을 이용하세요.");
        }

        updateAttendance(attendTime);

    }

    public Attendance modify(final LocalDateTime modifyTime) {
        if (!isAttended(modifyTime.toLocalDate())) {
            throw new IllegalArgumentException("해당 날짜에 출석 기록이 없습니다.");
        }
        Attendance prevAttendance = attendances.get(modifyTime.toLocalDate());
        updateAttendance(modifyTime);
        return prevAttendance;
    }

    private void updateAttendance(LocalDateTime time) {
        Attendance attendance = new Attendance(time);
        attendances.put(time.toLocalDate(), attendance);
    }

    public Map<LocalDate, Attendance> getAttendances(int today) {
        updateTimestampUntil(today);
        return Collections.unmodifiableMap(attendances);
    }

    public Map<AttendanceStatus, Integer> countAttendanceStatus(final int today) {
        updateTimestampUntil(today);

        Map<AttendanceStatus, Integer> attendanceStatuses = calculateAttendanceStatus(today);

        return attendanceStatuses;
    }

    private Map<AttendanceStatus, Integer> calculateAttendanceStatus(int today) {
        Map<AttendanceStatus, Integer> attendanceStatuses = new EnumMap<>(AttendanceStatus.class);

        for (AttendanceStatus attendanceStatus : AttendanceStatus.values()) {
            attendanceStatuses.put(attendanceStatus, (int) countAttendanceStatus(attendanceStatus, today));
        }
        return attendanceStatuses;
    }

    private long countAttendanceStatus(AttendanceStatus attendanceStatus, int today) {
        return attendances.entrySet().stream()
                .filter(entry -> entry.getKey().isBefore(LocalDate.of(2024, 12, today)))
                .map(entry -> entry.getValue())
                .filter(attendance -> attendance.attendanceStatus().equals(attendanceStatus))
                .count();
    }

    private boolean isAttended(LocalDate localDate) {
        return attendances.containsKey(localDate);
    }

    private void fillEmptyAttendance(final int day, final LocalDate date) {
        if (checker.isCampusDay(day) && !attendances.containsKey(date)) {
            attendances.put(date, new Attendance(null, AttendanceStatus.ABSENCE));
        }
    }

    private void updateTimestampUntil(int today) {
        for (int day = 1; day < today; day++) {
            LocalDate date = LocalDate.of(2024, 12, day);
            fillEmptyAttendance(day, date);
        }
    }
}
