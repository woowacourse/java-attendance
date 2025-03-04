package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class AttendancePolicy {

    private static final LocalTime CAMPUS_OPEN_TIME = LocalTime.of(8, 0);
    private static final LocalTime CAMPUS_CLOSE_TIME = LocalTime.of(23, 0);

    private static final LocalTime MONDAY_LATE_TIME = LocalTime.of(13, 5);
    private static final LocalTime MONDAY_ABSENCE_TIME = LocalTime.of(13, 30);
    private static final LocalTime LATE_TIME = LocalTime.of(10, 5);
    private static final LocalTime ABSENCE_TIME = LocalTime.of(10, 30);

    public void validateIsWeekDays(LocalDate today) {
        if (Set.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY).contains(today.getDayOfWeek())) {
            throw new IllegalArgumentException(
                    String.format("[ERROR] %02d월 %02d일 %s은 등교일이 아닙니다.",
                            today.getMonthValue(), today.getDayOfMonth(),
                            today.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN)));
        }
    }

    public void validateCampusOpen(LocalTime todayTime) {
        if (todayTime.isBefore(CAMPUS_OPEN_TIME) || todayTime.isAfter(CAMPUS_CLOSE_TIME)) {
            throw new IllegalArgumentException("[ERROR] 캠퍼스 운영 시간이 아닙니다.");
        }
    }

    public void validateIsHolidays(LocalDate today) {
        if (Holidays.isHoliday(today)) {
            throw new IllegalArgumentException("[ERROR] 공휴일에는 등교할 수 없습니다.");
        }
    }

    public AttendanceStatus getAttendanceStatus(LocalDateTime attendanceTime) {
        LocalTime lateTime = getLateTime(attendanceTime);
        LocalTime absenceTime = getAbsenceTime(attendanceTime);
        LocalTime currentTime = attendanceTime.toLocalTime();

        if (currentTime.isAfter(absenceTime)) {
            return AttendanceStatus.ABSENCE;
        }
        if (currentTime.isAfter(lateTime)) {
            return AttendanceStatus.LATE;
        }
        return AttendanceStatus.ATTENDANCE;
    }

    private boolean isMonday(LocalDateTime attendanceTime) {
        return attendanceTime.getDayOfWeek() == DayOfWeek.MONDAY;
    }

    private LocalTime getLateTime(LocalDateTime attendanceTime) {
        return isMonday(attendanceTime) ? MONDAY_LATE_TIME : LATE_TIME;
    }

    private LocalTime getAbsenceTime(LocalDateTime attendanceTime) {
        return isMonday(attendanceTime) ? MONDAY_ABSENCE_TIME : ABSENCE_TIME;
    }

    public boolean ignoreWeekendAndHoliday(LocalDate date) {
        return !Set.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY).contains(date.getDayOfWeek()) && !Holidays.isHoliday(date);
    }

    public List<LocalDate> getOpenDays(LocalDate today) {
        LocalDate startDate = today.withDayOfMonth(1);
        LocalDate endDate = today.plusDays(1);

        List<LocalDate> openDays = new ArrayList<>();
        for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(1)) {
            if (ignoreWeekendAndHoliday(date)) {
                openDays.add(date);
            }
        }
        return openDays;
    }
}
