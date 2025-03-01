package model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class AttendanceBook {

    private final List<CrewAttendances> crewsAttendances;

    public AttendanceBook(List<CrewAttendances> crewsAttendances) {
        this.crewsAttendances = crewsAttendances;
    }

    public Attendance check(String nickname, LocalDate date, LocalTime time) {
        AttendanceDateTimeChecker checker = new AttendanceDateTimeChecker();
        checker.determine(date, time);

        CrewAttendances crew = findCrewAttendance(nickname);
        if (crew.isAlreadyAttend(date)) {
            throw new IllegalArgumentException();
        }

        Attendance attendance = new Attendance(date, time);
        crew.addAttendance(attendance);
        return attendance;
    }

    public Attendance findAttendance(String nickname, LocalDate date) {
        CrewAttendances crewAttendances = findCrewAttendance(nickname);
        return crewAttendances.findAttendance(date);
    }

    public Attendance update(String updateNickname, LocalDate updateDate, LocalTime updateTime) {
        AttendanceDateTimeChecker checker = new AttendanceDateTimeChecker();
        checker.determine(updateDate, updateTime);

        Attendance findAttendance = findAttendance(updateNickname, updateDate);
        findAttendance.updateTime(updateTime);

        return findAttendance;
    }

    public CrewAttendances findCrewAttendance(String nickname) {
        return crewsAttendances.stream()
                .filter(crew -> crew.isSameNickname(nickname))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 등록되지 않은 닉네임입니다."));
    }
}
