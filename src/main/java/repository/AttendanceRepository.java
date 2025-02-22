package repository;

import domain.attendance.Attendance;
import domain.attendance.AttendanceBook;
import domain.crew.Crew;

import java.util.Map;
import java.util.Optional;

public interface AttendanceRepository {
    void save(Crew crew);

    void createNewAttendance(String crewName, int date, int hour, int minute);

    AttendanceBook findByCrew(Crew crew);

    Optional<Attendance> findByCrewAndDate(Crew crew, int date);

    void modifyAttendance(Crew crew, Attendance beforeAttendance, Attendance afterAttendance);

    Crew findCrewByName(String crewName);

    Map<Crew, AttendanceBook> findAll();
}
