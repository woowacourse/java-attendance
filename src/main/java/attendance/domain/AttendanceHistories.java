package attendance.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AttendanceHistories {
    private final List<AttendanceHistory> attendanceHistories;

    public AttendanceHistories() {
        this.attendanceHistories = new ArrayList<>();
    }

    public void add(LocalDateTime attendanceDateTime, AttendanceStatus attendanceStatus) {
        AttendanceHistory attendanceHistory = new AttendanceHistory(attendanceDateTime, attendanceStatus);
        attendanceHistories.add(attendanceHistory);
    }

    public List<AttendanceHistory> getImmutableAttendanceHistories() {
        return Collections.unmodifiableList(attendanceHistories);
    }
}
