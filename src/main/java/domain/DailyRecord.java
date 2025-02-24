package domain;

import static domain.AttendanceStatus.ABSENCE;
import static domain.AttendanceStatus.ATTENDANCE;
import static domain.AttendanceStatus.LATENESS;
import static util.constant.Value.ABSENCE_MINUTE;
import static util.constant.Value.DEFAULT_START_HOUR;
import static util.constant.Value.LATENESS_MINUTE;
import static util.constant.Value.MONDAY_START_HOUR;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Map;
import util.parser.DateTimeParser;

public class DailyRecord {

    private final LocalTime time;
    private final AttendanceStatus status;

    public DailyRecord(LocalTime time, DayOfWeek dayOfWeek) {
        this.time = time;
        this.status = checkStatus(dayOfWeek);
    }

    private AttendanceStatus checkStatus(DayOfWeek dayOfWeek) {
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

    public LocalTime getTime() {
        return time;
    }

    public AttendanceStatus getStatus() {
        return status;
    }
}
