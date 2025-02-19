package domain;

public class AttendanceDateTime {
    private final AttendanceDate attendanceDate;
    private final AttendanceTime attendanceTime;

    private AttendanceDateTime(AttendanceDate attendanceDate, AttendanceTime attendanceTime) {
        this.attendanceDate = attendanceDate;
        this.attendanceTime = attendanceTime;
    }

    public static AttendanceDateTime of(int day, int hour, int minute) {
        return new AttendanceDateTime(new AttendanceDate(day),
                new AttendanceTime(hour, minute));
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
