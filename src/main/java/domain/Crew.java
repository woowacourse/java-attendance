package domain;

import static domain.AttendanceStatus.isWeekendOrChristmas;
import static domain.Penalty.COUNSELLING;
import static domain.Penalty.EXPEL;
import static domain.Penalty.NONE;
import static domain.Penalty.WARNING;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Crew {
    private final String name;
    private List<Attendance> attendanceHistory;

    public Crew(String name) {
        this.name = name;
        attendanceHistory = new ArrayList<>();
    }

    public void addAttendanceWithDateTime(LocalDateTime localDateTime) {
        attendanceHistory.add(new Attendance(localDateTime));
    }

    public List<Attendance> getAttendanceHistory() {
        return attendanceHistory;
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

    // 경고 대상자: 결석 2회 이상
    //  - 면담 대상자: 결석 3회 이상
    //  - 제적 대상자: 결석 5회 초과

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
