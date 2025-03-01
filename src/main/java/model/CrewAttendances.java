package model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

public class CrewAttendances {

    private final String nickname;
    private final List<Attendance> attendances;

    public CrewAttendances(String nickname, List<Attendance> attendances) {
        this.nickname = nickname;
        this.attendances = attendances;
    }

    public long calculateLateCountUntilDate(LocalDate date) {
        return attendances.stream()
                .filter(attendance -> attendance.isBefore(date))
                .filter(Attendance::isLate)
                .count();
    }

    public long calculateAttendCountUntilDate(LocalDate date) {
        return attendances.stream()
                .filter(attendance -> attendance.isBefore(date))
                .filter(Attendance::isAttend)
                .count();
    }

    public long calculateAbsentCountUntilDate(LocalDate date) {
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
}
