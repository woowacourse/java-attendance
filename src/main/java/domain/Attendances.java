package domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Attendances {
    private final List<Attendance> attendances = new ArrayList<>();

    public void add(Attendance attendance) {
        validateAttendance(attendance);
        attendances.add(attendance);
    }

    private void validateAttendance(Attendance attendance) {
        if (attendances.contains(attendance)) {
            throw new IllegalStateException("[ERROR] 이미 출석이 완료되었습니다. 수정 기능을 이용하세요.");
        }
    }

    public Integer getLateCount() {
        return (int) attendances.stream()
                .filter(Attendance::isLate)
                .count();
    }

    public Integer getAbsentCount() {
        return (int) attendances.stream()
                .filter(Attendance::isAbsent)
                .count();
    }

    public Integer getTotalCount() {
        return attendances.size();
    }

    public Attendance findByDay(Day day) {
        return attendances.stream()
                .filter(attendance -> attendance.has(day))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("[ERROR] 해당일에 출석 기록이 없습니다."));
    }

    private Boolean isAlreadyAttended(LocalDate date) {
        return attendances.stream()
                .anyMatch(attendance -> attendance.has(new Day(date)));
    }

    public void recordAbsences() {
        LocalDate.of(2025, 2, 1)
                .datesUntil(LocalDate.now())
                .filter(CustomDayOfWeek::isWeekDay)
                .filter(date -> !Holiday.isHoliday(date))
                .filter(date -> !isAlreadyAttended(date))
                .forEach(date -> add(new Attendance(new Day(date), null)));
    }


}
