package domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class AttendanceBook {
    private final Map<String, Crew> crews;

    public AttendanceBook() {
        this.crews = new HashMap<>();
    }

    public void addCrew(String name, LocalDate date, LocalTime time) {
        crews.putIfAbsent(name, new Crew(name, date, time));
    }

    public void attendCrew(String name, LocalDate date, LocalTime time) {
        Crew crew = crews.get(name);
        if (crew == null) {
            throw new IllegalArgumentException("[ERROR] 등록되지 않은 닉네임입니다.");
        }
        if (crew.hasAlreadyAttended(date)) {
            throw new IllegalArgumentException("[ERROR] 이미 출석한 경우 수정 기능을 사용하세요.");
        }
        crew.addAttendance(date, time);
    }

    public boolean containsCrew(String name) {
        return crews.containsKey(name);
    }

    public Crew findCrewByName(String name) {
        return crews.get(name);
    }
}
