package attendance;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AttendanceManager {

    private final List<LocalDateTime> attendanceTimes;

    private AttendanceManager() {
        this.attendanceTimes = new ArrayList<>();
    }

    public static AttendanceManager create() {
        return new AttendanceManager();
    }

    public void add(LocalDateTime attendanceDateTime) {
        attendanceTimes.add(attendanceDateTime);
    }

    public List<LocalDateTime> getAttendanceTimes() {
        return Collections.unmodifiableList(attendanceTimes);
    }
}
