package model;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Student {
    private static final int LATE_CONVERSION_RATE = 3;

    private final AttendanceRecords attendanceRecords;
    private final String name;
    private final AttendanceCount attendanceCount;

    public Student(String name, AttendanceRecords attendanceRecords) {
        this.attendanceRecords = attendanceRecords;
        this.name = name;
        this.attendanceCount = new AttendanceCount(createAttendanceCount());
    }

    public void modifyAttendanceRecord(LocalDateTime modifyDateTime) {
        attendanceRecords.updateAttendanceStatusByLocalDate(modifyDateTime);
    }

    public void attendanceRegister(LocalDateTime localDateTime) {
        attendanceRecords.registerAttendanceRecord(localDateTime);
    }

    public void createAttendanceRecords(LocalDateTime localDateTime) {
        attendanceRecords.createAttendanceRecords(localDateTime);
    }

    public String findStateByLocalDateTime(LocalDateTime localDateTime) {
        return attendanceRecords.findAttendanceStatusByLocalDateTime(localDateTime).getState();
    }

    public long calculateAbsent() {
        updateAttendanceTotalCount();
        return attendanceCount.getAbsentTotalCount() + attendanceCount.getLateTotalCount() / LATE_CONVERSION_RATE;
    }

    public void updateAttendanceTotalCount(){
        attendanceCount.updateAttendanceCount(attendanceRecords);
    }

    private Map<AttendanceStatus, Long> createAttendanceCount(){
        Map<AttendanceStatus, Long> attendanceCount = new HashMap<>();
        for (AttendanceStatus attendanceStatus : AttendanceStatus.values()){
            attendanceCount.put(attendanceStatus, 0L);
        }
        return attendanceCount;
    }

    public AttendanceCount getAttendanceCount() {
        return attendanceCount;
    }

    public String getName() {
        return name;
    }

    public AttendanceRecords getAttendanceRecords() {
        return attendanceRecords;
    }
}
