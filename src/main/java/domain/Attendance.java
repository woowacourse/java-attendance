package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Attendance {
    
    private static final List<DayOfWeek> WEEKENDS = List.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);
    
    private final LocalDate attendDate;
    private final AttendanceTime attendTime;
    private final AttendanceStatus status;
    
    private Attendance(final LocalDate attendDate, final AttendanceTime attendTime, final AttendanceStatus status) {
        validateNotWeekend(attendDate);
        validateNotHoliday(attendDate);
        this.attendDate = attendDate;
        this.attendTime = attendTime;
        this.status = status;
    }
    
    private void validateNotWeekend(final LocalDate date) {
        if (isWeekend(date)) {
            throw new IllegalArgumentException("주말에는 출석할 수 없습니다.");
        }
    }
    
    private static boolean isWeekend(final LocalDate date) {
        return WEEKENDS.contains(date.getDayOfWeek());
    }
    
    private void validateNotHoliday(final LocalDate date) {
        if (isHoliday(date)) {
            throw new IllegalArgumentException("공휴일에는 출석할 수 없습니다.");
        }
    }
    
    private static boolean isHoliday(final LocalDate date) {
        return Holiday.isHoliday(date);
    }
    
    public static Attendance of(final LocalDate attendDate, final LocalTime attendTime) {
        return new Attendance(
                attendDate,
                AttendanceTime.from(attendTime),
                AttendanceStatus.of(attendDate.getDayOfWeek(), attendTime)
        );
    }
    
    public static Attendance noShow(LocalDate noShowDate) {
        return new Attendance(
                noShowDate,
                AttendanceTime.noShow(),
                AttendanceStatus.결석
        );
    }
    
    public static boolean isAttendableDate(LocalDate date) {
        return !isWeekend(date) && !isHoliday(date);
    }
    
    public LocalDate getAttendDate() {
        return attendDate;
    }
    
    public AttendanceTime getAttendTime() {
        return attendTime;
    }
    
    public AttendanceStatus getStatus() {
        return status;
    }
}
