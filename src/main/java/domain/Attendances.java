package domain;

import java.time.Clock;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Attendances {
    private static final Integer LATE_COUNT_FOR_ABSENCE = 3;
    private final List<Attendance> attendances;

    public Attendances() {
        attendances = new ArrayList<>();
    }

    private Attendances(List<Attendance> attendances) {
        this.attendances = attendances;
    }

    public void add(Attendance attendance) {
        validateAttendance(attendance);
        attendances.add(attendance);
    }

    private void validateAttendance(Attendance attendance) {
        if (attendances.contains(attendance)) {
            throw new IllegalStateException("[ERROR] 이미 출석이 완료되었습니다. 수정 기능을 이용하세요.");
        }
    }

    public Integer getLateCount(Clock clock) {
        return (int) attendances.stream()
                .filter(attendance -> !attendance.has(LocalDate.now(clock)))
                .filter(Attendance::isLate)
                .count();
    }

    public Integer getAbsentCount(Clock clock) {
        return (int) attendances.stream()
                .filter(attendance -> !attendance.has(LocalDate.now(clock)))
                .filter(Attendance::isAbsent)
                .count();
    }

    public Integer getTotalCount() {
        return attendances.size();
    }

    public Attendance findByDay(Day day) {
        return attendances.stream()
                .filter(attendance -> attendance.has(day.getDate()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("[ERROR] 해당일에 출석 기록이 없습니다."));
    }

    public Boolean isAlreadyAttended(LocalDate date) {
        return attendances.stream()
                .anyMatch(attendance -> attendance.has(date));
    }

    public void recordAbsences(Clock clock) {
        LocalDate.of(2025, 2, 1)
                .datesUntil(LocalDate.now(clock))
                .filter(CustomDayOfWeek::isWeekDay)
                .filter(date -> !Holiday.isHoliday(date))
                .filter(date -> !isAlreadyAttended(date))
                .forEach(date -> add(new Attendance(new Day(date), null)));
    }

    public Penalty getPenaltyStatus(Clock clock) {
        Integer penaltyPoint = getAbsentCount(clock) + getLateCount(clock) / LATE_COUNT_FOR_ABSENCE;
        return Penalty.getPenaltyOf(penaltyPoint);
    }

    public Attendances toImmutable() {
        return new Attendances(
                List.copyOf(
                        attendances.stream()
                                .map(Attendance::toImmutable)
                                .toList()
                )
        );
    }


    public List<Attendance> getAttendances() {
        return new ArrayList<>(attendances);
    }
}
