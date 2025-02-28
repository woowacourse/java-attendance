import exception.DuplicateAttendanceException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class AttendanceStorage {
    private final Set<Attendance> attendances;

    public AttendanceStorage() {
        this.attendances = new HashSet<>();
    }

    public boolean register(LocalDate date, LocalTime time) {
        Attendance attendance = new Attendance(date, time);
        if (attendances.contains(attendance)) {
            throw new DuplicateAttendanceException();
        }
        return attendances.add(attendance);
    }

    public Optional<Attendance> findByDate(LocalDate date) {
        return attendances.stream()
                .filter(attendance -> attendance.isAttendedOn(date))
                .findFirst();
    }

    public void modify(LocalDate date, LocalTime modifyTime) {
        Optional<Attendance> attendance = findByDate(date);
        attendance.ifPresent(attendances::remove);
        attendances.add(new Attendance(date, modifyTime));
    }
}
