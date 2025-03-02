package domain;

import util.Dates;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class AttendanceBook {
    private final Map<AttendanceDate, AttendanceTime> attendanceBook;

    public AttendanceBook() {
        attendanceBook = new HashMap<>();
    }

    public AttendanceBook(Map<AttendanceDate, AttendanceTime> attendanceBook) {
        this.attendanceBook = attendanceBook;
    }

    public Optional<AttendanceTime> getAttendanceTimeByDate(AttendanceDate date) {
        return Optional.ofNullable(attendanceBook.get(date));
    }

    public void attendance(AttendanceDate date, AttendanceTime time) {
        attendanceBook.put(date, time);
    }

    public boolean hasAttendanceRecord(AttendanceDate date) {
        return attendanceBook.containsKey(date);
    }

    public int getAbsenceCount(LocalDate today) {
        return (int) LocalDate.of(2024, 12, 1)
                .datesUntil(today.plusDays(1))
                .filter(Dates::isNotHoliday)
                .map(AttendanceDate::new)
                .filter(this::isAbsence)
                .count();
    }

    public int getAttendCount(LocalDate today) {
        return (int) LocalDate.of(2024, 12, 1)
                .datesUntil(today.plusDays(1))
                .filter(Dates::isNotHoliday)
                .map(AttendanceDate::new)
                .filter(attendanceBook::containsKey)
                .filter(this::isAttend)
                .count();
    }

    public int getTardyCount(LocalDate today) {
        return (int) LocalDate.of(2024, 12, 1)
                .datesUntil(today.plusDays(1))
                .filter(Dates::isNotHoliday)
                .map(AttendanceDate::new)
                .filter(attendanceBook::containsKey)
                .filter(this::isTardy)
                .count();
    }

    private boolean isAbsence(AttendanceDate date) {
        return !attendanceBook.containsKey(date) ||
                AttendanceStatus.getAttendanceStatus(date, attendanceBook.get(date))
                        .equals(AttendanceStatus.ABSENCE);
    }

    private boolean isTardy(AttendanceDate date) {
        return !attendanceBook.containsKey(date) ||
                AttendanceStatus.getAttendanceStatus(date, attendanceBook.get(date))
                        .equals(AttendanceStatus.TARDY);
    }

    private boolean isAttend(AttendanceDate date) {
        return !attendanceBook.containsKey(date) ||
                AttendanceStatus.getAttendanceStatus(date, attendanceBook.get(date))
                        .equals(AttendanceStatus.ATTEND);
    }

    public RiskStatus getRiskStatus(LocalDate today) {
        return RiskStatus.getRiskStatus(getAbsenceCount(today), getTardyCount(today));
    }


    public AttendanceStatus getAttendanceStatus(AttendanceDate today) {
        return getAttendanceTimeByDate(today)
                .map(attendanceTime -> AttendanceStatus.getAttendanceStatus(today, attendanceTime))
                .orElse(AttendanceStatus.ABSENCE);
    }

    public Map<AttendanceDate, AttendanceTime> getAttendanceBook() {
        return Collections.unmodifiableMap(attendanceBook);
    }

    public Map<AttendanceDate, AttendanceStatus> getAttendanceStatuses() {
        return attendanceBook.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> AttendanceStatus.getAttendanceStatus(entry.getKey(), entry.getValue())
                ));
    }
}
