package attendance.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class Attendances {

    private final CrewGroup crewGroup;
    private final List<Attendance> attendances;

    public Attendances(CrewGroup crewGroup, List<Attendance> attendances) {
        this.crewGroup = crewGroup;
        this.attendances = new ArrayList<>(attendances);
    }

    public void validateExistNickname(String nickname) {
        boolean isNotExistsCrew = !crewGroup.contains(nickname);
        if (isNotExistsCrew) {
            throw new IllegalArgumentException("등록되지 않은 닉네임입니다.");
        }
    }

    public void add(Attendance attendance) {
        if (isAlreadyAttendance(attendance)) {
            throw new IllegalArgumentException("크루는 같은 날에 또 출석할 수 없습니다.");
        }
        attendances.add(attendance);
    }

    public Attendance update(Attendance newAttendance) {
        for (Attendance attendance : attendances) {
            if (attendance.isAlreadyAttendance(newAttendance)) {
                attendances.remove(attendance);
                break;
            }
        }
        attendances.add(newAttendance);
        return newAttendance;
    }

    public Optional<Attendance> findByCrewAndDate(Crew crew, LocalDate date) {
        return attendances.stream()
                .filter(attendance -> attendance.isAlreadyAttendance(
                        new Attendance(crew, LocalDateTime.of(date, LocalTime.MIN))))
                .findFirst();
    }

    public List<Attendance> findAllByCrewAndMonth(Crew crew, Month findMonth) {
        validateExistCrew(crew);
        return attendances.stream()
                .filter(attendance -> attendance.isCrewAttendanceInMonth(crew, findMonth))
                .toList();
    }

    public Map<Crew, List<Attendance>> findAllByMonth(Month findMonth) {
        return crewGroup.getCrews().stream()
                .collect(Collectors.toMap(
                        crew -> crew,
                        crew -> findAllByCrewAndMonth(crew, findMonth)
                ));
    }

    private boolean isAlreadyAttendance(Attendance attendanceToCheck) {
        return attendances.stream()
                .anyMatch(attendance -> attendance.isAlreadyAttendance(attendanceToCheck));
    }

    private void validateExistCrew(Crew crew) {
        if (!crewGroup.contains(crew)) {
            throw new IllegalArgumentException("등록되지 않은 크루입니다.");
        }
    }
}
