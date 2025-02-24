package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

public class AttendanceBook {

    private final List<Crew> crews;

    public AttendanceBook(final List<Crew> crews) {
        this.crews = crews;
    }

    public static AttendanceBook of(final List<String> data, final LocalDate today) {
        final List<Crew> crews = data.stream()
                .map(d -> d.split(",")[0])
                .distinct()
                .map(d -> Crew.of(d, today))
                .toList();
        data.forEach(d -> initAttendance(crews, d));
        return new AttendanceBook(crews);
    }

    public Attendance attendance(final String name, final LocalDateTime localDateTime) {
        validateAttendanceDate(localDateTime);
        final Crew crew = findCrewByName(name);
        return crew.addAttendance(localDateTime);
    }

    private void validateAttendanceDate(final LocalDateTime localDateTime) {
        if (localDateTime.getDayOfWeek() == DayOfWeek.SATURDAY || localDateTime.getDayOfWeek() == DayOfWeek.SUNDAY
                || localDateTime.toLocalDate().equals(LocalDate.of(2024, 12, 25))) {
            throw new IllegalArgumentException("주말과 공휴일은 출석할 수 없습니다.");
        }
    }

    public boolean isAlreadyTodayAttendance(final String crewName, final LocalDate localDate) {
        final Crew crew = this.findCrewByName(crewName);
        return crew.existTodayAttendance(localDate);
    }

    public void validateCrewByName(final String name) {
        if (!existCrewByName(name)) {
            throw new IllegalArgumentException("크루가 존재하지 않습니다.");
        }
    }

    public void validateUpdateAttendanceDay(final String crewName, final int dayOfMonth) {
        if (!existTodayAttendanceByCrewName(crewName, LocalDate.of(2024, 12, dayOfMonth))) {
            throw new IllegalArgumentException("유효하지 않은 날짜입니다.");
        }
    }

    public List<Crew> calculateExpulsionCrews() {
        return crews.stream()
                .filter(this::isExpulsionCrew)
                .sorted()
                .toList();
    }

    public boolean isNotAttendanceDay(final LocalDate localDate) {
        return localDate.getDayOfWeek() == DayOfWeek.SUNDAY || localDate.getDayOfWeek() == DayOfWeek.SATURDAY
                || localDate.equals(LocalDate.of(2024, 12, 25));
    }


    private boolean isExpulsionCrew(final Crew crew) {
        final ExpulsionStatus expulsionStatus = crew.calculateExpulsionStatus();
        return !Objects.equals(expulsionStatus, ExpulsionStatus.NORMAL);
    }


    public Attendance updateAttendanceByCrewNameAndDay(final LocalTime targetTime, final String crewName,
                                                       final int dayOfMonth) {
        final LocalDate localDate = LocalDate.of(2024, 12, dayOfMonth);
        validateAttendanceDate(localDate);
        final Attendance beforeAttendance = findAttendanceByDate(crewName, localDate);
        updateAttendance(targetTime, crewName, localDate);
        return beforeAttendance;
    }

    private void updateAttendance(final LocalTime targetTime, final String crewName, final LocalDate localDate) {
        final Crew crew = findCrewByName(crewName);
        crew.updateAttendanceByDateAndTime(targetTime, localDate);
    }

    private void validateAttendanceDate(final LocalDate localDate) {
        if (Holiday.isHoliday(localDate)) {
            throw new IllegalArgumentException("주말 또는 휴일은 등교일이 아닙니다.");
        }
    }

    private Attendance findAttendanceByDate(final String crewName, final LocalDate localDate) {
        final Crew crew = findCrewByName(crewName);
        return crew.findAttendanceByDate(localDate);
    }

    private boolean existCrewByName(final String name) {
        return crews.stream().anyMatch(crew -> crew.isSameName(name));
    }

    private boolean existTodayAttendanceByCrewName(final String name, final LocalDate today) {
        final Crew crew = findCrewByName(name);
        return crew.existTodayAttendance(today);
    }

    private static void initAttendance(final List<Crew> crews, final String input) {
        final String[] data = input.split(",");
        crews.stream()
                .filter(crew -> crew.isSameName(data[0]))
                .findAny()
                .ifPresent(crew -> crew.updateAttendanceByDateTime(data[1]));
    }

    public Crew findCrewByName(final String name) {
        return crews.stream()
                .filter(crew -> crew.isSameName(name))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }

    public List<Crew> getCrews() {
        return crews;
    }
}
