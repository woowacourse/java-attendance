package domain;

import static util.constant.Value.ABSENCE_STRING;
import static util.constant.Value.ATTENDANCE_STRING;
import static util.constant.Value.LATENESS_STRING;
import static util.constant.Value.START_DAY;
import static util.constant.Value.START_MONTH;
import static util.constant.Value.START_YEAR;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import util.parser.DateTimeParser;

public class AttendanceStatistics {

    public static StatisticsResult countStatus(LocalDate nowDate, Crew crew) {
        LocalDate startDate = DateTimeParser.parseIntegerToDate(START_YEAR, START_MONTH, START_DAY);

        Map<String, Long> statusCounts = startDate.datesUntil(nowDate)
            .filter(date -> !Holiday.isHoliday(date) && !Holiday.isWeekend(date))
            .map(date -> {
                TimeAndStatus status = crew.findByDate(date);
                if (status == null) {
                    return ABSENCE_STRING;
                }
                return status.getStatus();
            })
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        return new StatisticsResult(
            statusCounts.getOrDefault(ATTENDANCE_STRING, 0L).intValue(),
            statusCounts.getOrDefault(LATENESS_STRING, 0L).intValue(),
            statusCounts.getOrDefault(ABSENCE_STRING, 0L).intValue()
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
