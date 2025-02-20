package repository;

import domain.Attendance;
import domain.AttendanceBook;
import domain.Crew;
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
    public AttendanceBook findByCrewName(String crewName) {
        Crew crew = findCrewByName(crewName);
        return crewAttendances.get(crew);
    }

    @Override
    public Optional<Attendance> findByCrewAndDate(String crewName, int date) {
        AttendanceBook attendanceBook = findByCrewName(crewName); // 크루 존재 여부 파악해줌
        return attendanceBook.findAttendanceByDate(date);
//
//        // TODO: 상수화 하기
//        int month = 12;
//
//        return findByCrew(crew).stream()
//                .filter(attendance ->
//                        attendance.getTime().getMonthValue() == month && attendance.getTime().getDayOfMonth() == date
//                )
//                .findFirst();
    }

    @Override
    public void modifyAttendance(String crewName, Attendance beforeAttendance, Attendance afterAttendance) {
        AttendanceBook attendanceBook = findByCrewName(crewName);
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
