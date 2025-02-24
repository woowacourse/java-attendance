package domain;

import static util.constant.Value.ABSENCE_MINUTE;
import static util.constant.Value.DEFAULT_START_HOUR;
import static util.constant.Value.LATENESS_MINUTE;
import static util.constant.Value.MONDAY_START_HOUR;
import static util.constant.Value.START_DAY;
import static util.constant.Value.START_MONTH;
import static util.constant.Value.START_YEAR;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import util.parser.DateTimeParser;

public enum AttendanceStatus {
    ATTENDANCE("출석"),
    LATENESS("지각"),
    ABSENCE("결석");

    public final String name;

    AttendanceStatus(String name) {
        this.name = name;
    }

    public static StatisticsResult countStatus(LocalDate nowDate, Crew crew) {
        LocalDate startDate = DateTimeParser.parseIntegerToDate(START_YEAR, START_MONTH, START_DAY);

        Map<AttendanceStatus, Long> statusCounts = startDate.datesUntil(nowDate)
            .filter(date -> Holiday.isWeekDay(date))
            .map(date -> crew.findStatusByDate(date))
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        return new StatisticsResult(
            statusCounts.getOrDefault(ATTENDANCE, 0L).intValue(),
            statusCounts.getOrDefault(LATENESS, 0L).intValue(),
            statusCounts.getOrDefault(ABSENCE, 0L).intValue()
        );
    }

    public static AttendanceStatus checkStatus(LocalTime time, DayOfWeek dayOfWeek) {
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
