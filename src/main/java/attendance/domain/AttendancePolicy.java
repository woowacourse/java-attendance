package attendance.domain;

import static attendance.domain.AttendanceType.ABSENCE;
import static attendance.domain.AttendanceType.ATTENDANCE;
import static attendance.domain.AttendanceType.LATE;
import static attendance.domain.AttendanceTime.CLOSE_TIME;
import static attendance.domain.AttendanceTime.GENERAL_ABSENCE_TIME;
import static attendance.domain.AttendanceTime.GENERAL_LATE_TIME;
import static attendance.domain.AttendanceTime.MONDAY_ABSENCE_TIME;
import static attendance.domain.AttendanceTime.MONDAY_LATE_TIME;
import static attendance.domain.AttendanceTime.OPEN_TIME;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

public class AttendancePolicy {
    private static final LocalDate CHRISTMAS = LocalDate.of(2024, 12, 25);

    private AttendancePolicy() {}

    public static AttendanceType checkAttendanceType(LocalDate localDate, LocalTime localTime) {
        if (localTime.isBefore(OPEN_TIME.getTime()) || localTime.isAfter(CLOSE_TIME.getTime())) {
            throw new IllegalArgumentException("캠퍼스 운영 시간이 아닙니다.");
        }
        DayOfWeek dayOfWeek = localDate.getDayOfWeek();
        if (dayOfWeek == DayOfWeek.MONDAY) {
            return checkMondayAttendanceType(localTime);
        }
        return checkGeneralAttendanceType(localTime);
    }

    public static void checkNotWeekendAndHoliday(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        if (isNotWeekend(dayOfWeek) && isNotHoliday(date)) {
            return;
        }
        throw new IllegalArgumentException("등교일이 아닙니다");
    }

    private static boolean isNotWeekend(DayOfWeek dayOfWeek) {
        return dayOfWeek != DayOfWeek.SATURDAY &&
                dayOfWeek != DayOfWeek.SUNDAY;
    }

    private static boolean isNotHoliday(LocalDate date) {
        return !date.equals(CHRISTMAS);
    }

    private static AttendanceType checkMondayAttendanceType(LocalTime localTime) {
        if (localTime.isBefore(MONDAY_LATE_TIME.getTime())) {
            return ATTENDANCE;
        }

        if (localTime.isBefore(MONDAY_ABSENCE_TIME.getTime())) {
            return LATE;
        }
        return ABSENCE;
    }

    /**
     * 월요일이 아닌 목 ~ 금의 출석 결과를 반환
     */
    private static AttendanceType checkGeneralAttendanceType(LocalTime localTime) {
        if (localTime.isBefore(GENERAL_LATE_TIME.getTime())) {
            return ATTENDANCE;
        }

        if (localTime.isBefore(GENERAL_ABSENCE_TIME.getTime())) {
            return LATE;
        }
        return ABSENCE;
    }
}
