package repository;

import domain.Attendance;
import domain.Crew;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AttendanceRepositoryImpl implements AttendanceRepository{
    private final List<Attendance> attendances;

    public AttendanceRepositoryImpl() {
        this.attendances = new ArrayList<>();
    }

    @Override
    public void save(Attendance attendance) {
        attendances.add(attendance);
    }

    @Override
    public List<Attendance> findByCrew(Crew crew) {
        return attendances.stream().filter(attendance -> attendance.getCrew().equals(crew)).toList();
    }

    @Override
    public Optional<Attendance> findByCrewAndDate(Crew crew, int date) {
        // TODO: 상수화 하기
        int month = 12;

        return findByCrew(crew).stream()
                .filter(attendance ->
                        attendance.getTime().getMonthValue() == month && attendance.getTime().getDayOfMonth() == date
                )
                .findFirst();
    }

    @Override
    public void replace(Attendance beforeAttendance, Attendance afterAttendance) {
        try {
            attendances.remove(beforeAttendance);
            save(afterAttendance);
        } catch (NullPointerException e) {
            throw new RuntimeException("존재하지 않은 출석 기록을 삭제할 수 없습니다.");
        }
    }
}
