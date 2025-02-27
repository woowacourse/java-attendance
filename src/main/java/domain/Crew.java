package domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Crew {
    private final String name;
    private List<Attendance> attendanceHistory;

    public Crew(String name) {
        this.name = name;
        attendanceHistory = new ArrayList<>();
    }

    public void addAttendanceWithDateTime(LocalDateTime localDateTime) {
        attendanceHistory.add(new Attendance(localDateTime));
    }

    public List<Attendance> getAttendanceHistory() {
        return attendanceHistory;
    }

    public void addAttendance(Attendance attendance) {
        if (isAlreadyAttendedDay(attendance)) {
            throw new IllegalArgumentException(ERROR_MESSAGE.ALREADY_ATTENDED.getMessage());
        }
        attendanceHistory.add(attendance);
    }

    private boolean isAlreadyAttendedDay(Attendance newAttendance) {
        return attendanceHistory.stream()
                .anyMatch(attendance -> attendance.getDayOfMonth() == newAttendance.getDayOfMonth());
    }

    public int getAttendCount() {
        return (int) attendanceHistory.stream()
                .filter(attendance -> attendance.getAttendanceStatus() == AttendanceStatus.ATTEND)
                .count();
    }

    public int getLateCount() {
        return (int) attendanceHistory.stream()
                .filter(attendance -> attendance.getAttendanceStatus() == AttendanceStatus.LATE)
                .count();
    }

    public int getAbsentCount() {
        return (int) attendanceHistory.stream()
                .filter(attendance -> attendance.getAttendanceStatus() == AttendanceStatus.ABSENT)
                .count();
    }
}
