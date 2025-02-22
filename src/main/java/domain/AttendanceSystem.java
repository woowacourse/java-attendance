package domain;

import domain.constants.ErrorMessage;
import domain.constants.ExpulsionStatus;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class AttendanceSystem {
    private static final LocalDate CHRISTMAS_DAY = LocalDate.of(2024, 12, 25);
    private static final String DELIMITER = ",";
    private final List<Crew> crews;
    private final DateTimeGenerator dateTimeGenerator;

    public AttendanceSystem(final List<Crew> crews, final DateTimeGenerator dateTimeGenerator) {
        this.crews = new ArrayList<>(crews);
        this.dateTimeGenerator = dateTimeGenerator;
    }

    public static AttendanceSystem of(final List<String> data, final DateTimeGenerator dateTimeGenerator) {
        final LocalDate today = dateTimeGenerator.generateDate();
        final List<Crew> crews = data.stream()
                .map(d -> d.split(DELIMITER)[0])
                .distinct()
                .map(d -> Crew.of(d, today))
                .toList();
        data.forEach(d -> initAttendance(crews, d));
        return new AttendanceSystem(crews, dateTimeGenerator);
    }

    private static void initAttendance(final List<Crew> crews, final String input) {
        final String[] data = input.split(DELIMITER);
        crews.stream()
                .filter(crew -> crew.isSameName(data[0]))
                .findAny()
                .ifPresent(crew -> crew.updateAttendanceByDateTime(data[1]));
    }

    public Attendance attendance(final String name, final LocalTime time) {
        final Crew crew = findCrewByName(name);
        final LocalDateTime dateTime = LocalDateTime.of(dateTimeGenerator.generateDate(), time);
        return crew.addAttendance(dateTime);
    }

    public boolean isAlreadyTodayAttendance(final String crewName) {
        final Crew crew = this.findCrewByName(crewName);
        return crew.isAlreadyTodayAttendance(dateTimeGenerator.generateDate());
    }

    public void validateCrewByName(final String name) {
        if (!existCrewByName(name)) {
            throw new IllegalArgumentException(ErrorMessage.CREW_NOT_FOUND.getMessage());
        }
    }

    public void validateUpdateAttendanceDay(final String crewName, final int dayOfMonth) {
        if (!isAlreadyTodayAttendanceByCrewName(crewName, convertDayOfMonthToLocalDate(dayOfMonth))) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_DATE.getMessage());
        }
    }

    public List<Crew> calculateRiskOfExpulsionCrews() {
        return crews.stream()
                .filter(this::isRiskOfExpulsionCrew)
                .sorted()
                .toList();
    }

    public boolean isNotAttendanceDay() {
        final LocalDate today = dateTimeGenerator.generateDate();
        return today.getDayOfWeek() == DayOfWeek.SUNDAY || today.getDayOfWeek() == DayOfWeek.SATURDAY
                || today.equals(CHRISTMAS_DAY);
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

    public ExpulsionStatus calculateExpulsionStatusByCrew(final String crewName) {
        return findCrewByName(crewName).calculateExpulsionStatus();
    }

    public Map<AttendanceStatus, Integer> calculateAttendanceStatisticsByCrew(final String crewName) {
        return findCrewByName(crewName).calculateAttendanceStatistics();
    }

    private Crew findCrewByName(final String name) {
        return crews.stream()
                .filter(crew -> crew.isSameName(name))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.CREW_NOT_FOUND.getMessage()));
    }

    private boolean isRiskOfExpulsionCrew(final Crew crew) {
        final ExpulsionStatus expulsionStatus = crew.calculateExpulsionStatus();
        return !Objects.equals(expulsionStatus, ExpulsionStatus.NORMAL);
    }

    private LocalDate convertDayOfMonthToLocalDate(final int dayOfMonth) {
        return dateTimeGenerator.generateDate()
                .withDayOfMonth(dayOfMonth);
    }

    private boolean existCrewByName(final String name) {
        return crews.stream()
                .anyMatch(crew -> crew.isSameName(name));
    }

    private boolean isAlreadyTodayAttendanceByCrewName(final String name, final LocalDate today) {
        return findCrewByName(name).isAlreadyTodayAttendance(today);
    }

    public List<Attendance> getAttendancesByCrew(final String crewName) {
        return findCrewByName(crewName).getAttendances();
    }

    public List<Crew> getCrews() {
        return new ArrayList<>(crews);
    }

    public LocalDate today() {
        return dateTimeGenerator.generateDate();
    }
}
