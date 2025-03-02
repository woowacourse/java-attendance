package domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
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
        Crew crew = findCrewByName(name);
        if (crew.hasAlreadyAttended(date)) {
            throw new IllegalArgumentException("[ERROR] 이미 출석한 경우 수정 기능을 사용하세요.");
        }
        return crew.addAttendance(date, time);
    }

    public Attendance editCrew(String name, LocalDate nowDate, LocalDate date, LocalTime time) {
        if (date.isAfter(nowDate)) {
            throw new IllegalArgumentException("[ERROR] 현재보다 이전 날짜만 수정 가능합니다.");
        }
        return crewGroup.findCrewByName(name).updateAttendance(date, time);
    }

    public int calculateAttendanceCount(String name, LocalDate nowDate) {
        Crew crew = findCrewByName(name);
        return crew.calculateAttendanceCount(nowDate);
    }

    public int calculateLatenessCount(String name, LocalDate nowDate) {
        Crew crew = findCrewByName(name);
        return crew.calculateLatenessCount(nowDate);
    }

    public int calculateAbsenceCount(String name, LocalDate nowDate) {
        Crew crew = findCrewByName(name);
        return crew.calculateAbsenceCount(nowDate);
    }

    public Penalty determinePenaltyStatus(String name, LocalDate nowDate) {
        Crew crew = findCrewByName(name);
        return crew.determinePenaltyStatus(nowDate);
    }

    public List<Crew> checkExpulsionRiskCrew(LocalDate nowDate) {
        List<Crew> resultCrew = new ArrayList<>();
        for (Crew crew : crewGroup.getAllCrews()) {
            addCrewIfAtRisk(nowDate, crew, resultCrew);
        }
        return sortCrews(resultCrew, nowDate);
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

    private void addCrewIfAtRisk(LocalDate nowDate, Crew crew, List<Crew> resultCrew) {
        if (determinePenaltyStatus(crew.getName(), nowDate) != Penalty.PASS) {
            resultCrew.add(crew);
        }
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
