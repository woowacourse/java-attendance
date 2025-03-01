package model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AttendanceBook {

    private final List<Attendance> crewsAttendanceRecords;

    public AttendanceBook(List<Attendance> crewsAttendanceRecords) {
        this.crewsAttendanceRecords = new ArrayList<>(crewsAttendanceRecords);
    }

    public Attendance check(String nickname, LocalDate date, LocalTime time) {
        AttendanceDateTimeChecker checker = new AttendanceDateTimeChecker();
        checker.determine(date, time);

        Attendance attendance = new Attendance(nickname, date, time);
        if (isAlreadyAttend(nickname, date)) {
            throw new IllegalArgumentException();
        }

        crewsAttendanceRecords.add(attendance);
        return attendance;
    }

    private boolean isAlreadyAttend(String nickname, LocalDate date) {
        return crewsAttendanceRecords.stream()
                .anyMatch(attendance -> attendance.isSameDate(nickname, date));
    }

    public Attendance findAttendance(String nickname, LocalDate date) {
        return crewsAttendanceRecords.stream()
                .filter(attendance -> attendance.isSameDate(nickname, date))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }

    public Attendance update(String updateNickname, LocalDate updateDate, LocalTime updateTime) {
        AttendanceDateTimeChecker checker = new AttendanceDateTimeChecker();
        checker.determine(updateDate, updateTime);

        Attendance findAttendance = findAttendance(updateNickname, updateDate);
        findAttendance.updateTime(updateTime);

        return findAttendance;
    }

    public List<Attendance> findCrewAttendance(String nickname) {
        List<Attendance> crewAttendance = crewsAttendanceRecords.stream()
                .filter(attendance -> attendance.isSameNickname(nickname))
                .toList();

        if (crewAttendance.isEmpty()) {
            throw new IllegalArgumentException("[ERROR] 등록되지 않은 닉네임입니다.");
        }

        return crewAttendance;
    }

    public long calculateAbsentCountByNickname(String nickname) {
        return findCrewAttendance(nickname).stream()
                .filter(Attendance::isAbsent)
                .count();
    }

    public long calculateAbsentCountByNicknameUntilDate(String nickname, LocalDate date) {
        int absentCount = 0;
        for (int day = 1; day < date.getDayOfMonth(); day++) {
            if (isWeekend(date.withDayOfMonth(day)) || isHoliday(date.withDayOfMonth(day))) {
                System.out.println(day);
                continue;
            }

            absentCount += increaseAbsentCountByNickname(nickname, date.withDayOfMonth(day));
        }

        return absentCount;
    }

    private boolean isHoliday(LocalDate date) {
        return date.isEqual(LocalDate.of(2024, 12, 25));
    }

    private int increaseAbsentCountByNickname(String nickname, LocalDate date) {
        for (Attendance crewsAttendanceRecord : crewsAttendanceRecords) {
            if (crewsAttendanceRecord.isSameDate(nickname, date)) {
                if (crewsAttendanceRecord.isAbsent()) {
                    return 1;
                }
                return 0;
            }
        }

        return 1;
    }

    private boolean isWeekend(LocalDate date) {
        return date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY;
    }

    public long calculateLateCountByNickname(String nickname) {
        return findCrewAttendance(nickname).stream()
                .filter(Attendance::isLate)
                .count();
    }
}
