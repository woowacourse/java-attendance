package domain;

public class AttendanceDateTime {
    private final AttendanceDate attendanceDate;
    private final AttendanceTime attendanceTime;

    private AttendanceDateTime(AttendanceDate attendanceDate, AttendanceTime attendanceTime) {
        this.attendanceDate = attendanceDate;
        this.attendanceTime = attendanceTime;
    }

    public static AttendanceDateTime of(int day, int hour, int minute) {
        return new AttendanceDateTime(new AttendanceDate(day), new AttendanceTime(hour, minute));
    }

    public int getMonth() {
        return attendanceDate.getMonth();
    }

    public int getDay() {
        return attendanceDate.getDay();
    }

    public int getDayOfWeek() {
        return attendanceDate.getDayOfWeek();
    }

    public int getHour() {
        return attendanceTime.getHour();
    }

    public int getMinute() {
        return attendanceTime.getMinute();
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

    public boolean hasSameDay(AttendanceDateTime comparedDateTime) {
        return comparedDateTime.attendanceDate.getDay() == attendanceDate.getDay();
    }
}
