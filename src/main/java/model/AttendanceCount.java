package model;

import java.util.Map;

public class AttendanceCount {
    private final Map<AttendanceStatus, Long> attendanceCount;

    public AttendanceCount(Map<AttendanceStatus, Long> attendanceCount) {
        this.attendanceCount = attendanceCount;
    }

    public void updateAttendanceCount(AttendanceRecords attendanceRecords){
        attendanceCount.put(AttendanceStatus.ATTENDANCE, attendanceRecords.findTotalAttendanceCount());
        attendanceCount.put(AttendanceStatus.LATE, attendanceRecords.findTotalLateCount());
        attendanceCount.put(AttendanceStatus.ABSENT, attendanceRecords.findTotalAbsentCount());
    }

    public long getAttendanceTotalCount(){
        return attendanceCount.get(AttendanceStatus.ATTENDANCE);
    }

    public long getLateTotalCount(){
        return attendanceCount.get(AttendanceStatus.LATE);
    }

    public long getAbsentTotalCount(){
        return attendanceCount.get(AttendanceStatus.ABSENT);
    }
}
