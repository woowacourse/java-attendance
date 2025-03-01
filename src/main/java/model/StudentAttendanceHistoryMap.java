package model;

import java.util.HashMap;
import java.util.Map;

public class StudentAttendanceHistoryMap {
    private final Map<AttendanceDate, AttendanceTime> studentAttendanceHistory;

    public StudentAttendanceHistoryMap(Map<AttendanceDate, AttendanceTime> studentAttendanceHistory) {
        this.studentAttendanceHistory = new HashMap<>(studentAttendanceHistory);
    }

    public void modifyStudentAttendanceHistory(AttendanceDate attendanceDate, AttendanceTime attendanceTime) {
        try {
            if (!isExistSameAttendanceDate(attendanceDate)) {
                throw new IllegalArgumentException("[ERROR] 출석하지 않은 요일입니다. 출석은 진행 후, 수정을 진행해 주세요.");
            }
            studentAttendanceHistory.put(attendanceDate, attendanceTime);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

    }

    public void addStudentAttendanceHistory(AttendanceDate attendanceDate, AttendanceTime attendanceTime) {
        studentAttendanceHistory.putIfAbsent(attendanceDate, attendanceTime);
    }

    private boolean isExistSameAttendanceDate(AttendanceDate attendanceDate) {
        return studentAttendanceHistory.containsKey(attendanceDate);
    }

    public Map<AttendanceDate, AttendanceTime> getStudentAttendanceHistory() {
        return studentAttendanceHistory;
    }
}
