package repository;

import domain.Attendance;
import domain.AttendanceBook;
import domain.Crew;
import exception.CrewNotExistException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class AttendanceRepositoryImpl implements AttendanceRepository {
    private final Map<Crew, AttendanceBook> crewAttendances;

    public AttendanceRepositoryImpl() {
        this.crewAttendances = new HashMap<>();
    }

    @Override
    public void save(Crew crew, int year, int month) {
        if (crewAttendances.containsKey(crew)) {
            throw new RuntimeException("이미 출석부가 존재하는 크루입니다.");
        }
        crewAttendances.put(crew, new AttendanceBook(year, month));
    }

    @Override
    public void createNewAttendance(String crewName, LocalDate date, LocalTime time) {
        Crew crew = findCrewByName(crewName);
        AttendanceBook attendanceBook = crewAttendances.get(crew);
        attendanceBook.replace(date, time);
        crewAttendances.replace(crew, attendanceBook);
    }

    @Override
    public AttendanceBook findByCrewName(String crewName) {
        Crew crew = findCrewByName(crewName);
        return crewAttendances.get(crew);
    }

    @Override
    public Attendance findByCrewAndDate(String crewName, LocalDate date) {
        AttendanceBook attendanceBook = findByCrewName(crewName);
        return attendanceBook.findAttendanceByDate(date);
    }

    @Override
    public void modifyAttendance(String crewName, LocalDate date, LocalTime time) {
        AttendanceBook attendanceBook = findByCrewName(crewName);
        attendanceBook.replace(date, time);
    }

    @Override
    public Crew findCrewByName(String crewName) {
        return crewAttendances.keySet().stream()
                .filter(crew -> crew.getName().equals(crewName))
                .findFirst()
                .orElseThrow(CrewNotExistException::new);
    }

    @Override
    public Map<Crew, AttendanceBook> findAll() {
        return Collections.unmodifiableMap(crewAttendances);
    }
}
