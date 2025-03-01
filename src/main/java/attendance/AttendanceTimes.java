package attendance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AttendanceTimes {

    private final List<AttendanceTime> attendanceTimes;

    private AttendanceTimes() {
        this.attendanceTimes = new ArrayList<>();
    }

    public static AttendanceTimes create() {
        return new AttendanceTimes();
    }

    public void add(AttendanceTime attendanceDateTime) {
        attendanceTimes.add(attendanceDateTime);
    }

    public List<AttendanceTime> getAttendanceTimes() {
        return Collections.unmodifiableList(attendanceTimes);
    }
}
