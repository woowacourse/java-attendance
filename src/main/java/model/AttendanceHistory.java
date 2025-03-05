package model;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import model.policy.CampusOperatingPolicy;

public class AttendanceHistory {

    private final Map<LocalDate, AttendanceDate> attendanceDateTable;
    private final Map<AttendanceDate, AttendanceTime> history;

    public AttendanceHistory() {
        attendanceDateTable = new HashMap<>();
        history = new HashMap<>();
    }

    public AttendanceHistory(final Map<AttendanceDate, AttendanceTime> history) {
        this.attendanceDateTable = getInitialAttendanceDateTable(history);
        this.history = history;
    }

    public void add(final AttendanceDate date, final AttendanceTime time) {
        if (history.containsKey(date)) {
            throw new IllegalArgumentException("[ERROR] 이미 출석 기록이 있습니다. 수정 기능을 이용해주세요.");
        }
        attendanceDateTable.put(date.getDate(), date);
        history.put(date, time);
    }

    public AttendanceTime modify(final AttendanceDate date, final AttendanceTime time) {
        attendanceDateTable.put(date.getDate(), date);
        return history.put(date, time);
    }

    public List<AttendanceHistoryByDate> getHistoryWithAttendanceType(final LocalDate endOfRangeDate) {
        return getRangeDates(endOfRangeDate)
                .filter(CampusOperatingPolicy::isOperatingDate)
                .map(this::getAttendanceHistoryByDate)
                .toList();
    }

    public Map<AttendanceDate, AttendanceTime> getHistory() {
        return Collections.unmodifiableMap(history);
    }

    private Stream<LocalDate> getRangeDates(final LocalDate endOfRange) {
        return LocalDate.of(2024, 12, 1).datesUntil(endOfRange);
    }

    private AttendanceHistoryByDate getAttendanceHistoryByDate(final LocalDate date) {
        if (!attendanceDateTable.containsKey(date)) {
            return AttendanceHistoryByDate.getDefault(date);
        }
        AttendanceDate attendanceDate = attendanceDateTable.get(date);
        AttendanceTime attendanceTime = history.get(attendanceDate);
        return new AttendanceHistoryByDate(date, attendanceTime.getTime(),
                AttendanceType.from(attendanceDate, attendanceTime));
    }

    private Map<LocalDate, AttendanceDate> getInitialAttendanceDateTable(
            final Map<AttendanceDate, AttendanceTime> history) {
        return history.keySet().stream()
                .collect(Collectors.toMap(
                        AttendanceDate::getDate,
                        attendanceDate -> attendanceDate)
                );
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AttendanceHistory that = (AttendanceHistory) o;
        return Objects.equals(history, that.history);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(history);
    }
}
