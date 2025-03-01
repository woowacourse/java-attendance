package model;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class StudentAttendanceHistory {
    private final Map<AttendanceDate, AttendanceTime> attendanceHistory;

    public StudentAttendanceHistory(Map<AttendanceDate, AttendanceTime> studentAttendanceHistory) {
        this.attendanceHistory = new HashMap<>(studentAttendanceHistory);
    }

    public void modifyStudentAttendanceHistory(AttendanceDate attendanceDate, AttendanceTime attendanceTime) {
        try {
            if (!isExistSameAttendanceDate(attendanceDate)) {
                throw new IllegalArgumentException("[ERROR] 출석하지 않은 요일입니다. 출석은 진행 후, 수정을 진행해 주세요.");
            }
            attendanceHistory.put(attendanceDate, attendanceTime);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

    }

    public void addStudentAttendanceHistory(AttendanceDate attendanceDate, AttendanceTime attendanceTime) {
        attendanceHistory.putIfAbsent(attendanceDate, attendanceTime);
    }

    public boolean isExistSameAttendanceDate(AttendanceDate attendanceDate) {
        return attendanceHistory.containsKey(attendanceDate);
    }

    public AttendanceTime findAttendanceTimeByAttendanceDate(AttendanceDate attendanceDate) {
        return attendanceHistory.get(attendanceDate);
    }

    public void updateMissingAttendanceRecords(AttendanceDate attendanceStartDate, AttendanceDate today) {
        while(!attendanceStartDate.equals(today)) {
            if (!isExistSameAttendanceDate(attendanceStartDate) && !attendanceStartDate.isHoliday()) {
                this.addStudentAttendanceHistory(attendanceStartDate, new AttendanceTime(LocalTime.of(0, 0)));
            }
            attendanceStartDate = attendanceStartDate.plusOneDay();
        }
    }

    public Map<AttendanceDate, AttendanceTime> getAttendanceHistory() {
        return attendanceHistory;
    }
}
