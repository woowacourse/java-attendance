package domain;

import domain.vo.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CrewAttendance {
    
    private static final LocalTime CAMPUS_OPEN_TIME = LocalTime.of(8, 0);
    private static final LocalTime CAMPUS_CLOSE_TIME = LocalTime.of(23, 0);
    private static final List<DayOfWeek> weekends = List.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);
    
    private final Map<LocalDate, Optional<LocalTime>> calendar;
    
    public CrewAttendance(final LocalDate today) {
        this.calendar = generateCalendar(today);
    }
    
    private Map<LocalDate, Optional<LocalTime>> generateCalendar(final LocalDate today) {
        return IntStream.rangeClosed(1, today.getDayOfMonth())
                .mapToObj(today::withDayOfMonth)
                .collect(Collectors.toMap(date -> date, date -> Optional.empty()));
    }
    
    public AttendResult attend(final LocalDate date, final LocalTime time) {
        validateIsDateAvailable(date);
        validateCampusOpen(time);
        validateHasAlreadyAttended(date);
        
        calendar.put(date, Optional.of(time));
        
        return new AttendResult(date, time, AttendanceStatus.of(date.getDayOfWeek(), time));
    }
    
    public AttendanceModifyResult modify(final LocalDate targetDate, final LocalTime newTime) {
        validateIsDateAvailable(targetDate);
        validateCampusOpen(newTime);
        
        final var targetDateDayOfWeek = targetDate.getDayOfWeek();
        final var oldTime = calendar.put(targetDate, Optional.of(newTime)).orElse(null);
        
        return new AttendanceModifyResult(
                targetDate,
                Optional.ofNullable(oldTime),
                Optional.ofNullable(oldTime).map(time -> AttendanceStatus.of(targetDateDayOfWeek, time)),
                newTime,
                AttendanceStatus.of(targetDateDayOfWeek, newTime)
        );
    }
    
    public AttendanceRecordFindResults findRecord() {
        final var results = calendar.entrySet().stream()
                .filter(entry -> !weekends.contains(entry.getKey().getDayOfWeek()))
                .filter(entry -> !Holiday.isHoliday(entry.getKey()))
                .map(entry -> new AttendanceRecord(
                        entry.getKey(),
                        entry.getValue(),
                        determineStatus(entry.getKey().getDayOfWeek(), entry.getValue())))
                .collect(Collectors.toSet());
        final var lateCount = getCountOfAttendanceStatusIs(AttendanceStatus.지각);
        final var absentCount = getCountOfAttendanceStatusIs(AttendanceStatus.결석);
        return new AttendanceRecordFindResults(
                results,
                getCountOfAttendanceStatusIs(AttendanceStatus.출석), lateCount, absentCount,
                ExpelWarning.of(lateCount, absentCount)
        );
    }
    
    private int getCountOfAttendanceStatusIs(AttendanceStatus status) {
        return (int) calendar.entrySet().stream()
                .filter(entry -> !weekends.contains(entry.getKey().getDayOfWeek()))
                .filter(entry -> !Holiday.isHoliday(entry.getKey()))
                .filter(entry -> determineStatus(entry.getKey().getDayOfWeek(), entry.getValue()) == status)
                .count();
    }
    
    private AttendanceStatus determineStatus(final DayOfWeek dayOfWeek, final Optional<LocalTime> attendTime) {
        if (attendTime.isEmpty()) {
            return AttendanceStatus.결석;
        }
        return AttendanceStatus.of(dayOfWeek, attendTime.get());
    }
    
    public ExpelWarningResult calculateExpelWarning() {
        final var lateCount = getCountOfAttendanceStatusIs(AttendanceStatus.지각);
        final var absentCount = getCountOfAttendanceStatusIs(AttendanceStatus.결석);
        
        return new ExpelWarningResult(ExpelWarning.of(lateCount, absentCount), lateCount, absentCount);
    }
    
    private void validateIsDateAvailable(final LocalDate date) {
        validateIsInCalender(date);
        validateNotWeekend(date.getDayOfWeek());
        validateNotHoliday(date);
    }
    
    private void validateIsInCalender(final LocalDate date) {
        if (!calendar.containsKey(date)) {
            throw new IllegalArgumentException("출석할 수 없는 날짜입니다.");
        }
    }
    
    private void validateNotWeekend(final DayOfWeek dayOfWeek) {
        if (weekends.contains(dayOfWeek)) {
            throw new IllegalArgumentException("주말에는 출석할 수 없습니다.");
        }
    }
    
    private void validateNotHoliday(final LocalDate date) {
        if (Holiday.isHoliday(date)) {
            throw new IllegalArgumentException("공휴일에는 출석할 수 없습니다.");
        }
    }
    
    private void validateHasAlreadyAttended(final LocalDate date) {
        if (hasAlreadyAttended(date)) {
            throw new IllegalArgumentException("하루에 여러번 출석할 수 없습니다. 수정 기능을 이용하세요.");
        }
    }
    
    private boolean hasAlreadyAttended(final LocalDate date) {
        return calendar.get(date).isPresent();
    }
    
    private void validateCampusOpen(final LocalTime time) {
        if (!isCampusOpen(time)) {
            throw new IllegalArgumentException("캠퍼스 운영시간이 아닙니다. (08:00~23:00)");
        }
    }
    
    private boolean isCampusOpen(final LocalTime time) {
        return !time.isBefore(CAMPUS_OPEN_TIME) && !time.isAfter(CAMPUS_CLOSE_TIME);
    }
}
