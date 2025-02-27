import exception.DuplicateAttendanceException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AttendanceStorage {
    private final List<Attendance> attendances;

    public AttendanceStorage() {
        this.attendances = new ArrayList<>();
    }

    public Attendance register(LocalDate date, LocalTime time) {
        Attendance attendance = new Attendance(date, time);
        if (attendances.contains(attendance)) {
            throw new DuplicateAttendanceException();
        }
        attendances.add(attendance);
        return attendance;
    }
}
