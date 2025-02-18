package attendance.domain;

import static java.time.DayOfWeek.SATURDAY;
import static java.time.DayOfWeek.SUNDAY;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.Locale;

public class Attendance {

    private static final LocalTime OPERATION_START_TIME = LocalTime.of(8, 0);
    private static final LocalTime OPERATION_END_TIME = LocalTime.of(23, 0);

    private final Crew crew;
    private final LocalDateTime attendanceDateTime;

    public Attendance(final Crew crew, final LocalDateTime attendanceDateTime) {
        validate(attendanceDateTime);
        this.crew = crew;
        this.attendanceDateTime = attendanceDateTime;
    }

    private void validate(final LocalDateTime attendanceDateTime) {
        validateHoliday(attendanceDateTime);
        validateOperationTime(attendanceDateTime);
    }

    private void validateHoliday(final LocalDateTime attendanceDateTime) {
        if (Holiday.isExists(attendanceDateTime.toLocalDate()) || isWeekend(attendanceDateTime)) {
            throw new IllegalArgumentException("%d월 %d일 %s은 등교일이 아닙니다.".formatted(
                    attendanceDateTime.getMonthValue(), attendanceDateTime.getDayOfMonth(),
                    attendanceDateTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA)
            ));
        }
    }

    private void validateOperationTime(final LocalDateTime attendanceDateTime) {
        LocalTime attendanceTime = attendanceDateTime.toLocalTime();
        if (attendanceTime.isBefore(OPERATION_START_TIME) || attendanceTime.isAfter(OPERATION_END_TIME)) {
            throw new IllegalArgumentException("%02d:%02d은 캠퍼스 운영 시간이 아닙니다.".formatted(
                    attendanceDateTime.getHour(), attendanceDateTime.getMinute()
            ));
        }
    }

    private boolean isWeekend(final LocalDateTime attendanceDateTime) {
        DayOfWeek attendanceDayOfWeek = attendanceDateTime.getDayOfWeek();
        return attendanceDayOfWeek.equals(SUNDAY) || attendanceDayOfWeek.equals(SATURDAY);
    }

}
