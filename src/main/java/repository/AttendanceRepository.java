package repository;

import domain.Attendance;
import domain.AttendanceBook;
import domain.Crew;

import java.util.List;
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
