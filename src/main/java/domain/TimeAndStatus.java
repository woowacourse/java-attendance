package domain;

import static util.constant.Value.ABSENCE_MINUTE;
import static util.constant.Value.ABSENCE_STRING;
import static util.constant.Value.ATTENDANCE_STRING;
import static util.constant.Value.DEFAULT_START_HOUR;
import static util.constant.Value.LATENESS_MINUTE;
import static util.constant.Value.LATENESS_STRING;
import static util.constant.Value.MONDAY_START_HOUR;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Map;
import util.parser.DateTimeParser;

public class TimeAndStatus {

    private final LocalTime time;
    private final String status;

    public TimeAndStatus(LocalTime time, DayOfWeek dayOfWeek) {
        this.time = time;
        this.status = checkStatus(dayOfWeek);
    }

    private String checkStatus(DayOfWeek dayOfWeek) {
        Map<DayOfWeek, LocalTime> lateTimes = Map.of(DayOfWeek.MONDAY,
            DateTimeParser.parseIntegerToTime(MONDAY_START_HOUR, LATENESS_MINUTE));
        Map<DayOfWeek, LocalTime> absentTimes = Map.of(DayOfWeek.MONDAY,
            DateTimeParser.parseIntegerToTime(MONDAY_START_HOUR, ABSENCE_MINUTE));

        LocalTime lateTime = lateTimes.getOrDefault(dayOfWeek,
            DateTimeParser.parseIntegerToTime(DEFAULT_START_HOUR, LATENESS_MINUTE));
        LocalTime absentTime = absentTimes.getOrDefault(dayOfWeek,
            DateTimeParser.parseIntegerToTime(DEFAULT_START_HOUR, ABSENCE_MINUTE));

        if (time.isAfter(absentTime)) {
            return ABSENCE_STRING;
        }
        if (time.isAfter(lateTime)) {
            return LATENESS_STRING;
        }
        return ATTENDANCE_STRING;
    }

    public LocalTime getTime() {
        return time;
    }

    public String getStatus() {
        return status;
    }
}
