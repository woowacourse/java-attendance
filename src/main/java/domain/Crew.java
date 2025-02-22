package domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Crew {

    private final String name;
    private final Map<AttendanceStatus, Integer> attendanceStatuses;

    public Crew(String name) {
        this.name = name;
        this.attendanceStatuses = new HashMap<>();
    }

    public String getName() {
        return this.name;
    }

    public Map<AttendanceStatus, Integer> getAttendanceStatus(List<AttendanceTime> attendanceTimes) {
        countAttendanceStatus(attendanceTimes);
        return attendanceStatuses;
    }

    private void countAttendanceStatus(List<AttendanceTime> attendanceTimes) {
        for (AttendanceStatus attendanceStatus : AttendanceStatus.values()) {
            attendanceStatuses.put(attendanceStatus, 0);
        }
        for (AttendanceTime attendanceTime : attendanceTimes) {
            attendanceStatuses.compute(attendanceTime.getAttendanceStatus(), (status, count) -> count + 1);
        }
    }

    public boolean getExpelStatus(List<AttendanceTime> attendanceTimes) {
        countAttendanceStatus(attendanceTimes);
        return ExpelStatus.determineExpelStatus(attendanceStatuses) != ExpelStatus.NONE;
    }
}
