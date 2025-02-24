package attendance.model;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Attendances {

    private final CrewGroup crewGroup;
    private final List<Attendance> attendances;

    public Attendances(CrewGroup crewGroup, List<Attendance> attendances) {
        this.crewGroup = crewGroup;
        this.attendances = new ArrayList<>(attendances);
    }

    public void attend(Attendance attendance) {
        if (isAlreadyAttendance(attendance)) {
            throw new IllegalArgumentException("크루는 같은 날에 또 출석할 수 없습니다.");
        }
        attendances.add(attendance);
    }

    public Attendance update(LocalDate today, Attendance newAttendance) {
        if (newAttendance.isAfter(today)) {
            throw new IllegalArgumentException("미래날짜의 출석을 수정할 수 없습니다.");
        }
        for (Attendance attendance : attendances) {
            if (attendance.isAlreadyAttendance(newAttendance)) {
                attendances.remove(attendance);
                break;
            }
        }
        attendances.add(newAttendance);
        return newAttendance;
    }

    public Attendance findByCrewAndDate(Crew crew, LocalDate date) {
        return attendances.stream()
                .filter(attendance -> attendance.isAlreadyAttendance(crew, date))
                .findFirst()
                .orElse(Attendance.absent(crew, date));
    }

    public MonthlyAttendance findMonthlyAttendance(Crew crew, Month findMonth) {
        validateExistCrew(crew);
        List<Attendance> monthlyAttendance = attendances.stream()
                .filter(attendance -> attendance.isCrewAttendanceInMonth(crew, findMonth))
                .toList();
        return new MonthlyAttendance(findMonth, crew, monthlyAttendance);
    }

    public List<AttendanceResult> findAllCrewAttendanceResultUntilDate(LocalDate endDate) {
        return crewGroup.getCrews().stream()
                .map(crew -> findMonthlyAttendance(crew, endDate.getMonth()))
                .map(monthlyAttendance -> monthlyAttendance.calculateAttendanceResultUntilDate(endDate))
                .collect(Collectors.toList());
    }

    public Crew findCrewByNickname(String nickname) {
        return crewGroup.findCrewByNickname(nickname);
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
