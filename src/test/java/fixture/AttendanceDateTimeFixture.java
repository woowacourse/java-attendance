package fixture;

import domain.AttendanceDateTime;
import domain.Campus;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

public class AttendanceDateTimeFixture {
    private static final LocalTime PRESENT_TIME = LocalTime.of(10, 0);
    private static final LocalTime ABSENT_TIME = LocalTime.of(14, 0);
    private static final LocalTime DEFAULT_TARDY_TIME = LocalTime.of(10, 15);
    private static final LocalTime MONDAY_TARDY_TIME = LocalTime.of(13, 15);

    private static final LocalDate START_DAY_OF_7TH_PERIOD = LocalDate.of(2025, 2, 11);
    private static final LocalDate LAST_DAY_OF_FEBRUARY = START_DAY_OF_7TH_PERIOD.with(
            TemporalAdjusters.lastDayOfMonth());
    protected static final List<LocalDate> VALIDATE_DAYS_IN_FEBRUARY = Campus.getInstance()
            .getOpenDaysUntil(LAST_DAY_OF_FEBRUARY);


    public static List<AttendanceDateTime> createPresentDates() { // 2월 한 달 동안의 출석 기록
        return VALIDATE_DAYS_IN_FEBRUARY.stream()
                .map(date -> new AttendanceDateTime(date.atTime(PRESENT_TIME)))
                .toList();
    }

    public static List<AttendanceDateTime> createAbsentDates() { // 2월 한 달 동안의 결석 기록
        return VALIDATE_DAYS_IN_FEBRUARY.stream()
                .map(date -> new AttendanceDateTime(date.atTime(ABSENT_TIME)))
                .toList();
    }

    public static List<AttendanceDateTime> createTardyDates() { // 2월 한 달 동안의 지각 기록
        return VALIDATE_DAYS_IN_FEBRUARY.stream()
                .map(AttendanceDateTimeFixture::createTardyDate)
                .toList();
    }

    private static AttendanceDateTime createTardyDate(LocalDate date) {
        if (date.getDayOfWeek() == DayOfWeek.MONDAY) {
            return new AttendanceDateTime(date.atTime(MONDAY_TARDY_TIME));
        }
        return new AttendanceDateTime(date.atTime(DEFAULT_TARDY_TIME));
    }
}
