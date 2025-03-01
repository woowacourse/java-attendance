package model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CrewAttendances {

    private final String nickname;
    private final List<Attendance> attendances;

    public CrewAttendances(String nickname, List<Attendance> attendances) {
        this.nickname = nickname;
        this.attendances = new ArrayList<>(attendances);
    }

    public int calculateLateCountUntilDate(LocalDate date) {
        return (int) attendances.stream()
                .filter(attendance -> attendance.isBefore(date))
                .filter(Attendance::isLate)
                .count();
    }

    public int calculateAttendCountUntilDate(LocalDate date) {
        return (int) attendances.stream()
                .filter(attendance -> attendance.isBefore(date))
                .filter(Attendance::isAttend)
                .count();
    }

    public int calculateAbsentCountUntilDate(LocalDate date) {
        int absentCount = 0;
        for (int day = 1; day < date.getDayOfMonth(); day++) {
            if (isWeekend(date.withDayOfMonth(day)) || isHoliday(date.withDayOfMonth(day))) {
                continue;
            }

            absentCount += increaseAbsentCount(date.withDayOfMonth(day));
        }

        return absentCount;
    }

    private boolean isWeekend(LocalDate date) {
        return date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY;
    }

    private boolean isHoliday(LocalDate date) {
        return date.isEqual(LocalDate.of(2024, 12, 25));
    }

    private int increaseAbsentCount(LocalDate date) {
        for (Attendance crewsAttendanceRecord : attendances) {
            if (crewsAttendanceRecord.isSameDate(date)) {
                if (crewsAttendanceRecord.isAbsent()) {
                    return 1;
                }
                return 0;
            }
        }

        return 1;
    }

    public int allAttendanceCount() {
        return attendances.size();
    }

    public AbsentPenalty determineAttendPenalty(LocalDate date) {
        int absentCount = calculateAbsentCountUntilDate(date);
        int lateCount = calculateLateCountUntilDate(date);

        absentCount += lateCount / 3;
        return AbsentPenalty.determine(absentCount);
    }

    public boolean isSameNickname(String nickname) {
        return this.nickname.equals(nickname);
    }

    public boolean isAlreadyAttend(LocalDate date) {
        return attendances.stream()
                .anyMatch(attendance -> attendance.isSameDate(date));
    }

    public void addAttendance(Attendance attendance) {
        attendances.add(attendance);
    }

    public Attendance findAttendance(LocalDate date) {
        return attendances.stream()
                .filter(attendance -> attendance.isSameDate(date))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 출석 기록이 없습니다."));
    }

    public String getNickname() {
        return nickname;
    }

    public int attendPolicyCountSum(LocalDate date) {
        int absentCount = calculateAbsentCountUntilDate(date);
        int lateCount = calculateLateCountUntilDate(date);

        return absentCount + lateCount;
    }
}
