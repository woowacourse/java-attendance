package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

public class AttendanceSystem {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private final List<Crew> crews;

    public AttendanceSystem(final List<Crew> crews) {
        this.crews = crews;
    }

    public static AttendanceSystem of(final List<String> data, final LocalDate today) {
        final List<Crew> crews = data.stream()
                .map(d -> d.split(",")[0])
                .distinct()
                .map(d -> Crew.of(d, today))
                .toList();
        data.forEach(d -> initAttendance(crews, d));
        return new AttendanceSystem(crews);
    }

    public Attendance attendance(final String name, final LocalDateTime localDateTime) {
        final Crew crew = findCrewByName(name);
        return crew.addAttendance(localDateTime.format(DATE_TIME_FORMATTER));
    }

    public boolean isAlreadyTodayAttendance(final String crewName) {
        final Crew crew = this.findCrewByName(crewName);
        return crew.isAlreadyTodayAttendance(LocalDate.now().withYear(2024).withMonth(12));
    }

    public void validateCrewByName(final String name) {
        if (!existCrewByName(name)) {
            throw new IllegalArgumentException("크루가 존재하지 않습니다.");
        }
    }

    public void validateUpdateAttendanceDay(final String crewName, final int dayOfMonth) {
        if (!isAlreadyTodayAttendanceByCrewName(crewName, convertDayOfMonthToLocalDate(dayOfMonth))) {
            throw new IllegalArgumentException("유효하지 않은 날짜입니다.");
        }
    }

    public List<Crew> calculateExpulsionCrews() {
        return crews.stream()
                .filter(this::isRiskOfExpulsionCrew)
                .sorted()
                .toList();
    }

    public boolean isNotAttendanceDay(final LocalDate localDate) {
        return localDate.getDayOfWeek() == DayOfWeek.SUNDAY || localDate.getDayOfWeek() == DayOfWeek.SATURDAY
                || localDate.equals(LocalDate.of(2024, 12, 25));
    }

    private boolean isRiskOfExpulsionCrew(final Crew crew) {
        final ExpulsionStatus expulsionStatus = crew.calculateExpulsionStatus();
        return !Objects.equals(expulsionStatus, ExpulsionStatus.NORMAL);
    }

    public Attendance updateAttendanceByCrewNameAndDay(final LocalTime targetTime, final String crewName,
                                                       final int dayOfMonth) {
        final LocalDate targetDate = convertDayOfMonthToLocalDate(dayOfMonth);
        return findCrewByName(crewName)
                .updateAttendanceByDateAndTime(targetTime, targetDate);
    }

    public Attendance findAttendanceByDate(final String crewName, final int dayOfMonth) {
        final LocalDate targetDate = convertDayOfMonthToLocalDate(dayOfMonth);
        return findCrewByName(crewName).findAttendanceByDate(targetDate);
    }

    public Crew findCrewByName(final String name) {
        return crews.stream()
                .filter(crew -> crew.isSameName(name))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }

    private LocalDate convertDayOfMonthToLocalDate(final int dayOfMonth) {
        return LocalDate.of(2024, 12, dayOfMonth);
    }

    private boolean existCrewByName(final String name) {
        return crews.stream()
                .anyMatch(crew -> crew.isSameName(name));
    }

    private boolean isAlreadyTodayAttendanceByCrewName(final String name, final LocalDate today) {
        return findCrewByName(name).isAlreadyTodayAttendance(today);
    }

    private static void initAttendance(final List<Crew> crews, final String input) {
        final String[] data = input.split(",");
        crews.stream()
                .filter(crew -> crew.isSameName(data[0]))
                .findAny()
                .ifPresent(crew -> crew.updateAttendanceByDateTime(data[1]));
    }

    public List<Crew> getCrews() {
        return crews;
    }
}
