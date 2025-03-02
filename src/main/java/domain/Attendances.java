package domain;

import static domain.AlertCode.COUNSELING;
import static domain.AlertCode.EXPULSION;
import static domain.AlertCode.NORMAL;
import static domain.AlertCode.WARNING;
import static domain.AttendanceCode.ABSENT;
import static domain.AttendanceCode.LATE;
import static domain.AttendanceCode.PRESENT;

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

    private int calculateAttendanceCode(AttendanceCode attendanceCode) {
        return Math.toIntExact(attendances.stream()
                .filter(attendance -> attendance.calculateAttendanceCode() == attendanceCode)
                .count());
    }

    public int calculatePresent() {
        return calculateAttendanceCode(PRESENT);
    }

    public int calculateLate() {
        return calculateAttendanceCode(LATE);
    }

    public int calculateAbsent() {
        return calculateAttendanceCode(ABSENT);
    }

    public AlertCode calucateAlertCode() {
        int alertThreshold = calculateAbsent() + (calculateLate() / 3);
        if (alertThreshold >= EXPULSION.getLimit()) {
            return EXPULSION;
        }

        if (alertThreshold >= COUNSELING.getLimit()) {
            return COUNSELING;
        }

        if (alertThreshold >= WARNING.getLimit()) {
            return WARNING;
        }
        return NORMAL;
    }

    private void validateFutureDate(DayOfMonth dayOfMonth, LocalDate today) {
        if (dayOfMonth.isHoliday(today)) {
            if (today.getDayOfMonth() < dayOfMonth.dayOfMonth()) {
                throw new IllegalArgumentException("없는 날짜는 변경할 수 없습니다.");
            }
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

    public void addAttendance(Attendance attendance) {
        attendances.add(attendance);
    }

    public boolean isExist(LocalDate today) {
        return attendances.stream()
                .anyMatch(attendance -> attendance.isSameDay(today));
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
