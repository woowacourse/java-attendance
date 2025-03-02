package attendance.domain;

import attendance.view.TimeFormatter;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;

public class CampusScheduler {

    public void validateOperationDate(final LocalDate attendanceDate) {
        if (isNotOperationDate(attendanceDate)) {
            throw new IllegalArgumentException(
                    "[ERROR] " + TimeFormatter.makeDateMessage(attendanceDate) + "은 등교일이 아닙니다.");
        }
    }

    public AttendanceState calculateAttendanceState(final LocalDateTime attendanceTime) {
        LocalTime startTime = Campus.getEducationStartTime(attendanceTime.getDayOfWeek());
        long diff = Duration.between(startTime, attendanceTime).toMinutes();
        return AttendanceState.from(diff);
    }

    public void validateOperationTime(final LocalDateTime attendanceTime) {
        boolean isOperationTime = Campus.isOperationTime(LocalTime.from(attendanceTime));
        if (!isOperationTime) {
            throw new IllegalArgumentException("[ERROR] 캠퍼스 운영 시간이 아닙니다.");
        }
    }

    public boolean isNotOperationDate(final LocalDate attendanceDate) {
        return isWeekend(attendanceDate) || isHoliday(attendanceDate);
    }

    public Map<AttendanceState, Integer> countByAttendanceState(final CrewHistory history,
                                                                final LocalDate nowDate) {
        Map<AttendanceState, Integer> result = initialize();
        LocalDate date = nowDate.withDayOfMonth(1);
        while (date.isBefore(nowDate)) {
            countHistory(history, date, result);
            date = date.plusDays(1);
        }
        return result;
    }

    private Map<AttendanceState, Integer> initialize() {
        Map<AttendanceState, Integer> map = new EnumMap<>(AttendanceState.class);
        for (AttendanceState attendanceState : AttendanceState.values()) {
            map.put(attendanceState, 0);
        }
        return map;
    }

    private void countHistory(final CrewHistory history, LocalDate date,
                              final Map<AttendanceState, Integer> result) {
        if (isNotOperationDate(date)) {
            return;
        }
        count(history, date, result);
    }

    private void count(final CrewHistory history, final LocalDate date,
                           final Map<AttendanceState, Integer> result) {
        Optional<LocalDateTime> time = history.find(date);
        if (time.isEmpty()) {
            result.merge(AttendanceState.ABSENCE, 1, Integer::sum);
            return;
        }
        AttendanceState attendanceState = calculateAttendanceState(time.get());
        result.merge(attendanceState, 1, Integer::sum);
    }

    private boolean isWeekend(final LocalDate attendanceDate) {
        DayOfWeek dayOfWeek = attendanceDate.getDayOfWeek();
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
    }

    private boolean isHoliday(final LocalDate attendanceDate) {
        return Holiday.isHoliday(attendanceDate.getMonthValue(), attendanceDate.getDayOfMonth());
    }
}
