package attendance.model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Set;

public enum WoowaDurationTime {
    MONDAY(DayOfWeek.MONDAY, LocalTime.of(13, 0)),
    TUESDAY(DayOfWeek.TUESDAY, LocalTime.of(10, 0)),
    WEDNESDAY(DayOfWeek.WEDNESDAY, LocalTime.of(10, 0)),
    THURSDAY(DayOfWeek.THURSDAY, LocalTime.of(10, 0)),
    FRIDAY(DayOfWeek.FRIDAY, LocalTime.of(10, 0));

    private final DayOfWeek dayOfWeek;
    private final LocalTime startTime;
    private static final Set<LocalDate> holidayDate = Set.of(LocalDate.of(2024, 12, 25));

    WoowaDurationTime(DayOfWeek dayOfWeek, LocalTime startTime) {
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
    }

    public static WoowaDurationTime from(AttendanceDate attendanceDate) {
        return Arrays.stream(WoowaDurationTime.values())
                .filter(woowaDurationTime -> woowaDurationTime.dayOfWeek == attendanceDate.getDayOfWeek())
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 등교일 입니다."));
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public static boolean isDurationDay(LocalDate localDate) {
        return !(isWeekend(localDate) || isHoliday(localDate));
    }

    private static boolean isWeekend(LocalDate localDate) {
        DayOfWeek dayOfWeek = localDate.getDayOfWeek();
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
    }

    private static boolean isHoliday(LocalDate localDate) {
        return holidayDate.contains(localDate);
    }
}
