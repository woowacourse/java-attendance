import exception.DuplicateAttendanceException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

public class AttendanceStorage {
    private final Set<Attendance> attendances;

    public AttendanceStorage() {
        this.attendances = new HashSet<>();
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
}
