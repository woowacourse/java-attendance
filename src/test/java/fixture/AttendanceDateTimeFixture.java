package fixture;

import domain.AttendanceDateTime;
import domain.LegalHoliday;
import domain.Vacation;
import java.time.DayOfWeek;
import java.time.LocalDate;

public class AttendanceDateTimeFixture {
    private static final LocalDate START_DATE = LocalDate.of(2025, 2, 11);
    private static final int PRESENT_THRESHOLD_MINUTE = 0;
    private static final int TARDY_THRESHOLD_MINUTE = 6;
    private static final int ABSENT_THRESHOLD_MINUTE = 31;
    private static final int MONDAY_START_TIME = 13;
    private static final int DEFAULT_START_TIME = 10;

    private LocalDate date;

    public AttendanceDateTimeFixture() {
        this.date = START_DATE.minusDays(1);
        moveToNextDate();
    }

    public static LocalDate getNthValidDate(int n) {
        LocalDate date = START_DATE;
        int validDaysCount = 0;
        while (validDaysCount < n) {
            if (!isDayOff(date)) {
                validDaysCount++;
            }
            date = date.plusDays(1);
        }
        return date;
    }

    public AttendanceDateTime createPresentDateTime() {
        return createDateTime(PRESENT_THRESHOLD_MINUTE);
    }

    public AttendanceDateTime createTardyDateTime() {
        return createDateTime(TARDY_THRESHOLD_MINUTE);
    }

    public AttendanceDateTime createAbsentDateTime() {
        return createDateTime(ABSENT_THRESHOLD_MINUTE);
    }

    private AttendanceDateTime createDateTime(int statusThreshold) {
        moveToNextDate();
        if (date.getDayOfWeek() == DayOfWeek.MONDAY) {
            return new AttendanceDateTime(
                    date.atTime(MONDAY_START_TIME, statusThreshold));
        }
        return new AttendanceDateTime(
                date.atTime(DEFAULT_START_TIME, statusThreshold));
    }

    private void moveToNextDate() {
        do {
            this.date = date.plusDays(1);
        } while (isDayOff(this.date));

    }

    private static boolean isDayOff(LocalDate date) {
        return date.getDayOfWeek() == DayOfWeek.SATURDAY
                || date.getDayOfWeek() == DayOfWeek.SUNDAY
                || LegalHoliday.isHoliday(date)
                || Vacation.isVacation(date);
    }
}
