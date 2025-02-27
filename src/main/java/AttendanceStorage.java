import exception.DuplicateAttendanceException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class AttendanceStorage {
    private final Map<AttendanceDate, AttendanceTime> attendances;

    public AttendanceStorage() {
        this.attendances = new HashMap<>();
    }

    public void register(LocalDate date, LocalTime time) {
        AttendanceDate enterDate = new AttendanceDate(date);
        if (attendances.containsKey(enterDate)) {
            throw new DuplicateAttendanceException();
        }
        AttendanceTime enterTime = new AttendanceTime(time);
        attendances.put(enterDate, enterTime);
    }
}
