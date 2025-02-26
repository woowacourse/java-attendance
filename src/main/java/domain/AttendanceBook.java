package domain;

import exception.DuplicateAttendanceException;
import exception.InvalidDateException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class AttendanceBook {
    private final HashSet<Attendance> attendances;

    public AttendanceBook() {
        attendances = new HashSet<>();
    }

    public Attendance register(LocalDate date, LocalTime time) {
        final boolean isExist = attendances.stream().anyMatch(attendance -> attendance.isAttendedOn(date));
        if (isExist) {
            throw new DuplicateAttendanceException();
        }
        if (isHoliday(date)) {
            throw new InvalidDateException();
        }
        Attendance attendance = Attendance.of(date, time);
        attendances.add(attendance);
        return attendance;
    }

    public Attendance replace(LocalDate date, LocalTime time) {
        Attendance oldAttendance = findAttendanceByDate(date);
        Attendance modifiedAttendance = oldAttendance.modify(time);
        attendances.remove(oldAttendance);
        attendances.add(modifiedAttendance);
        return modifiedAttendance;
    }

    public Attendance findAttendanceByDate(LocalDate date) {
        return attendances.stream()
                .filter(attendance -> attendance.isAttendedOn(date))
                .findFirst()
                .orElse(Attendance.empty(date));
    }

    public List<Attendance> getAllAttendances(LocalDate startDate, LocalDate endDate) {
        List<Attendance> result = new ArrayList<>();
        for (LocalDate current = startDate; current.isBefore(endDate); current = current.plusDays(1)) {
            if (isHoliday(current)) {
                continue;
            }
            Attendance attendance = findAttendanceByDate(current);
            result.add(attendance);
        }
        return result;
    }

    public AttendanceStatistic getAttendanceStatistic(LocalDate startDate, LocalDate endDate) {
        Map<AttendanceStatus, Integer> result = new HashMap<>();

        for (LocalDate current = startDate; current.isBefore(endDate); current = current.plusDays(1)) {
            if (isHoliday(current)) {
                continue;
            }
            Attendance attendance = findAttendanceByDate(current);
            AttendanceStatus status = attendance.getStatus();
            final int updatedValue = result.getOrDefault(status, 0) + 1;
            result.put(status, updatedValue);
        }

        return new AttendanceStatistic(result);
    }

    private boolean isHoliday(LocalDate date) {
        Month month = Month.of(date.getMonthValue());
        return month.isHoliday(date.getDayOfMonth());
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        AttendanceBook that = (AttendanceBook) object;
        return attendances.containsAll(that.attendances);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attendances);
    }
}
