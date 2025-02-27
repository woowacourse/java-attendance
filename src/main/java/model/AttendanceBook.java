package model;

import exception.CrewNotExistException;
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

    public List<AttendanceStatistics> findAllStatistics() {
        return attendances.keySet().stream()
                .map(crew -> new AttendanceStatistics(attendances.get(crew)))
                .toList();
    }

//    public static Map<Crew, AttendanceHistory> initializeAttendanceOf(Crews crews) {
//        Map<Crew, AttendanceHistory> attendances = new HashMap<>();
//        for (Crew crew : crews.getCrews()) {
//            List<Attendance> defaultAttendances = IntStream.range(1, 32)
//                    .mapToObj(date -> new Attendance(
//                            LocalDate.of(2024, 12, date),
//                            Common.noneAttendanceTime))
//                    .collect(Collectors.toList()); //TODO : toList면 불변이 되어 수정 불가능해짐
//            attendances.put(crew, new AttendanceHistory(defaultAttendances));
//        }
//        return attendances;
//    }


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
