package domain;

import exception.DuplicateAttendanceException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class AttendanceStorage {
    private final Set<Attendance> attendances;

    private AttendanceStorage(Set<Attendance> attendances) {
        this.attendances = attendances;
    }

    public static AttendanceStorage of(List<Attendance> attendances) {
        Set<Attendance> distinctAttendances = new HashSet<>(attendances);
        return new AttendanceStorage(distinctAttendances);
    }

    public static AttendanceStorage init() {
        return new AttendanceStorage(new HashSet<>());
    }

    public boolean register(LocalDate date, LocalTime time) {
        ExistAttendance attendance = new ExistAttendance(date, time);
        if (attendances.contains(attendance)) {
            throw new DuplicateAttendanceException();
        }
        return attendances.add(attendance);
    }

    public boolean modify(LocalDate date, LocalTime modifyTime) {
        Attendance attendance = getAttendanceByDate(date);
        attendances.remove(attendance);
        return attendances.add(new ExistAttendance(date, modifyTime));
    }

    public Attendance getAttendanceByDate(LocalDate date) {
        return attendances.stream()
                .filter(attendance -> attendance.isAttendedOn(date))
                .findFirst()
                .orElse(EmptyAttendance.of(date));
    }

    public List<Attendance> getAttendancesByDateRange(LocalDate start, LocalDate end) {
        return start.datesUntil(end)
                .filter(AttendanceDate::isValid)
                .map(this::getAttendanceByDate)
                .toList();
    }

    public AttendanceStatistic getStatisticByDateRange(LocalDate start, LocalDate end) {
        Map<AttendanceStatus, Integer> result = new EnumMap<>(AttendanceStatus.class);
        List<LocalDate> dates = start.datesUntil(end)
                .filter(AttendanceDate::isValid)
                .toList();
        for (LocalDate date : dates) {
            Attendance attendance = getAttendanceByDate(date);
            AttendanceStatus status = attendance.getStatus();
            final int updatedValue = result.getOrDefault(status, 0) + 1;
            result.put(status, updatedValue);
        }
        return new AttendanceStatistic(result);
    }
}
