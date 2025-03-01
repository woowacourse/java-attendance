package model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

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
            if (isWeekend(date.withDayOfMonth(day))) {
                continue;
            }

            boolean flag = false;
            for (int idx = 0; idx < crewsAttendanceRecords.size(); idx++) {
                if (crewsAttendanceRecords.get(idx).isSameDate(nickname, date.withDayOfMonth(day))) {
                    if (crewsAttendanceRecords.get(idx).isAbsent()) {
                        absentCount++;
                        flag = true;
                        break;
                    }
                }
            }

            if (!flag) {
                absentCount++;
            }
        }

        return absentCount;
    }

    private boolean isWeekend(LocalDate date) {
        return date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY;
    }
}
