package domain;

public class AttendanceStatistics {
    private int present;
    private int late;
    private int absent;
    private AttendanceAlertLevel alertLevel;

    public void updateStatus(Attendances attendances) {
        this.present = attendances.countPresent();
        this.late = attendances.countLate();
        this.absent = attendances.countAbsent();
        this.alertLevel = attendances.calculateAttendanceAlertLevel();
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

    public AttendanceAlertLevel getAlertLevel() {
        return alertLevel;
    }
}
