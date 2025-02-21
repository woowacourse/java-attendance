package attendance.domain;

import static attendance.domain.AttendanceType.ABSENCE;
import static attendance.domain.AttendanceType.ATTENDANCE;
import static attendance.domain.AttendanceType.LATE;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

public class AttendancePolicy {
    private static final LocalTime OPEN_TIME = LocalTime.of(8, 0);
    private static final LocalTime CLOSE_TIME = LocalTime.of(23, 0);
    private static final LocalTime MONDAY_LATE_TIME = LocalTime.of(13, 6);
    private static final LocalTime MONDAY_ABSENCE_TIME = LocalTime.of(13, 31);
    private static final LocalTime GENERAL_LATE_TIME = LocalTime.of(10, 6);
    private static final LocalTime GENERAL_ABSENCE_TIME = LocalTime.of(10, 31);
    private static final LocalDate CHRISTMAS = LocalDate.of(2024, 12, 25);

    private AttendancePolicy() {}

    public static AttendanceType checkAttendanceType(LocalDate localDate, LocalTime localTime) {
        if (localTime.isBefore(OPEN_TIME) || localTime.isAfter(CLOSE_TIME)) {
            throw new IllegalArgumentException("캠퍼스 운영 시간이 아닙니다.");
        }
        DayOfWeek dayOfWeek = localDate.getDayOfWeek();
        if (dayOfWeek == DayOfWeek.MONDAY) {
            return checkMondayAttendanceType(localTime);
        }
        return checkGeneralAttendanceType(localTime);
    }

    public static void checkHoliday(LocalDate localDate) {
        DayOfWeek dayOfWeek = localDate.getDayOfWeek();
        if (dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY &&
                !localDate.equals(CHRISTMAS)) {
            return;
        }
        throw new IllegalArgumentException("등교일이 아닙니다");
    }

    private static AttendanceType checkMondayAttendanceType(LocalTime localTime) {
        if (localTime.isBefore(MONDAY_LATE_TIME)) {
            return ATTENDANCE;
        }

        if (localTime.isBefore(MONDAY_ABSENCE_TIME)) {
            return LATE;
        }
        return ABSENCE;
    }

    /**
     * 월요일이 아닌 목 ~ 금의 출석 결과를 반환
     */
    private static AttendanceType checkGeneralAttendanceType(LocalTime localTime) {
        if (localTime.isBefore(GENERAL_LATE_TIME)) {
            return ATTENDANCE;
        }

        if (localTime.isBefore(GENERAL_ABSENCE_TIME)) {
            return LATE;
        }
        return ABSENCE;
    }
}
