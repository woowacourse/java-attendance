package attendance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AttendanceManager {

    private final List<AttendanceTime> attendanceTimes;

    private AttendanceManager() {
        this.attendanceTimes = new ArrayList<>();
    }

    public static AttendanceManager create() {
        return new AttendanceManager();
    }

    public void add(AttendanceTime attendanceDateTime) {
        attendanceTimes.add(attendanceDateTime);
    }

    public List<AttendanceTime> getAttendanceTimes() {
        return Collections.unmodifiableList(attendanceTimes);
    }
}
