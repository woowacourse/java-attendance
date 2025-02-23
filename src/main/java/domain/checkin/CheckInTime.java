package domain.checkin;

import domain.workingtime.WorkingTime;
import domain.attendance.AttendanceStatus;
import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class CheckInTime {
    private LocalDateTime checkInTime;

    private CheckInTime(LocalDateTime checkInTime) {
        validateWorkingDay(checkInTime);
        this.checkInTime = checkInTime;
    }

    private static void validateWorkingDay(LocalDateTime checkInTime) {
        if (checkInTime.getDayOfWeek() == DayOfWeek.SATURDAY || checkInTime.getDayOfWeek() == DayOfWeek.SUNDAY) {
            throw new IllegalArgumentException("[ERROR] 주말에는 출근할 수 없습니다.");
        }
        if (checkInTime.getDayOfMonth() == 25) {
            throw new IllegalArgumentException("[ERROR] 공휴일에는 출근할 수 없습니다.");
        }
    }

    public static CheckInTime of(LocalDateTime checkInTime) {
        return new CheckInTime(checkInTime);
    }

    public AttendanceStatus getAttendanceStatus() {
        int minute = WorkingTime.getMinute(checkInTime);
        return AttendanceStatus.timeGapToAttendanceStatus(minute);
    }

    public boolean isSameDate(CheckInTime otherTime) {
        return checkInTime.toLocalDate()
                .isEqual(
                        otherTime.checkInTime.toLocalDate()
                );
    }

    public boolean isBeforeDate(LocalDateTime otherTime) {
        return checkInTime.toLocalDate()
                .isBefore(
                        otherTime.toLocalDate()
                );
    }

    public void modify(CheckInTime otherTime) {
        checkInTime = otherTime.checkInTime;
    }

    public boolean isNotModifiable(LocalDateTime time) {
        return checkInTime.isAfter(time);
    }

    public LocalDateTime toLocalDateTime() {
        return LocalDateTime.from(checkInTime);
    }
}
