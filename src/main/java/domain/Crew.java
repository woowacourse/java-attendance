package domain;

import static domain.AttendanceStatus.isWeekendOrChristmas;
import static domain.Penalty.COUNSELLING;
import static domain.Penalty.EXPEL;
import static domain.Penalty.NONE;
import static domain.Penalty.WARNING;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Crew {
    private final String name;
    private List<Attendance> attendanceHistory;

    public Crew(String name) {
        this.name = name;
        attendanceHistory = new ArrayList<>();
    }

    public Attendance addAttendanceWithDateTime(LocalDateTime localDateTime) {
        Attendance attendance = new Attendance(localDateTime);
        attendanceHistory.add(attendance);
        return attendance;
    }

    public ModifyResult modifyAttendedTime(int date, LocalTime localTime) {
        if (isEmptyDay(date)) {
            throw new IllegalArgumentException(ERROR_MESSAGE.EMPTY_DATE.getMessage());
        }
        return attendanceHistory.stream().filter(attendance -> attendance.getDayOfMonth() == date).findFirst()
                .map(attendance -> attendance.changeTimeTo(localTime)).orElse(null);
    }

    public String getName() {
        return name;
    }

    public List<Attendance> getAttendanceHistory() {
        return attendanceHistory;
    }

    public boolean isSameName(String name) {
        return this.name.equals(name);
    }

    public void addAttendance(Attendance attendance) {
        if (isAlreadyAttendedDay(attendance)) {
            throw new IllegalArgumentException(ERROR_MESSAGE.ALREADY_ATTENDED.getMessage());
        }
        attendanceHistory.add(attendance);
    }

    private boolean isEmptyDay(int date) {
        return attendanceHistory.stream()
                .noneMatch(attendance -> attendance.getDayOfMonth() == date);
    }

    private boolean isAlreadyAttendedDay(Attendance newAttendance) {
        return attendanceHistory.stream()
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
        attendanceHistory.add(new Attendance(LocalDateTime.of(2024, 12, day, 22, 59, 59)));
    }

    public int getAttendCount() {
        return (int) attendanceHistory.stream()
                .filter(attendance -> attendance.getAttendanceStatus() == AttendanceStatus.ATTEND)
                .count();
    }

    public int getLateCount() {
        return (int) attendanceHistory.stream()
                .filter(attendance -> attendance.getAttendanceStatus() == AttendanceStatus.LATE)
                .count();
    }

    public int getAbsentCount() {
        return (int) attendanceHistory.stream()
                .filter(attendance -> attendance.getAttendanceStatus() == AttendanceStatus.ABSENT)
                .count();
    }

    public Penalty getPenalty() {
        int penaltyStandard = getAbsentCount() + (getLateCount() / 3);
        if (penaltyStandard > EXPEL.getCount()) {
            return EXPEL;
        }
        if (penaltyStandard >= COUNSELLING.getCount()) {
            return COUNSELLING;
        }
        if (penaltyStandard >= WARNING.getCount()) {
            return WARNING;
        }
        return NONE;
    }


}
