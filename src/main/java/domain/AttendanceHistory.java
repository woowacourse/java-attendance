package domain;

import java.time.LocalDateTime;

public class AttendanceHistory implements Comparable<AttendanceHistory> {

    public static final int ABSENT_DEFAULT_HOUR = 23;
    public static final int ABSENT_DEFAULT_MINUTE = 59;

    private static final int START_TIME = 8;
    private static final int END_TIME = 23;

    private final LocalDateTime attendanceTime;
    private final AttendanceResult attendanceResult;

    @Override
    public int compareTo(AttendanceHistory o) {
        return this.attendanceTime.compareTo(o.attendanceTime);
    }

    public AttendanceHistory(LocalDateTime attendanceTime) {
        validateHistory(attendanceTime);
        this.attendanceTime = attendanceTime;
        attendanceResult = getAttendanceResult(attendanceTime);
    }

    private AttendanceResult getAttendanceResult(LocalDateTime attendanceTime) {
        return AttendanceResult.findAttendanceResult(attendanceTime);
    }

    public boolean isSameDayOfMonth(LocalDateTime attendanceTIme) {
        return this.attendanceTime.getDayOfMonth() == attendanceTIme.getDayOfMonth();
    }

    public boolean isSameMonth(LocalDateTime attendanceTIme) {
        return this.attendanceTime.getMonthValue() == attendanceTIme.getMonthValue();
    }

    public boolean isBeforeHistory(LocalDateTime attendanceTIme) {
        LocalDateTime standardTime = LocalDateTime.of(attendanceTIme.getYear(), attendanceTIme.getMonthValue(),
                attendanceTIme.getDayOfMonth(), 0, 0);
        return attendanceTime.isBefore(standardTime);
    }

    private void validateHistory(LocalDateTime attendanceTime) {
        Holiday.validate(attendanceTime);
        validateOpeningHours(attendanceTime);
    }

    private void validateOpeningHours(LocalDateTime attendanceTime) {
        if (!(attendanceTime.getHour() == ABSENT_DEFAULT_HOUR && attendanceTime.getMinute() == ABSENT_DEFAULT_MINUTE)
                && (attendanceTime.isBefore(LocalDateTime.of(attendanceTime.getYear(), attendanceTime.getMonthValue(),
                attendanceTime.getDayOfMonth(), START_TIME, 0)) ||
                (attendanceTime.isAfter(LocalDateTime.of(attendanceTime.getYear(), attendanceTime.getMonthValue(),
                        attendanceTime.getDayOfMonth(), END_TIME, 0)))
        )) {
            throw new IllegalArgumentException("[ERROR] 캠퍼스 운영 시간은 08:00~23:00 입니다. 해당 시간 내의 시간을 입력해 주세요.");
        }
    }

    public LocalDateTime getAttendanceTime() {
        return attendanceTime;
    }

    public AttendanceResult getAttendanceResult() {
        return attendanceResult;
    }


}
