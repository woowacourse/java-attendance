package repository;

import domain.Attendance;
import domain.Crew;

import java.util.List;
import java.util.Optional;

public class FakeAttendanceRepository implements AttendanceRepository{
    private final AttendanceRepository originalAttendanceRepository = new AttendanceRepositoryImpl();

    @Override
    public void save(Attendance attendance) {
        originalAttendanceRepository.save(attendance);
    }

    @Override
    public List<Attendance> findByCrew(Crew crew) {
        return originalAttendanceRepository.findByCrew(crew);
    }

    @Override
    public Optional<Attendance> findByCrewAndDate(Crew crew, int date) {
        return originalAttendanceRepository.findByCrewAndDate(crew, date);
    }

    @Override
    public void replace(Attendance beforeAttendance, Attendance afterAttendance) {
        originalAttendanceRepository.replace(beforeAttendance, afterAttendance);
    }
}
