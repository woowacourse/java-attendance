package domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttendanceBook {
    private final Map<String, Crew> crews;

    public AttendanceBook() {
        this.crews = new HashMap<>();
    }

    public void addCrew(String name, LocalDate date, LocalTime time) {
        Crew crew = crews.get(name);
        if (crew == null) {
            crews.put(name, new Crew(name, date, time));
            return;
        }
        crew.addAttendance(date, time);
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
        return findCrewByName(name).updateAttendance(date, time);
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
        for (String name : crews.keySet()) {
            Penalty penalty = determinePenaltyStatus(name, nowDate);
            if (penalty != Penalty.PASS) {
                resultCrew.add(crews.get(name));
            }
        }
        return sortCrews(resultCrew, nowDate);
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

    public Attendance findAttendance(String name, LocalDate date) {
        return findCrewByName(name).findAttendanceByDate(date);
    }

    public boolean containsCrew(String name) {
        return crews.containsKey(name);
    }

    public Crew findCrewByName(String name) {
        if (!crews.containsKey(name)) {
            throw new IllegalArgumentException("[ERROR] 등록되지 않은 닉네임입니다.");
        }
        return crews.get(name);
    }
}
