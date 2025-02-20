package domain;

public class AttendanceCount {
    public static final int LATE_TO_ABSENT_THRESHOLD = 3;
    private int present;
    private int late;
    private int absent;

    public void calculateRecord(Attendance attendance) {
        if (attendance.calculateAttendanceStatus() == AttendanceStatus.PRESENT) {
            present++;
        }
        if (attendance.calculateAttendanceStatus() == AttendanceStatus.LATE) {
            late++;
        }
        if (attendance.calculateAttendanceStatus() == AttendanceStatus.ABSENT) {
            absent++;
        }
    }

    public AttendanceAlertLevel calculateAttendanceAlertLevel() {
        int absentTotal = absent + (late / LATE_TO_ABSENT_THRESHOLD);
        if (absentTotal >= AttendanceAlertLevel.DISMISSED.absenceLimit) {
            return AttendanceAlertLevel.DISMISSED;
        }
        if (absentTotal >= AttendanceAlertLevel.COUNSEL_REQUIRED.absenceLimit) {
            return AttendanceAlertLevel.COUNSEL_REQUIRED;
        }
        if (absentTotal >= AttendanceAlertLevel.CAUTION.absenceLimit) {
            return AttendanceAlertLevel.CAUTION;
        }
        return AttendanceAlertLevel.NORMAL;
    }

    public int getPresent() {
        return present;
    }

    public int getLate() {
        return late;
    }

    public int getAbsent() {
        return absent;
    }
}
