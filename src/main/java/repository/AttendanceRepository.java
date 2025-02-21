package repository;

import domain.attendance.Attendance;
import domain.attendance.AttendanceBook;
import domain.crew.Crew;

import java.util.Map;
import java.util.Optional;

public interface AttendanceRepository {
    void save(Crew crew);

    void createNewAttendance(String crewName, int date, int hour, int minute);

    AttendanceBook findByCrewName(String crewName);

    Optional<Attendance> findByCrewAndDate(String crewName, int date);

    void modifyAttendance(String crewName, Attendance beforeAttendance, Attendance afterAttendance);

    Crew findCrewByName(String crewName);

    Map<Crew, AttendanceBook> findAll();
}
