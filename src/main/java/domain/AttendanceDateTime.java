package domain;

public class AttendanceDateTime {
    private final AttendanceDate attendanceDate;
    private final AttendanceTime attendanceTime;

    public AttendanceDateTime(AttendanceDate attendanceDate, AttendanceTime attendanceTime) {
        this.attendanceDate = attendanceDate;
        this.attendanceTime = attendanceTime;
    }

    public AttendanceType getAttendanceType() {
        int dayOfWeek = attendanceDate.getDayOfWeek();
        if (attendanceTime.isAbsence(dayOfWeek)) {
            return AttendanceType.ABSENCE;
        }
        if (attendanceTime.isLate(dayOfWeek)) {
            return AttendanceType.LATE;
        }
        return AttendanceType.PRESENT;
    }
}
