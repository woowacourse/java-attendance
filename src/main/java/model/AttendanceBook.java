package model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
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

    public List<CrewAttendances> findSortedRiskOfDismissalCrews(LocalDate date) {
        return crewsAttendances.stream()
                .filter(crew -> crew.determineAttendPenalty(date) != AbsentPenalty.NONE)
                .sorted(riskOfDismissalComparator(date))
                .toList();
    }

    private Comparator<CrewAttendances> riskOfDismissalComparator(LocalDate date) {
        return (o1, o2) -> {
            int count1 = o1.attendPolicyCountSum(date);
            int count2 = o2.attendPolicyCountSum(date);

            if (count1 == count2) {
                return o1.getNickname().compareTo(o2.getNickname());
            }

            return count2 - count1;
        };
    }
}
