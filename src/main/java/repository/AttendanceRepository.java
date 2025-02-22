package repository;

import domain.Attendance;
import domain.AttendanceBook;
import domain.Crew;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

public interface AttendanceRepository {
    void save(Crew crew, int year, int month);

    void createNewAttendance(String crewName, LocalDate date, LocalTime time);

    AttendanceBook findByCrewName(String crewName);

    Attendance findByCrewAndDate(String crewName, LocalDate date);

    void modifyAttendance(String crewName, LocalDate date, LocalTime time);

    Crew findCrewByName(String crewName);

    Map<Crew, AttendanceBook> findAll();
}
