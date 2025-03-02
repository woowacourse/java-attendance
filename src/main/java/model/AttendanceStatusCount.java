package model;

import java.util.HashMap;
import java.util.Map;

public class AttendanceStatusCount {
    private final Map<AttendanceStatus, Long> attendanceStatusCount;

    public AttendanceStatusCount() {
        this.attendanceStatusCount = new HashMap<>();
    }

    public void updateAttendanceCount(AttendanceStatusRecord attendanceStatusRecord) {
        long attendanceCount = attendanceStatusRecord.findAttendanceStatusCount(AttendanceStatus.ATTENDANCE);
        long lateCount = attendanceStatusRecord.findAttendanceStatusCount(AttendanceStatus.LATE);
        long absentCount = attendanceStatusRecord.findAttendanceStatusCount(AttendanceStatus.ABSENT);

        attendanceStatusCount.put(AttendanceStatus.ATTENDANCE, attendanceCount);
        attendanceStatusCount.put(AttendanceStatus.LATE, lateCount);
        attendanceStatusCount.put(AttendanceStatus.ABSENT, absentCount);
    }

    public Map<AttendanceStatus, Long> getAttendanceStatusCount() {
        return attendanceStatusCount;
    }

    public long getAbsentCount(){
        return attendanceStatusCount.get(AttendanceStatus.ABSENT);
    }

    public long getLateCount(){
        return attendanceStatusCount.get(AttendanceStatus.LATE);
    }
}