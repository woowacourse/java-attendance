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
        validateAlreadyAttend(date, crew);

        Attendance attendance = new Attendance(date, time);
        crew.addAttendance(attendance);
        return attendance;
    }

    private void validateAlreadyAttend(LocalDate date, CrewAttendances crew) {
        if (crew.isAlreadyAttend(date)) {
            throw new IllegalArgumentException("[ERROR] 이미 출석했습니다. 수정 기능을 이용하세요.");
        }
    }

    public CrewAttendances findCrewAttendance(String nickname) {
        return crewsAttendances.stream()
                .filter(crew -> crew.isSameNickname(nickname))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 등록되지 않은 닉네임입니다."));
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

    public List<String> allNames() {
        return crewsAttendances.stream()
                .map(CrewAttendances::getNickname)
                .distinct()
                .toList();
    }
}
