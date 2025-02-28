import exception.DuplicateAttendanceException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AttendanceStorage {
    private final List<Attendance> attendances;

    public AttendanceStorage() {
        this.attendances = new ArrayList<>();
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

    }
}
