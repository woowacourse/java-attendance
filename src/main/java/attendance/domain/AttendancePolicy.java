package attendance.domain;

import static attendance.domain.AttendanceType.ABSENCE;
import static attendance.domain.AttendanceType.ATTENDANCE;
import static attendance.domain.AttendanceType.LATE;
import static attendance.error.ErrorMessage.NOT_OPERATING_TIME;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

public class AttendancePolicy {

    private AttendancePolicy() {}

    public static AttendanceType checkAttendanceType(LocalDate localDate, LocalTime localTime) {
        if (OperatingTime.isOperate(localTime)) {
            throw new IllegalArgumentException(NOT_OPERATING_TIME.getMessage());
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
                !localDate.equals(LocalDate.of(2024, 12, 25))) {
            return;
        }
        throw new IllegalArgumentException("등교일이 아닙니다");
    }

    private static AttendanceType checkMondayAttendanceType(LocalTime localTime) {
        if (localTime.isBefore(LocalTime.of(13, 6))) {
            return ATTENDANCE;
        }

        if (localTime.isBefore(LocalTime.of(13, 31))) {
            return LATE;
        }
        return ABSENCE;
    }

    /**
     * 월요일이 아닌 목 ~ 금의 출석 타입을 반환
     */
    private static AttendanceType checkGeneralAttendanceType(LocalTime localTime) {
        if (localTime.isBefore(LocalTime.of(10, 6))) {
            return ATTENDANCE;
        }

        if (localTime.isBefore(LocalTime.of(10, 31))) {
            return LATE;
        }
        return ABSENCE;
    }
}
