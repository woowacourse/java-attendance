package domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Attendances {
    private static final Integer LATE_COUNT_FOR_ABSENT = 3;

    private final List<Attendance> attendances = new ArrayList<>();

    public Attendances() {
    }

    public void addAttendance(Attendance attendance) {
        attendances.add(attendance);
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

    public Integer getNonAttendanceCount() {
        return attendances.size() - -getAbsentCount() - getLateCount() / LATE_COUNT_FOR_ABSENT;
    }

    public Boolean isAlreadyAttended(LocalDate date) {
        return attendances.stream()
                .anyMatch(attendance -> attendance.isEqualTo(date));
    }

    public Attendance findByDate(LocalDate date) {
        return attendances.stream()
                .filter(attendance -> attendance.isEqualTo(date))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 날짜는 출석일이 아닙니다."));
    }

    public Integer getTotalAttendanceCount() {
        return attendances.size();
    }

    public List<AttendanceDto> createAttendanceDtos() {
        return attendances.stream()
                .map(Attendance::toDto)
                .toList();
    }

    public void recordAbsence() {
        LocalDate.now().withDayOfMonth(1)
                .datesUntil(LocalDate.now())
                .filter(date -> date.getDayOfWeek().getValue() < 6)
                .filter(date -> !Holiday.isHoliday(date))
                .filter(date -> !isAlreadyAttended(date))
                .forEach(date -> addAttendance(new Attendance(new Day(date), null)));
    }
}
