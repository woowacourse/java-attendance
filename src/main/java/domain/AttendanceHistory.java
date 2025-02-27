package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AttendanceHistory {

    private static final int CHRISTMAS_MONTH = 12;
    private static final int CHRISTMAS_MONTH_OF_DAY = 25;
    private static final LocalDate ATTENDANCE_HISTORY_RECORD_BEGIN_DATE = LocalDate.of(2024, 12, 1);
    private static final LocalTime START_TIME = LocalTime.of(8, 0);
    private static final LocalTime END_TIME = LocalTime.of(23, 0);
    ;

    private final Crew crew;
    private final Map<LocalDate, AttendanceRecord> attendanceHistory;

    public AttendanceHistory(final Crew crew) {
        this.attendanceHistory = new HashMap<>();
        this.crew = crew;
    }

    public AttendanceRecord attendance(final LocalDateTime attendanceDateTime) {
        validateAttendanceDay(attendanceDateTime);
        validateAlreadyAttendance(attendanceDateTime);
        final AttendanceRecord attendanceRecord = new AttendanceRecord(attendanceDateTime);
        attendanceHistory.put(attendanceDateTime.toLocalDate(), attendanceRecord);
        return attendanceRecord;
    }

    private void validateAlreadyAttendance(final LocalDateTime attendanceDateTime) {
        if (attendanceHistory.containsKey(attendanceDateTime.toLocalDate())) {
            throw new IllegalStateException();
        }
    }

    private void validateAttendanceDay(final LocalDateTime attendanceDateTime) {
        if (!isAttendanceDay(attendanceDateTime.toLocalDate())) {
            throw new IllegalArgumentException();
        }
    }

    public AttendanceRecord updateTimeByDate(final LocalDateTime afterTime) {
        validateAttendanceDay(afterTime);
        validateAttendanceTime(afterTime);
        final AttendanceRecord prevRecord = attendanceHistory.get(afterTime.toLocalDate());
        final AttendanceRecord newRecord = new AttendanceRecord(afterTime);
        attendanceHistory.put(afterTime.toLocalDate(), newRecord);
        return prevRecord;
    }

    private static void validateAttendanceTime(final LocalDateTime afterTime) {
        final LocalTime time = afterTime.toLocalTime();
        if (time.isBefore(START_TIME) || time.isAfter(END_TIME)) {
            throw new IllegalArgumentException();
        }
    }

    public AttendanceRecord findByDate(final LocalDate date) {
        validateAttendanceDay(LocalDateTime.of(date, LocalTime.NOON));
        if (!attendanceHistory.containsKey(date)) {
            return AttendanceRecord.empty(date);
        }
        return attendanceHistory.get(date);
    }

    public List<AttendanceRecord> findAllUntilBeforeToday(final LocalDate targetDate) {
        return ATTENDANCE_HISTORY_RECORD_BEGIN_DATE.datesUntil(targetDate)
                .filter(this::isAttendanceDay)
                .map(date -> attendanceHistory.getOrDefault(date, AttendanceRecord.empty(date)))
                .collect(Collectors.toList());
    }

    private boolean isAttendanceDay(final LocalDate date) {
        final DayOfWeek dayOfWeek = date.getDayOfWeek();
        if (DayOfWeek.SATURDAY == dayOfWeek || DayOfWeek.SUNDAY == dayOfWeek
                || (date.getMonthValue() == CHRISTMAS_MONTH && date.getDayOfMonth() == CHRISTMAS_MONTH_OF_DAY)) {
            return false;
        }
        return true;
    }

    public Crew getCrew() {
        return crew;
    }

    public Map<AttendanceStatus, Integer> calculateAttendanceStatusStatistics(final LocalDate targetDate) {
        final Map<AttendanceStatus, Integer> statistic = initStatistics();
        findAllUntilBeforeToday(targetDate)
                .forEach(record -> statistic.merge(record.calculateAttendanceStatus(), 1, Integer::sum));
        return statistic;
    }

    private Map<AttendanceStatus, Integer> initStatistics() {
        return Arrays.stream(AttendanceStatus.values())
                .collect(Collectors.toMap(Function.identity(), status -> 0));
    }

    public boolean isRiskOfExpulsion(final LocalDate targetDate) {
        final Map<AttendanceStatus, Integer> statistics = calculateAttendanceStatusStatistics(targetDate);
        final int absenceCount = statistics.get(AttendanceStatus.ABSENCE) + statistics.get(AttendanceStatus.LATE) / 3;
        final RiskOfExpulsionStatus status = RiskOfExpulsionStatus.calculateRiskOfExpulsionStatus(
                absenceCount);
        return status != RiskOfExpulsionStatus.NORMAL;
    }

    public RiskOfExpulsionStatus calculateRiskOfExpulsionStatus(final LocalDate targetDate) {
        return null;
    }
}
