package attendance.domain;

import static attendance.domain.HourMinute.NULL_TIME;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class Attendance {
    private final String name;
    private final Map<LocalDate, HourMinute> timestamps;

    public Attendance(String name) {
        this.name = name;
        this.timestamps = new HashMap<>();
    }

    public void add(final LocalDateTime localDateTime) {
        LocalDate date = localDateTime.toLocalDate();
        HourMinute hourMinute = new HourMinute(localDateTime);

        if (timestamps.containsKey(date)) {
            throw new IllegalArgumentException("출석 기록이 존재합니다. 출석 수정 기능을 이용하세요.");
        }

        timestamps.put(date, hourMinute);
    }

    public HourMinute modify(final LocalDate localDate, final HourMinute hourMinute) {
        return timestamps.put(localDate, hourMinute);
    }

    public boolean isNameMatch(String anotherName) {
        return this.name.equals(anotherName);
    }

    public boolean hasTimeStamp(final LocalDate localDate) {
        return timestamps.containsKey(localDate);
    }

    public Map<AttendanceStatus, Integer> countAttendanceStatus(final int today) {
        updateTimestampUntil(today);

        Map<AttendanceStatus, Integer> attendanceStatuses = calculateAttendanceStatus();

        removeTodayStatus(today, attendanceStatuses);

        return attendanceStatuses;
    }

    private Map<AttendanceStatus, Integer> calculateAttendanceStatus() {
        Map<AttendanceStatus, Integer> attendanceStatuses = new EnumMap<>(AttendanceStatus.class);

        for (AttendanceStatus attendanceStatus : AttendanceStatus.values()) {
            long count = timestamps.values().stream()
                    .filter(hourMinute -> hourMinute.attendanceStatus().equals(attendanceStatus))
                    .count();

            attendanceStatuses.put(attendanceStatus, (int) count);
        }
        return attendanceStatuses;
    }

    private void removeTodayStatus(final int today, final Map<AttendanceStatus, Integer> attendanceStatuses) {
        LocalDate dateOfToday = LocalDate.of(2024, 12, today);
        if (!timestamps.containsKey(dateOfToday)) {
            return;
        }
        AttendanceStatus status = timestamps.get(dateOfToday).attendanceStatus();

        attendanceStatuses.put(status, attendanceStatuses.get(status) - 1);
    }

    private void updateTimestampUntil(int today) {
        for (int day = 1; day < today; day++) {
            LocalDate date = LocalDate.of(2024, 12, day);
            updateTimeStamp(day, date);
        }
    }

    private void updateTimeStamp(final int day, final LocalDate date) {
        if (AttendanceChecker.isCampusDay(day) && !timestamps.containsKey(date)) {
            timestamps.put(date, new HourMinute(NULL_TIME, NULL_TIME, AttendanceStatus.ABSENCE));
        }
    }

    public Map<LocalDate, HourMinute> getTimestamps(int today) {
        updateTimestampUntil(today);
        return Collections.unmodifiableMap(timestamps);
    }

    public String getName() {
        return name;
    }
}
