package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.MonthDay;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import utils.DayOfWeeks;
import utils.SolarCalendarHoliday;

public class AttendanceHistory {
    private static final LocalDate ATTENDANCE_HISTORY_RECORD_BEGIN_DATE = LocalDate.of(2024, 12, 1);
    private static final LocalTime START_TIME = LocalTime.of(8, 0);
    private static final LocalTime END_TIME = LocalTime.of(23, 0);

    private final Crew crew;
    private final Map<LocalDate, AttendanceRecord> attendanceHistory;

    public AttendanceHistory(final Crew crew) {
        this.attendanceHistory = new HashMap<>();
        this.crew = crew;
    }

    public AttendanceRecord attendance(final LocalDateTime attendanceDateTime) {
        validateAttendanceDay(attendanceDateTime);
        validateAttendanceTime(attendanceDateTime);
        validateAlreadyAttendance(attendanceDateTime);
        final AttendanceRecord attendanceRecord = new AttendanceRecord(attendanceDateTime);
        attendanceHistory.put(attendanceDateTime.toLocalDate(), attendanceRecord);
        return attendanceRecord;
    }

    private void validateAlreadyAttendance(final LocalDateTime attendanceDateTime) {
        if (attendanceHistory.containsKey(attendanceDateTime.toLocalDate())) {
            throw new IllegalStateException("주어진 날짜는 이미 출석이 완료되었습니다.");
        }
    }

    public boolean isAlreadyAttendance(final LocalDate targetDate) {
        return attendanceHistory.containsKey(targetDate);
    }

    private void validateAttendanceDay(final LocalDateTime attendanceDateTime) {
        if (!isAttendanceDay(attendanceDateTime.toLocalDate())) {
            throw new IllegalArgumentException("주어진 날짜는 출석하는 날이 아닙니다.");
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
            throw new IllegalArgumentException("출석이 가능한 시간이 아닙니다.");
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

    private boolean isAttendanceDay(final LocalDate targetDate) {
        return !(DayOfWeeks.isWeekend(targetDate.getDayOfWeek())
                || SolarCalendarHoliday.isHoliday(MonthDay.from(targetDate)));
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
        return calculateRiskOfExpulsionStatus(targetDate) != RiskOfExpulsionStatus.NORMAL;
    }

    public RiskOfExpulsionStatus calculateRiskOfExpulsionStatus(final LocalDate targetDate) {
        final int absenceCount = calculateAbsenceCountConsideredThreeLateToAbsence(targetDate);
        return RiskOfExpulsionStatus.calculateRiskOfExpulsionStatus(absenceCount);
    }

    private int calculateAbsenceCountConsideredThreeLateToAbsence(final LocalDate targetDate) {
        final Map<AttendanceStatus, Integer> statistics = calculateAttendanceStatusStatistics(targetDate);
        return statistics.get(AttendanceStatus.ABSENCE) + statistics.get(AttendanceStatus.LATE) / 3;
    }

    public Crew getCrew() {
        return crew;
    }
}
