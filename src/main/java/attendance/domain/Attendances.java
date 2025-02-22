package attendance.domain;

import attendance.util.DateUtil;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Attendances {

    private Map<Crew, List<Attendance>> attendances = new HashMap<>();

    public void addAttendance(Crew crew, Attendance attendance) {
        if (!attendances.containsKey(crew)) {
            attendances.put(crew, new ArrayList<>(List.of(attendance)));
            return;
        }

        for (Attendance existAttendance : attendances.get(crew)) {
            validateAlreadyAttended(attendance, existAttendance);
        }

        attendances.get(crew).add(attendance);
    }

    private void validateAlreadyAttended(Attendance attendance, Attendance existAttendance) {
        if (existAttendance.getAttendedTime().getDayOfMonth() == attendance.getAttendedTime().getDayOfMonth()) {
            throw new IllegalArgumentException("\n[ERROR] 이미 출석을 완료했습니다. 수정 기능을 이용해주세요.");
        }
    }

    public Attendance getAttendance(Crew targetCrew, LocalDate targetDate) {
        return attendances.getOrDefault(targetCrew, new ArrayList<>())
            .stream()
            .filter(attendance -> attendance.getAttendedTime().getDayOfMonth() == targetDate.getDayOfMonth())
            .findAny()
            .orElse(Attendance.ofAbsence(targetDate));
    }

    public List<Attendance> getAttendances(Crew targetCrew, LocalDate untilDate) {
        List<Attendance> attendancesOfCrew = attendances.getOrDefault(targetCrew, new ArrayList<>());
        List<Attendance> absencesOfCrew = generateAbsences(attendancesOfCrew, untilDate);

        return Stream.of(attendancesOfCrew, absencesOfCrew)
            .flatMap(List::stream)
            .sorted(Comparator.comparing(Attendance::getAttendedTime))
            .collect(Collectors.toList());
    }

    private List<Attendance> generateAbsences(List<Attendance> attendances, LocalDate untilDate) {
        List<Integer> attendedDays = attendances.stream()
            .map(Attendance::getAttendedTime)
            .map(LocalDateTime::getDayOfMonth)
            .toList();

        return IntStream.range(1, untilDate.getDayOfMonth())
            .boxed()
            .filter(Predicate.not(attendedDays::contains))
            .map(day -> LocalDate.of(untilDate.getYear(), untilDate.getMonth(), day))
            .filter(DateUtil::isWeekDay)
            .map(Attendance::ofAbsence)
            .toList();
    }

    public int countAttendanceStatus(Crew crew, LocalDate date, AttendanceStatus status) {
        List<Attendance> attendancesOfCrew = getAttendances(crew, date);
        int absenceCount = 0;
        for (Attendance attendance : attendancesOfCrew) {
            if (attendance.getStatus().equals(status)) {
                absenceCount++;
            }
        }
        return absenceCount;
    }
}
