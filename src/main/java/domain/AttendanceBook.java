package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class AttendanceBook {
    private final Map<LocalDate, LocalTime> attendanceBook;
    private final LocalDate CHRISTMAS = LocalDate.of(2024, 12, 25);


    public AttendanceBook() {
        attendanceBook = new HashMap<>();
    }

    public LocalTime getAttendanceTimeByDate(LocalDate date) {
        return attendanceBook.get(date);
    }

    public void attendance(LocalDate date, LocalTime time) {
        if (isNotOperatingHours(time)) {
            throw new IllegalArgumentException();
        }
        if (isHoliday(date)) {
            throw new IllegalArgumentException();
        }
        attendanceBook.put(date, time);
    }

    public boolean hasAttendanceRecord(LocalDate date) {
        return attendanceBook.containsKey(date);
    }

    private boolean isNotOperatingHours(LocalTime time) {
        return time.isBefore(LocalTime.of(8, 0)) || time.isAfter(LocalTime.of(23, 0));
    }

    public int getAbsenceCount(LocalDate today) {
        return (int) LocalDate.of(2024, 12, 1)
                .datesUntil(today.plusDays(1))
                .filter(date -> !isHoliday(date))
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
                .filter(date -> !isHoliday(date))
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
                .filter(date -> !isHoliday(date))
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

    private boolean isHoliday(LocalDate date) {
        DayOfWeek day = date.getDayOfWeek();
        return isWeekend(day) || isChristmas(date);
    }

    private boolean isChristmas(LocalDate date) {
        return Objects.equals(date, CHRISTMAS);
    }

    private static boolean isWeekend(DayOfWeek day) {
        return day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY;
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
