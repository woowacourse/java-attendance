package repository;

import domain.attendance.Attendance;
import domain.attendance.AttendanceBook;
import domain.crew.Crew;
import exception.CrewNotExistException;

import java.util.*;

public class AttendanceRepositoryImpl implements AttendanceRepository{
    private final Map<Crew, AttendanceBook> crewAttendances;

    public AttendanceRepositoryImpl() {
        this.crewAttendances = new HashMap<>();
    }

    @Override
    public void save(Crew crew) {
        if (crewAttendances.containsKey(crew)) {
            throw new RuntimeException("이미 출석부가 존재하는 크루입니다.");
        }
        crewAttendances.put(crew, new AttendanceBook());
    }

    @Override
    public void createNewAttendance(String crewName, int date, int hour, int minute) {
        Crew crew = findCrewByName(crewName);
        AttendanceBook attendanceBook = crewAttendances.get(crew);
        attendanceBook.create(date, hour, minute);
    }

    @Override
    public AttendanceBook findByCrew(Crew crew) {
        //TODO : 여기서 크루가 없다면, 이상한 상황인거임 (사용자 잘못 x)
        if (!crewAttendances.containsKey(crew)) {
            throw new RuntimeException("크루가 존재하지 않습니다.");
        }
        return crewAttendances.get(crew);
    }

    @Override
    public Optional<Attendance> findByCrewAndDate(Crew crew, int date) {
        AttendanceBook attendanceBook = findByCrew(crew);
        return attendanceBook.findAttendanceByDate(date);
    }

    @Override
    public void modifyAttendance(Crew crew, Attendance beforeAttendance, Attendance afterAttendance) {
        AttendanceBook attendanceBook = findByCrew(crew);
        attendanceBook.replace(beforeAttendance, afterAttendance);
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
