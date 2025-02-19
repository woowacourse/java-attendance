package repository;

import domain.Attendance;
import domain.Crew;

import java.util.List;
import java.util.Optional;

public interface AttendanceRepository {
    void save(Attendance attendance);

    List<Attendance> findByCrew(Crew crew);

    Optional<Attendance> findByCrewAndDate(Crew crew, int date);

    void replace(Attendance beforeAttendance, Attendance afterAttendance);
}
