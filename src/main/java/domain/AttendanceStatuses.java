package domain;

import util.Dates;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class AttendanceStatuses {
    private final Map<AttendanceDate, AttendanceStatus> attendanceStatuses;

    public AttendanceStatuses() {
        attendanceStatuses = new HashMap<>();
    }

    public AttendanceStatuses(Map<AttendanceDate, AttendanceStatus> attendanceStatuses) {
        this.attendanceStatuses = attendanceStatuses;
    }

    public void put(AttendanceDate date, AttendanceTime time) {
        attendanceStatuses.put(date, AttendanceStatus.getAttendanceStatus(date, time));
    }

    public int getAbsenceCount() {
        return Dates.getWorkingDays() - getAttendCount() - getTardyCount();
    }

    public int getAttendCount() {
        return (int) attendanceStatuses.values().stream()
                .filter(status -> status == AttendanceStatus.ATTEND)
                .count();
    }

    public int getTardyCount() {
        return (int) attendanceStatuses.values().stream()
                .filter(status -> status == AttendanceStatus.TARDY)
                .count();
    }


    public Map<AttendanceDate, AttendanceStatus> getAttendanceStatuses() {
        return Collections.unmodifiableMap(attendanceStatuses);
    }

    public AttendanceStatus get(AttendanceDate date) {
        return attendanceStatuses.getOrDefault(date, AttendanceStatus.ABSENCE);
    }

    public RiskStatus getRiskStatus() {
        return RiskStatus.getRiskStatus(getAbsenceCount(), getTardyCount());
    }
}
