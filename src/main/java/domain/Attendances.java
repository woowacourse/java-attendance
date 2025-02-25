package domain;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class Attendances {
    private static final Integer LATE_COUNT_FOR_ABSENT = 3;

    private final List<Attendance> attendances;

    public Attendances() {
        attendances = new ArrayList<>();
    }

    public Attendances(Attendances attendances) {
        this.attendances = new ArrayList<>(attendances.getAttendances());
    }

    public void add(Attendance attendance) {
        attendances.add(attendance);
    }

    public List<Attendance> getAttendances() {
        return attendances.stream()
                .map(Attendance::new)
                .toList();
    }

    public Integer getLateCount() {
        return (int) attendances.stream()
                .filter(Attendance::getLate)
                .count();
    }

    public Integer getAbsentCount() {
        return (int) attendances.stream()
                .filter(Attendance::getAbsent)
                .count();
    }

    public PenaltyStatus getPenaltyStatus() {
        return PenaltyStatus.getInstance(getNonAttendanceCount());
    }

    private Integer getNonAttendanceCount() {
        return attendances.size() - -getAbsentCount() - getLateCount() / LATE_COUNT_FOR_ABSENT;
    }

    public Integer getTotalAttendanceCount() {
        return attendances.size();
    }

    public Boolean isAlreadyAttended(LocalDate date) {
        return attendances.stream()
                .anyMatch(attendance -> attendance.isEqualTo(date));
    }

    public void recordAbsence() {
        LocalDate.now().withDayOfMonth(1)
                .datesUntil(LocalDate.now())
                .filter(date -> date.getDayOfWeek().getValue() < 6)
                .filter(date -> !Holiday.isHoliday(date))
                .filter(date -> !isAlreadyAttended(date))
                .forEach(date -> add(new Attendance(new Day(date), null)));
    }

    public Attendance findByDate(Integer dayOfMonth) {
        LocalDate today = LocalDate.now();

        int month = today.getMonth().getValue();
        LocalDate date = LocalDate.of(today.getYear(), month, dayOfMonth);
        validateDayOfMonth(dayOfMonth, today);

        return attendances.stream()
                .filter(attendance -> attendance.isEqualTo(date))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 날짜는 출석일이 아닙니다."));
    }

    private void validateDayOfMonth(Integer dayOfMonth, LocalDate today) {
        YearMonth yearMonth = YearMonth.of(today.getYear(), today.getMonth().getValue());
        if (dayOfMonth < 1 || dayOfMonth > yearMonth.lengthOfMonth()) {
            throw new IllegalArgumentException("[ERROR] 존재하지 않는 날짜입니다.");
        }
    }
}
