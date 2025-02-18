import java.time.LocalTime;

public enum AttendanceCalculatorByDay {

    MONDAY(LocalTime.of(13,5),LocalTime.of(13,30),1),
    TUESDAY(LocalTime.of(10,5),LocalTime.of(10,30),2),
    WEDNESDAY(LocalTime.of(10,5), LocalTime.of(10,30),3),
    THURSDAY(LocalTime.of(10,5), LocalTime.of(10,30),4),
    FRIDAY(LocalTime.of(10,5), LocalTime.of(10,30),5);

    private LocalTime lateTime;
    private LocalTime absentTime;
    private int dayOfWeekValue;

    AttendanceCalculatorByDay(LocalTime lateTime, LocalTime absentTime, int dayOfWeekValue) {
        this.lateTime = lateTime;
        this.absentTime = absentTime;
        this.dayOfWeekValue = dayOfWeekValue;
    }

    public static AttendanceStatus attendanceCalculator(int day, LocalTime localTime) {
        for (AttendanceCalculatorByDay attendanceCalculatorByDay : AttendanceCalculatorByDay.values()) {
            if (attendanceCalculatorByDay.dayOfWeekValue == day) {
                if (localTime.isBefore(attendanceCalculatorByDay.lateTime)) {
                    return AttendanceStatus.ATTENDANCE;
                }
                if (localTime.isBefore(attendanceCalculatorByDay.absentTime)) {
                    return AttendanceStatus.LATE;
                }
                return AttendanceStatus.ABSENT;
            }
        }
        return null;
    }
}
