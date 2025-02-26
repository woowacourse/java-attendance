package attendance.domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import attendance.SystemDateConfig;

public record AttendanceHistory(Map<LocalDate, Optional<Attendance>> history) {

    public static AttendanceHistory from(Map<LocalDate, Attendance> attendances) {
        Map<LocalDate, Optional<Attendance>> history = createAttendanceHistory(attendances);
        return new AttendanceHistory(history);
    }

    public static Map<LocalDate, Optional<Attendance>> createAttendanceHistory(
        Map<LocalDate, Attendance> attendances) {
        Map<LocalDate, Optional<Attendance>> history = new HashMap<>();
        List<LocalDate> workingDays = extractWorkingDays();
        for (LocalDate workingDay : workingDays) {
            history.put(workingDay, Optional.ofNullable(attendances.get(workingDay)));
        }
        return history;
    }

    private static List<LocalDate> extractWorkingDays() {
        return Stream.iterate(SystemDateConfig.START_CALENDER, date -> date.plusDays(1))
            .limit(ChronoUnit.DAYS.between(SystemDateConfig.START_CALENDER, getLastDay()))
            .filter(AttendanceHistory::isValidWorkingDay)
            .collect(Collectors.toList());
    }

    private static LocalDate getLastDay() {
        return SystemDateConfig.NOW_DATE.plusDays(1);
    }

    private static boolean isValidWorkingDay(LocalDate date) {
        return !isWeekend(date) && !isHoliday(date);
    }

    private static boolean isHoliday(LocalDate date) {
        return SystemDateConfig.DAT_OF_HOLIDAY.stream()
            .anyMatch(day -> date.getDayOfMonth() == day);
    }

    private static boolean isWeekend(LocalDate date) {
        return date.getDayOfWeek().getValue() >= DayOfWeek.SATURDAY.getValue();
    }

    public EnumMap<AttendanceStatus, Integer> countStatusOnHistory() {
        EnumMap<AttendanceStatus, Integer> statistics = new EnumMap<>(AttendanceStatus.class);
        for (LocalDate localDate : history.keySet()) {
            AttendanceStatus status = history.get(localDate)
                .map(Attendance::attendanceStatus)
                .orElse(AttendanceStatus.ABSENCE);

            statistics.put(status, statistics.getOrDefault(status, 0) + 1);
        }
        return statistics;
    }
}

