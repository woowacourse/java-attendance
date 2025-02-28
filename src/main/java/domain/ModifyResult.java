package domain;

public class ModifyResult {
    private final Attendance oldAttendance;
    private final Attendance newAttendance;
    public ModifyResult(Attendance oldAttendance, Attendance newAttendance) {
        this.oldAttendance = oldAttendance;
        this.newAttendance = newAttendance;
    }
    public Attendance getOldAttendance() {
        return oldAttendance;
    }
    public Attendance getNewAttendance() {
        return newAttendance;
    }
}
