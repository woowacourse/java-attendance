package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import util.DayConverter;

public class Attendances {
    private List<Attendance> attendances;

    public Attendances(List<Attendance> attendances) {
        this.attendances = new ArrayList<>(attendances);
    }

    private void validateFutureDate(DayOfMonth dayOfMonth, LocalDate today) {
        if (today.getDayOfMonth() < dayOfMonth.dayOfMonth()) {
            throw new IllegalArgumentException("미래 날짜는 변경할 수 없습니다.");
        }
    }

    public Attendance getSpecificAttendance(DayOfMonth dayOfMonth, LocalDate today) {
        validateFutureDate(dayOfMonth, today);
        LocalDate changedDate = DayConverter.combineDayAndDate(dayOfMonth, today);
        return attendances.stream()
                .filter(attendance -> attendance.isSameDay(changedDate))
                .findAny()
                .get();
    }

    private void deleteSpecificAttendance(DayOfMonth dayOfMonth, LocalDate today) {
        validateFutureDate(dayOfMonth, today);
        LocalDate changedDate = DayConverter.combineDayAndDate(dayOfMonth, today);
        attendances = attendances.stream()
                .filter(attendance -> !attendance.isSameDay(changedDate))
                .collect(Collectors.toList());
    }

    public Attendance changeAttendance(DayOfMonth dayOfMonth, LocalDate today, LocalTime changeTime) {
        LocalDate changedDate = DayConverter.combineDayAndDate(dayOfMonth, today);
        Attendance changedAttendance = new Attendance(LocalDateTime.of(changedDate, changeTime));
        deleteSpecificAttendance(dayOfMonth, today);
        attendances.add(changedAttendance);
        return changedAttendance;
    }

    public List<Attendance> getAttendances() {
        return Collections.unmodifiableList(attendances);
    }
}
