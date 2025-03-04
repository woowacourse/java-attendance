package domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;

public class AttendanceBook {
    private final CrewGroup crewGroup;

    public AttendanceBook() {
        this.crewGroup = new CrewGroup();
    }

    public void addCrew(String name, LocalDate date, LocalTime time) {
        crewGroup.addCrew(name, new Crew(name));
        crewGroup.findCrewByName(name).addAttendance(date, time);
    }

    public Attendance attendCrew(String name, LocalDate date, LocalTime time) {
        return crewGroup.findCrewByName(name).addAttendance(date, time);
    }

    public Attendance editCrew(String name, LocalDate nowDate, LocalDate date, LocalTime time) {
        if (date.isAfter(nowDate)) {
            throw new IllegalArgumentException("[ERROR] 현재보다 이전 날짜만 수정 가능합니다.");
        }
        return crewGroup.findCrewByName(name).updateAttendance(date, time);
    }

    public int calculateAttendanceCount(String name, LocalDate nowDate) {
        return crewGroup.findCrewByName(name).calculateAttendanceCount(nowDate);
    }

    public int calculateLatenessCount(String name, LocalDate nowDate) {
        return crewGroup.findCrewByName(name).calculateLatenessCount(nowDate);
    }

    public int calculateAbsenceCount(String name, LocalDate nowDate) {
        return crewGroup.findCrewByName(name).calculateAbsenceCount(nowDate);
    }

    public Penalty determinePenaltyStatus(String name, LocalDate nowDate) {
        return crewGroup.findCrewByName(name).determinePenaltyStatus(nowDate);
    }

    public List<Crew> findExpulsionRiskCrews(LocalDate nowDate) {
        List<Crew> crewsAtRisk = crewGroup.getCrewsAtRisk(nowDate);
        return sortCrews(crewsAtRisk, nowDate);
    }

    public Crew findCrewByName(String name) {
        if (!crewGroup.containsCrew(name)) {
            throw new IllegalArgumentException("[ERROR] 등록되지 않은 닉네임입니다.");
        }
        return crewGroup.findCrewByName(name);
    }

    public Attendance findAttendance(String name, LocalDate date) {
        return crewGroup.findCrewByName(name).findAttendanceByDate(date);
    }

    private List<Crew> sortCrews(List<Crew> crews, LocalDate nowDate) {
        crews.sort(Comparator
                .comparing((Crew crew) -> crew.determinePenaltyStatus(nowDate))
                .thenComparing(crew -> calculateAdjustedAbsence(crew, nowDate), Comparator.reverseOrder())
                .thenComparing(crew -> calculateRemainingLateness(crew, nowDate), Comparator.reverseOrder())
                .thenComparing(Crew::getName)
        );
        return crews;
    }

    private int calculateAdjustedAbsence(Crew crew, LocalDate nowDate) {
        return crew.calculateAbsenceCount(nowDate) + crew.calculateLatenessCount(nowDate) / 3;
    }

    private int calculateRemainingLateness(Crew crew, LocalDate nowDate) {
        return crew.calculateLatenessCount(nowDate) % 3;
    }
}
