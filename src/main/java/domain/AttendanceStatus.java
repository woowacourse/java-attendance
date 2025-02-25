package domain;

import static controller.AttendanceController.NOW_MONTH;
import static controller.AttendanceController.NOW_YEAR;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import util.parser.DateTimeParser;

public enum AttendanceStatus {
    ATTENDANCE("출석"),
    LATENESS("지각"),
    ABSENCE("결석");

    private static final int MONDAY_START_HOUR = 13;
    private static final int DEFAULT_START_HOUR = 10;
    private static final int LATENESS_MINUTE = 5;
    private static final int ABSENCE_MINUTE = 30;

    public final String name;

    AttendanceStatus(String name) {
        this.name = name;
    }

    public static StatisticsResult countStatus(LocalDate nowDate, Crew crew) {
        LocalDate startDate = DateTimeParser.parseIntegerToDate(NOW_YEAR, NOW_MONTH, 1);

        Map<AttendanceStatus, Long> statusCounts = startDate.datesUntil(nowDate)
            .filter(date -> Holiday.isWeekDay(date))
            .map(date -> crew.findStatusByDate(date))
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        Map<AttendanceStatus, Integer> result = new HashMap<>();
            result.put(ATTENDANCE, statusCounts.getOrDefault(ATTENDANCE, 0L).intValue());
            result.put(LATENESS, statusCounts.getOrDefault(LATENESS, 0L).intValue());
            result.put(ABSENCE, statusCounts.getOrDefault(ABSENCE, 0L).intValue());

        return new StatisticsResult(result);
    }

    public static AttendanceStatus of(LocalTime time, DayOfWeek dayOfWeek) {
        Map<DayOfWeek, LocalTime> lateTimes = Map.of(DayOfWeek.MONDAY,
            DateTimeParser.parseIntegerToTime(MONDAY_START_HOUR, LATENESS_MINUTE));
        Map<DayOfWeek, LocalTime> absentTimes = Map.of(DayOfWeek.MONDAY,
            DateTimeParser.parseIntegerToTime(MONDAY_START_HOUR, ABSENCE_MINUTE));

        LocalTime lateTime = lateTimes.getOrDefault(dayOfWeek,
            DateTimeParser.parseIntegerToTime(DEFAULT_START_HOUR, LATENESS_MINUTE));
        LocalTime absentTime = absentTimes.getOrDefault(dayOfWeek,
            DateTimeParser.parseIntegerToTime(DEFAULT_START_HOUR, ABSENCE_MINUTE));

        if (time.isAfter(absentTime)) {
            return ABSENCE;
        }
        if (time.isAfter(lateTime)) {
            return LATENESS;
        }
        return ATTENDANCE;
    }
}
