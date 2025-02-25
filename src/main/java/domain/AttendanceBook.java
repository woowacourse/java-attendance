package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AttendanceBook {

    private final List<Crew> crews;

    // TODO: 변환 책임에 대해 고민해보기
    public AttendanceBook(Map<String, List<LocalDateTime>> crewAttendances) {
        this.crews = new ArrayList<>();
        for (String name : crewAttendances.keySet()) {
            List<Attendance> attendances = parseAttendances(crewAttendances, name);
            this.crews.add(new Crew(name, new Attendances(attendances)));
        }
    }

    private List<Attendance> parseAttendances(Map<String, List<LocalDateTime>> crewAttendances, String name) {
        List<Attendance> attendances = new ArrayList<>();
        for (LocalDateTime attendanceDateTime : crewAttendances.get(name)) {
            LocalDate date = attendanceDateTime.toLocalDate();
            LocalTime time = attendanceDateTime.toLocalTime();
            Attendance attendance = new Attendance(new AttendanceDate(date), new AttendanceTime(time));

            attendances.add(attendance);
        }
        return attendances;
    }

    public void checkAlreadyAttended(String name, AttendanceDate attendanceDate) {
        Crew crew = findCrewByName(name);
        crew.checkAlreadyAttend(attendanceDate);
    }

    // AttendanceDate로 파싱한 걸 받아올지? 여기서 파싱할지?
    public void attend(String name, AttendanceDate attendanceDate, AttendanceTime attendanceTime) {
        Crew crew = findCrewByName(name);
        crew.attend(attendanceDate, attendanceTime);
    }

    public void checkExistCrew(String name) {
        crews.stream()
            .filter(crew -> crew.isSameName(name))
            .findAny()
            .orElseThrow(() -> new IllegalArgumentException("[ERROR] 등록되지 않은 닉네임입니다."));
    }

    public Crew findCrewByName(String name) {
        return crews.stream()
                .filter(crew -> crew.isSameName(name))
                .findAny()
                .orElseThrow();
    }
}
