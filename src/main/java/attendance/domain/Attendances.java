package attendance.domain;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

import attendance.util.DateUtil;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Attendances {

    private final Map<Crew, List<Attendance>> attendances = new HashMap<>();

    public void addAttendance(Crew crew, Attendance attendance) {
        attendances.putIfAbsent(crew, new ArrayList<>());
        LocalDate date = attendance.getAttendedTime().toLocalDate();

        if (alreadyAttendedAt(date, crew)) {
            throw new IllegalArgumentException("\n[ERROR] 이미 출석을 완료했습니다. 수정 기능을 이용해주세요.");
        }

        attendances.get(crew).add(attendance);
    }

    private boolean alreadyAttendedAt(LocalDate dateToAttend, Crew crew) {
        return attendances.get(crew).stream()
            .map(Attendance::getAttendedTime)
            .map(LocalDateTime::toLocalDate)
            .anyMatch(dateToAttend::equals);
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
            .collect(toList());
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
            .filter(DateUtil::isWeekday)
            .map(Attendance::ofAbsence)
            .toList();
    }

    public void modifyAttendance(Crew crew, LocalDate toBeModified, LocalTime toModify) {
        Attendance before = getAttendance(crew, toBeModified);
        Attendance after = Attendance.of(LocalDateTime.of(toBeModified, toModify));

        List<Attendance> attendancesOfCrew = attendances.get(crew);
        attendancesOfCrew.remove(before);

        attendances.putIfAbsent(crew, new ArrayList<>());
        attendances.get(crew).add(after);
    }

    public Map<AttendanceStatus, Integer> countAttendanceStatus(Crew crew, LocalDate untilDate) {
        List<Attendance> attendancesOfCrew = getAttendances(crew, untilDate);
        return attendancesOfCrew.stream()
            .collect(
                groupingBy(Attendance::getStatus,
                collectingAndThen(counting(),Long::intValue))
            );
    }
}
