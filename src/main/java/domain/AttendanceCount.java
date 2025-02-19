package domain;

public class AttendanceCount {
    private int present;
    private int late;
    private int absent;

    public void calculateRecord(Attandance attandance) {
        if (attandance.calculateAttendanceStatus() == AttendanceStatus.PRESENT){
            present++;
        }
        if (attandance.calculateAttendanceStatus() == AttendanceStatus.LATE){
            late++;
        }
        if (attandance.calculateAttendanceStatus() == AttendanceStatus.ABSENT){
            absent++;
        }
    }

    public AttendanceAlertLevel calculateAttendanceAlertLevel() {
        int absentTotal = absent + (late / 3);
        if (absentTotal >= AttendanceAlertLevel.DISMISSED.absenceLimit){
            return AttendanceAlertLevel.DISMISSED;
        }
        if (absentTotal >= AttendanceAlertLevel.COUNSEL_REQUIRED.absenceLimit){
            return AttendanceAlertLevel.COUNSEL_REQUIRED;
        }
        if (absentTotal >= AttendanceAlertLevel.CAUTION.absenceLimit){
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
