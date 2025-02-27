package model;

import model.exception.CrewNotExistException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class AttendanceBook {

    private final Map<Crew, AttendanceHistory> attendances;

    public static AttendanceBook from(Crews crews) {
        Map<Crew, AttendanceHistory> attendances = new HashMap<>();
        for (Crew crew : crews.getCrews()) {
            attendances.put(crew, new AttendanceHistory());
        }
        return new AttendanceBook(attendances);
    }

    public AttendanceBook(Map<Crew, AttendanceHistory> attendances) {
        this.attendances = attendances;
    }

    public void update(Map<String, List<LocalDateTime>> updatingAttendances, Crews crews) {
        for (String crewName : updatingAttendances.keySet()) {
            List<LocalDateTime> attendanceTimes = updatingAttendances.get(crewName);
            Crew crew = crews.findCrewByName(crewName).orElseThrow(CrewNotExistException::new); //TODO : Crews를 굳이 가져오는게 불편함
            AttendanceHistory attendanceHistory = this.attendances.get(crew); //TODO : 없으면 예외
            attendanceTimes.forEach(attendanceTime ->
                    attendanceHistory.register(attendanceTime.toLocalDate(), attendanceTime.toLocalTime()));
        }
    }

    public AttendanceHistory findByCrew(Crew targetCrew) {
        return attendances.keySet().stream()
                .filter(crew -> crew.equals(targetCrew))
                .findAny()
                .map(attendances::get)
                .orElseThrow(CrewNotExistException::new);
    }

    public Map<Crew, List<Attendance>> findAllStatisticsUntilBefore(LocalDate limitDate) {
        Map<Crew, List<Attendance>> statistics = new HashMap<>();
        attendances.keySet().forEach(
                crew -> {
                    List<Attendance> slicedAttendances = attendances.get(crew).sliceByDateUntilBefore(limitDate);
                    statistics.put(crew, slicedAttendances);
                });
        return statistics;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        AttendanceBook targetAttendancdBook = (AttendanceBook) object;
        return attendances.entrySet().containsAll(targetAttendancdBook.attendances.entrySet())
                && targetAttendancdBook.attendances.entrySet().containsAll(attendances.entrySet());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(attendances);
    }
}
