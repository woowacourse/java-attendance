package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Crew {
    private final Username username;
    private final AttendanceHistories attendanceHistories;

    public Crew(String username, List<LocalDateTime> histories, LocalDate standardDate) {
        this.username = new Username(username);
        this.attendanceHistories = new AttendanceHistories(histories, standardDate);
    }

    public AttendanceResult addAttendanceHistory(LocalDateTime attendanceTime) {
        return attendanceHistories.addAttendanceHistory(attendanceTime);
    }

    public AttendanceResult editAttendanceHistory(LocalDateTime editAttendanceTime) {
        return attendanceHistories.editAttendanceHistory(editAttendanceTime);
    }

    public AttendanceHistory findAttendanceHistory(LocalDate standardTime) {
        return attendanceHistories.findAttendanceHistoryByDate(standardTime);
    }

    public boolean isSameName(String username) {
        return this.username.isSameName(username);
    }

    public boolean isExpulsionTarget(LocalDate standard) {
        AttendanceAnalyze attendanceAnalyze = getAttendanceAnalyze(standard);
        return attendanceAnalyze.isExpulsionTarget();
    }

    public AttendanceAnalyze getAttendanceAnalyze(LocalDate standardDate) {
        return attendanceHistories.getAttendanceAnalyze(standardDate);
    }

    public Username getUsername() {
        return username;
    }

}
