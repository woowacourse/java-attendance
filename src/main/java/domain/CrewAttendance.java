package domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CrewAttendance {
    
    private final Map<LocalDate, Attendance> attendances;
    
    public CrewAttendance(final LocalDate today) {
        this.attendances = generateCalendar(today);
    }
    
    public CrewAttendance(final LocalDate today, final Map<LocalDate, LocalTime> attendances) {
        this(today);
        attendances.forEach((date, time) ->
                this.attendances.put(date, Attendance.of(date, time)));
    }
    
    private Map<LocalDate, Attendance> generateCalendar(final LocalDate today) {
        return IntStream.rangeClosed(1, today.getDayOfMonth())
                .mapToObj(today::withDayOfMonth)
                .filter(AttendanceDate::isAttendDay)
                .collect(Collectors.toMap(
                        date -> date,
                        Attendance::noShow
                ));
    }
    
    public void attend(final LocalDate date, final LocalTime time) {
        validateIsInCalender(date);
        
        attendances.put(date, Attendance.of(date, time));
    }
    
    public void modify(final LocalDate targetDate, final LocalTime newTime) {
        validateIsInCalender(targetDate);
        
        attendances.put(targetDate, Attendance.of(targetDate, newTime));
    }
    
    private void validateIsInCalender(final LocalDate date) {
        if (!attendances.containsKey(date)) {
            throw new IllegalArgumentException("출석할 수 없는 날짜입니다.");
        }
    }
    
    public AttendanceStatus getAttendanceStatusOf(final LocalDate date) {
        validateIsAttendedDay(date);
        
        return attendances.get(date).getStatus();
    }
    
    public Optional<LocalTime> getAttendanceTimeOf(final LocalDate date) {
        validateIsAttendedDay(date);
        
        return attendances.get(date).getAttendTime().getAttendTime();
    }
    
    private void validateIsAttendedDay(final LocalDate date) {
        if (!attendances.containsKey(date)) {
            throw new IllegalArgumentException("출석하지 않은 날입니다.");
        }
    }
    
    public Set<Attendance> getAllAttendances() {
        return new HashSet<>(attendances.values());
    }
    
    public int countAttendanceStatusOf(AttendanceStatus targetStatus) {
        return (int) attendances.values().stream()
                .map(Attendance::getStatus)
                .filter(status -> status == targetStatus)
                .count();
    }
    
    public ExpelWarning getExpelWarning() {
        final var lateCount = countAttendanceStatusOf(AttendanceStatus.지각);
        final var absentCount = countAttendanceStatusOf(AttendanceStatus.결석);
        
        return ExpelWarning.of(lateCount, absentCount);
    }
}
