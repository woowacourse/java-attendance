package domain;

import static domain.AttendanceStatus.isWeekendOrChristmas;
import static error.ErrorMessage.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Crew {
    private final String name;
    private List<Attendance> attendances;

    public Crew(String name) {
        this.name = name;
        attendances = new ArrayList<>();
    }

    public Attendance addAttendanceWithDateTime(LocalDateTime localDateTime) {
        Attendance attendance = new Attendance(localDateTime);
        attendances.add(attendance);
        return attendance;
    }

    public ModifyResult modifyAttendedTime(int date, LocalTime localTime) {
        if (isEmptyDay(date)) {
            throw new IllegalArgumentException(EMPTY_DATE.getMessage());
        }
        return attendances.stream()
                .filter(attendance -> attendance.getDayOfMonth() == date)
                .findFirst()
                .map(attendance -> attendance.changeTimeTo(localTime))
                .orElseThrow();
    }

    public String getName() {
        return name;
    }

    public List<Attendance> getAttendances() {
        return attendances;
    }

    public boolean isSameName(String name) {
        return this.name.equals(name);
    }

    public void addAttendance(Attendance attendance) {
        if (isAlreadyAttendedDay(attendance)) {
            throw new IllegalArgumentException(ALREADY_ATTENDED.getMessage());
        }
        attendances.add(attendance);
    }

    private boolean isEmptyDay(int date) {
        return attendances.stream()
                .noneMatch(attendance -> attendance.getDayOfMonth() == date);
    }

    private boolean isAlreadyAttendedDay(Attendance newAttendance) {
        return attendances.stream()
                .anyMatch(attendance -> attendance.getDayOfMonth() == newAttendance.getDayOfMonth());
    }

    public void fillEmptyDateWithAbsent(LocalDate localDate) {
        for (int day = 1; day <= localDate.getDayOfMonth(); day++) {
            if (isEmptyDay(day) && !isWeekendOrChristmas(LocalDate.of(2024, 12, day))) {
                addDummyAbsent(day);
            }
        }
    }

    private void addDummyAbsent(int day) {
        attendances.add(new Attendance(LocalDateTime.of(2024, 12, day, 22, 59, 59)));
    }

    public int getAttendCount() {
        return (int) attendances.stream()
                .filter(attendance -> attendance.getAttendanceStatus() == AttendanceStatus.ATTEND)
                .count();
    }

    public int getLateCount() {
        return (int) attendances.stream()
                .filter(attendance -> attendance.getAttendanceStatus() == AttendanceStatus.LATE)
                .count();
    }

    public int getAbsentCount() {
        return (int) attendances.stream()
                .filter(attendance -> attendance.getAttendanceStatus() == AttendanceStatus.ABSENT)
                .count();
    }

    public int getPenaltyStandard() {
        return getAbsentCount() + (getLateCount() / 3);
    }

    public Penalty getPenalty() {
        return Penalty.getPenalty(getPenaltyStandard());
    }

}
