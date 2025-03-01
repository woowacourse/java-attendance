package model;

import java.util.ArrayList;
import java.util.List;

public class StudentAttendanceHistory {
    private final List<AttendanceDateTime> attendanceHistory;

    public StudentAttendanceHistory(List<AttendanceDateTime> attendanceHistory) {
        this.attendanceHistory = new ArrayList<>(attendanceHistory);
    }

    public void addAttendanceDateTime(AttendanceDateTime attendanceDateTime) {
        attendanceHistory.add(attendanceDateTime);
    }

    public boolean isExistSameAttendanceDateTime(AttendanceDateTime wantToFindAttendanceDateTime) {
        return attendanceHistory.stream()
                .anyMatch(attendanceDateTime -> attendanceDateTime.equals(wantToFindAttendanceDateTime));
    }

    public AttendanceDateTime findSameAttendanceDate(AttendanceDateTime wantToAddAttendanceDateTime) {
        return attendanceHistory.stream()
                .filter(attendanceDateTime -> attendanceDateTime.isSameDate(wantToAddAttendanceDateTime))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 출석하지 않은 요일입니다. 먼저 출석을 진행 후, 수정해 주세요."));
    }

    public void modifyAttendance(AttendanceDateTime wantToAddAttendanceDateTime) {
        try {
            attendanceHistory.remove(findSameAttendanceDate(wantToAddAttendanceDateTime));
            addAttendanceDateTime(wantToAddAttendanceDateTime);
            }
            catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
        }
    }
}
