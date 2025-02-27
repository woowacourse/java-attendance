package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AttendanceBook {

    private final List<Crew> crews;

    public AttendanceBook(Map<String, List<LocalDateTime>> crewAttendances) {
        this.crews = new ArrayList<>();
        for (String name : crewAttendances.keySet()) {
            List<Attendance> attendances = parseAttendances(crewAttendances, name);
            this.crews.add(new Crew(name, new Attendances(attendances)));
        }
    }

    public void checkAlreadyAttended(Crew crew, AttendanceDate attendanceDate) {
        if (crew.checkAlreadyAttend(attendanceDate)) {
            throw new IllegalArgumentException("[ERROR] 이미 출석했습니다. 수정 기능을 이용해 주세요.");
        }
    }

    public void attend(Crew crew, AttendanceDate attendanceDate, AttendanceTime attendanceTime) {
        crew.attend(attendanceDate, attendanceTime);
    }

    public Crew findCrewByName(String name) {
        return this.crews.stream()
                .filter(crew -> crew.isSameName(name))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 등록되지 않은 닉네임입니다."));
    }

    public void checkAttendanceExist(Crew crew, AttendanceDate attendanceDate) {
        if (!crew.checkAlreadyAttend(attendanceDate)) {
            throw new IllegalArgumentException("[ERROR] 출석 기록이 존재하지 않습니다.");
        }
    }

    public void editAttendance(Crew crew, AttendanceDate attendanceDate, AttendanceTime attendanceTime) {
        crew.edit(attendanceDate, attendanceTime);
    }

    public List<Crew> findRiskOfExpulsionCrew(LocalDate nowDate) {
        return this.crews.stream()
                .filter(crew -> crew.isExpelledStatus(nowDate))
                .toList();
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
}
