package domain;

import util.Dates;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class AttendanceBook {
    private final Map<LocalDate, LocalTime> attendanceBook;

    public AttendanceBook() {
        attendanceBook = new HashMap<>();
    }

    public AttendanceBook(Map<LocalDate, LocalTime> attendanceBook) {
        this.attendanceBook = attendanceBook;
    }

    public LocalTime getAttendanceTimeByDate(LocalDate date) {
        return attendanceBook.getOrDefault(date, Dates.DEFAULT_TIME);
    }

    public void attendance(LocalDate date, LocalTime time) {
        if (isNotOperatingHours(time)) {
            throw new IllegalArgumentException();
        }
        if (Dates.isHoliday(date)) {
            throw new IllegalArgumentException();
        }
        attendanceBook.put(date, time);
    }

    private boolean isNotOperatingHours(LocalTime time) {
        return time.isBefore(LocalTime.of(8, 0)) || time.isAfter(LocalTime.of(23, 0));
    }

    public int getAbsenceCount(LocalDate today) {
        return (int) LocalDate.of(2024, 12, 1)
                .datesUntil(today.plusDays(1))
                .filter(Dates::isNotHoliday)
                .filter(this::isAbsence)
                .count();
    }

    private boolean isAbsence(LocalDate date) {
        return !attendanceBook.containsKey(date) ||
                AttendanceStatus.getAttendanceStatus(date, attendanceBook.get(date))
                        .equals(AttendanceStatus.ABSENCE);
    }

    public int getTardyCount(LocalDate today) {
        return (int) LocalDate.of(2024, 12, 1)
                .datesUntil(today.plusDays(1))
                .filter(Dates::isNotHoliday)
                .filter(attendanceBook::containsKey)
                .filter(this::isTardy)
                .count();
    }

    private boolean isTardy(LocalDate date) {
        return !attendanceBook.containsKey(date) ||
                AttendanceStatus.getAttendanceStatus(date, attendanceBook.get(date))
                        .equals(AttendanceStatus.TARDY);
    }

    public int getAttendCount(LocalDate today) {
        return (int) LocalDate.of(2024, 12, 1)
                .datesUntil(today.plusDays(1))
                .filter(Dates::isNotHoliday)
                .filter(attendanceBook::containsKey)
                .filter(this::isAttend)
                .count();
    }

    private boolean isAttend(LocalDate date) {
        return !attendanceBook.containsKey(date) ||
                AttendanceStatus.getAttendanceStatus(date, attendanceBook.get(date))
                        .equals(AttendanceStatus.ATTEND);
    }

    public RiskStatus getRiskStatus(LocalDate today) {
        return RiskStatus.getRiskStatus(getAbsenceCount(today), getTardyCount(today));
    }



    public AttendanceStatus getAttendanceStatus(LocalDate today) {
        LocalTime dateTime = getAttendanceTimeByDate(today);
        return AttendanceStatus.getAttendanceStatus(today, dateTime);
    }

    public Map<LocalDate, LocalTime> getAttendanceBook() {
        return Collections.unmodifiableMap(attendanceBook);
    }

    public Map<LocalDate, AttendanceStatus> getAttendanceStatuses() {
        return attendanceBook.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> AttendanceStatus.getAttendanceStatus(entry.getKey(), entry.getValue())
                ));
    }
}
