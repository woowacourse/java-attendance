package attendance.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Crews {
    private final Set<Crew> crews;

    public Crews() {
        this.crews = new HashSet<>();
    }

    public void initCrews(final List<List<String>> csvData) {
        csvData.stream()
                .map(List::getFirst)
                .distinct()
                .forEach(uniqueCrewName -> crews.add(new Crew(uniqueCrewName))); // Crew 객체 추가
    }

    public void initCrewsAttendance(final List<List<String>> csvData) {
        crews.forEach(crew -> crew.initCrewAttendances(csvData));
    }

    public boolean contains(final Crew crew) {
        return crews.contains(crew);
    }

    public Crew findCrew(final String name) {
        return crews.stream()
                .filter(crew -> crew.isSameName(name))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("입력하신 크루가 존재하지 않습니다."));
    }

    public void attendToday(final Crew crew, final LocalTime attendTime) {
        crews.stream()
                .filter(findCrew -> findCrew.equals(crew))
                .findFirst()
                .ifPresent(findCrew -> findCrew.attendToday(attendTime));
    }

    public Attendance findTodayAttendance(final Crew crew) {
        return crews.stream()
                .filter(findCrew -> findCrew.equals(crew))
                .findFirst()
                .map(findCrew -> findCrew.findAttendance(LocalDate.now()))
                .orElseThrow(() -> new IllegalStateException("출석 체크가 안됐습니다."));
    }

    public boolean hasTodayAttendance(final Crew crew) {
        return crews.stream()
                .filter(findCrew -> findCrew.equals(crew))
                .findFirst()
                .map(Crew::isAttendToday)
                .get();
    }

    public LocalTime findCrewAttendanceTime(final Crew crew, final LocalDate modifyDate) {
        return crews.stream()
                .filter(findCrew -> findCrew.equals(crew))
                .findFirst()
                .map(Crew::getAttendanceHistory)
                .flatMap(attendanceHistory ->
                        attendanceHistory.stream()
                                .filter(attendance -> attendance.isSameDate(modifyDate))
                                .map(Attendance::getTime)
                                .findFirst()
                )
                .orElseThrow(() -> new IllegalStateException("출석 조회를 실패했습니다."));
    }

    public Attendance findCrewAttendance(final Crew crew, final LocalDate modifyDate) {
        return crews.stream()
                .filter(findCrew -> findCrew.equals(crew))
                .findFirst()
                .map(Crew::getAttendanceHistory)
                .map(attendanceHistory -> attendanceHistory.stream()
                        .filter(attendance -> attendance.isSameDate(modifyDate))
                        .findFirst()
                )
                .get()
                .map(Attendance::createSameAttendance)
                .orElseThrow(() -> new IllegalStateException("해당하는 날짜의 출석 기록을 가져올 수 없습니다."));
    }

    public void modifyAttendance(final Crew crew, final LocalDate modifyDate, final LocalTime modifyTime) {
        crews.stream()
                .filter(modifyCrew -> modifyCrew.equals(crew))
                .findFirst()
                .ifPresent(modifyCrew -> modifyCrew.modifyAttendance(LocalDateTime.of(modifyDate, modifyTime)));
    }

    public List<Crew> calculateExpelCrew() {
        return crews.stream()
                .filter(crew -> !crew.getStatus().equals(Status.NONE))
                .sorted(Comparator.comparingInt(Crew::getPenaltyCount).reversed().thenComparing(Crew::getName))
                .toList();
    }
}
