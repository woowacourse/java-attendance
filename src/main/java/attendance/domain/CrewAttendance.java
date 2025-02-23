package attendance.domain;

import static attendance.domain.AttendanceTimeStatus.NULL_TIME;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class CrewAttendance {
    private final String name;
    private final Map<LocalDate, AttendanceTimeStatus> attendances;

    public CrewAttendance(String name) {
        this.name = name;
        this.attendances = new HashMap<>();
    }

    public void add(final LocalDateTime localDateTime) {
        LocalDate date = localDateTime.toLocalDate();
        AttendanceTimeStatus attendanceTimeStatus = new AttendanceTimeStatus(localDateTime);

        if (attendances.containsKey(date)) {
            throw new IllegalArgumentException("[ERROR] 출석 기록이 존재합니다. 출석 수정 기능을 이용하세요.");
        }

        attendances.put(date, attendanceTimeStatus);
    }

    public AttendanceTimeStatus modify(final LocalDate localDate, final AttendanceTimeStatus attendanceTImeStatus) {
        return attendances.put(localDate, attendanceTImeStatus);
    }

    private void updateAttendanceUntil(int today) {
        for (int day = 1; day < today; day++) {
            LocalDate date = LocalDate.of(2024, 12, day);
            createAbsence(day, date);
        }
    }

    private void createAbsence(final int day, final LocalDate date) {
        if (AttendanceChecker.isCampusDay(day) && !hasAttendanceOn(date)) {
            attendances.put(date, new AttendanceTimeStatus(NULL_TIME, NULL_TIME, AttendanceStatus.ABSENCE));
        }
    }

    public boolean hasAttendanceOn(final LocalDate localDate) {
        return attendances.containsKey(localDate);
    }

    public Map<AttendanceStatus, Integer> countAttendanceStatus(final int today) {
        updateAttendanceUntil(today);

        Map<AttendanceStatus, Integer> attendanceStatuses = calculateAttendanceStatus();

        removeTodayStatus(today, attendanceStatuses);

        return attendanceStatuses;
    }

    private Map<AttendanceStatus, Integer> calculateAttendanceStatus() {
        Map<AttendanceStatus, Integer> attendanceStatuses = new EnumMap<>(AttendanceStatus.class);

        for (AttendanceStatus attendanceStatus : AttendanceStatus.values()) {
            long count = attendances.values().stream()
                    .filter(timeStatus -> timeStatus.status().equals(attendanceStatus))
                    .count();

            attendanceStatuses.put(attendanceStatus, (int) count);
        }
        return attendanceStatuses;
    }

    private void removeTodayStatus(final int today, final Map<AttendanceStatus, Integer> attendanceStatuses) {
        LocalDate dateOfToday = LocalDate.of(2024, 12, today);
        if (!hasAttendanceOn(dateOfToday)) {
            return;
        }

        AttendanceStatus status = attendances.get(dateOfToday).status();

        attendanceStatuses.put(status, attendanceStatuses.get(status) - 1);
    }

    public boolean isNameMatch(String anotherName) {
        return this.name.equals(anotherName);
    }

    public Map<LocalDate, AttendanceTimeStatus> getAttendances(int today) {
        updateAttendanceUntil(today);
        return Collections.unmodifiableMap(attendances);
    }

    public String getName() {
        return name;
    }
}
