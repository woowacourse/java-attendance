package domain;

import exception.CrewNotExistException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class CrewAttendances {
    private final Map<Crew, AttendanceBook> crewAttendances;

    public CrewAttendances() {
        this.crewAttendances = new HashMap<>();
    }

    public void registerCrew(Crew crew) {
        if (crewAttendances.containsKey(crew)) {
            throw new RuntimeException("이미 출석부가 존재하는 크루입니다.");
        }
        crewAttendances.put(crew, new AttendanceBook());
    }

    public Attendance createNewAttendance(String crewName, LocalDate date, LocalTime time) {
        Crew crew = findCrewByName(crewName);
        AttendanceBook attendanceBook = crewAttendances.get(crew);
        Attendance attendance = attendanceBook.register(date, time);
        crewAttendances.replace(crew, attendanceBook);
        return attendance;
    }

    public AttendanceBook findAttendanceBookByCrewName(String crewName) {
        Crew crew = findCrewByName(crewName);
        return crewAttendances.get(crew);
    }

    public Attendance findAttendanceByCrewAndDate(String crewName, LocalDate date) {
        AttendanceBook attendanceBook = findAttendanceBookByCrewName(crewName);
        return attendanceBook.findAttendanceByDate(date);
    }

    public Attendance modifyAttendance(String crewName, LocalDate date, LocalTime time) {
        AttendanceBook attendanceBook = findAttendanceBookByCrewName(crewName);
        return attendanceBook.replace(date, time);
    }

    public Crew findCrewByName(String crewName) {
        return crewAttendances.keySet().stream()
                .filter(crew -> crew.getName().equals(crewName))
                .findFirst()
                .orElseThrow(CrewNotExistException::new);
    }

    public Map<Crew, AttendanceBook> findAll() {
        return Collections.unmodifiableMap(crewAttendances);
    }

    public Map<LocalDate, Attendance> getAttendances(String name, LocalDate startDate, LocalDate endDate) {
        AttendanceBook attendanceBook = findAttendanceBookByCrewName(name);
        List<Attendance> attendances = attendanceBook.getAllAttendances(startDate, endDate);

        Map<LocalDate, Attendance> result = new HashMap<>();
        for (Attendance attendance : attendances) {
            result.put(attendance.getDate(), attendance);
        }
        return result;
    }
}
