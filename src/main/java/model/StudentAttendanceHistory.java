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

    public boolean isContainsAttendanceDateTime(AttendanceDateTime wantToFindAttendanceDateTime) {
        return attendanceHistory.stream()
                .anyMatch(attendanceDateTime -> attendanceDateTime.equals(wantToFindAttendanceDateTime));
    }

    public void removeAttendanceDateTime(AttendanceDateTime attendanceDateTime) {
        attendanceHistory.remove(attendanceDateTime);
    }
}
