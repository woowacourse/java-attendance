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
        crews.computeIfAbsent(name, key -> new Crew(name, date, time))
                .addAttendance(date, time);
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
