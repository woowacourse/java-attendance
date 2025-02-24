package domain;

import static domain.AttendanceStatus.ABSENCE;
import static domain.AttendanceStatus.ATTENDANCE;
import static domain.AttendanceStatus.LATENESS;
import static util.constant.Value.START_DAY;
import static util.constant.Value.START_MONTH;
import static util.constant.Value.START_YEAR;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import util.parser.DateTimeParser;

public class AttendanceStatistics {

    public static StatisticsResult countStatus(LocalDate nowDate, Crew crew) {
        LocalDate startDate = DateTimeParser.parseIntegerToDate(START_YEAR, START_MONTH, START_DAY);

        Map<AttendanceStatus, Long> statusCounts = startDate.datesUntil(nowDate)
            .filter(date -> Holiday.isWeekDay(date))
            .map(date -> {
                DailyRecord status = crew.findTimeByDate(date);
                if (status == null) {
                    return ABSENCE;
                }
                return status.getStatus();
            })
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        return new StatisticsResult(
            statusCounts.getOrDefault(ATTENDANCE, 0L).intValue(),
            statusCounts.getOrDefault(LATENESS, 0L).intValue(),
            statusCounts.getOrDefault(ABSENCE, 0L).intValue()
        );
    }

    public static Map<String, StatisticsResult> calculateExpelledWarning
        (LocalDate nowDate, Map<String, Crew> crews) {
        return crews.entrySet().stream()
            .map(entry -> Map.entry(entry.getKey(),
                AttendanceStatistics.countStatus(nowDate, entry.getValue())))
            .filter(entry -> entry.getValue().hasPenalty())
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                (a, b) -> b, LinkedHashMap::new));
    }
}
