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

    public Attendance findByDate(LocalDate date) {
        return attendances.stream()
                .filter(attendance -> attendance.isAttendedOn(date))
                .findFirst()
                .orElse(EmptyAttendance.of(date));
    }

    public boolean modify(LocalDate date, LocalTime modifyTime) {
        Attendance attendance = findByDate(date);
        attendances.remove(attendance);
        return attendances.add(new ExistAttendance(date, modifyTime));
    }

    public AttendanceStatistic getStatisticByDateRange(LocalDate start, LocalDate end) {
        Map<AttendanceStatus, Integer> result = new EnumMap<>(AttendanceStatus.class);
        start.datesUntil(end)
                .forEach(date -> {
                    Attendance attendance = findByDate(date);
                    AttendanceStatus status = attendance.getStatus();
                    final int updatedValue = result.getOrDefault(status, 0) + 1;
                    result.put(status, updatedValue);
                });
        return new AttendanceStatistic(result);
    }
}
