package attendance.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CrewAttendances {

    private static final DateTimeFormatter MONTH_DAY_PATTERN = DateTimeFormatter.ofPattern("M월 d일", Locale.KOREA);
    private final Map<Crew, Attendances> crewAttendances;

    public CrewAttendances(final Map<Crew, List<LocalDateTime>> crewAttendanceDateTimes, final LocalDate standardDate) {
        this.crewAttendances = toCrewAttendances(crewAttendanceDateTimes, standardDate);
    }

    private Map<Crew, Attendances> toCrewAttendances(
            final Map<Crew, List<LocalDateTime>> crewAttendanceDateTimes, final LocalDate standardDate
    ) {
        return crewAttendanceDateTimes.keySet().stream()
                .collect(Collectors.toMap(
                        Function.identity(),
                        crew -> createAttendancesWithFillEmptyDay(crewAttendanceDateTimes, crew, standardDate)
                ));
    }

    private Attendances createAttendancesWithFillEmptyDay(
            final Map<Crew, List<LocalDateTime>> crewAttendanceDateTimes, final Crew crew, final LocalDate standardDate
    ) {
        List<LocalDateTime> ascendingAttendanceDateTimes = new ArrayList<>(crewAttendanceDateTimes.get(crew));
        Collections.sort(ascendingAttendanceDateTimes);
        List<Attendance> attendances = new ArrayList<>();
        for (int day = 1; day <= standardDate.getDayOfMonth(); day++) {
            LocalDate targetDate = standardDate.withDayOfMonth(day);
            addAttendanceWithoutWeekendAndHoliday(targetDate, attendances, ascendingAttendanceDateTimes);
        }
        return new Attendances(attendances);
    }

    private void addAttendanceWithoutWeekendAndHoliday(final LocalDate targetDate, final List<Attendance> attendances,
                                                       final List<LocalDateTime> ascendingAttendanceDateTimes
    ) {
        if (Holiday.isWeekend(targetDate) || Holiday.isExistsInPublicHolidays(targetDate)) {
            return;
        }
        attendances.add(createAttendance(ascendingAttendanceDateTimes, targetDate));
    }

    private Attendance createAttendance(final List<LocalDateTime> attendanceDateTimes, final LocalDate targetDate) {
        return attendanceDateTimes.stream()
                .filter(dateTime -> dateTime.toLocalDate().isEqual(targetDate))
                .findAny()
                .map(Attendance::new)
                .orElse(Attendance.absent(targetDate));
    }

    public boolean hasCrewAttendanceRecordByLocalDate(final Crew crew, final LocalDate findDate) {
        validateCrewExistence(crew);
        return crewAttendances.get(crew)
                .hasAttendanceByLocalDate(findDate);
    }

    private void validateCrewExistence(final Crew crew) {
        if (!crewAttendances.containsKey(crew)) {
            throw new IllegalArgumentException("존재하지 않는 크루입니다.");
        }
    }

    public Attendance findCrewAttendanceByLocalDate(final Crew crew, final LocalDate findDate) {
        validateCrewExistence(crew);
        if (!hasCrewAttendanceRecordByLocalDate(crew, findDate)) {
            return Attendance.absent(findDate);
        }
        return crewAttendances.get(crew)
                .findSameDateAttendance(findDate);
    }

    public void modifyCrewAttendanceByModificationDateTime(
            final Crew crew, final LocalDateTime modificationDateTime, final LocalDate today
    ) {
        validateCrewExistence(crew);
        validateIsFutureDate(modificationDateTime.toLocalDate(), today);
        Attendances attendances = crewAttendances.get(crew);
        attendances.modifyByModificationDateTime(modificationDateTime);
    }

    private void validateIsFutureDate(final LocalDate comparisonDate, final LocalDate standardDate) {
        if (comparisonDate.isAfter(standardDate)) {
            throw new IllegalArgumentException(
                    String.join(" ", MONTH_DAY_PATTERN.format(standardDate), "보다 미래의 날짜를 수정할 수 없습니다.")
            );
        }
    }

    public void addAttendance(final Crew crew, final Attendance attendance) {
        validateCrewExistence(crew);
        Attendances attendances = crewAttendances.get(crew);
        attendances.add(attendance);
    }

    public Attendances findAllCrewAttendanceUntilStandardDate(final Crew crew, final LocalDate standardDate) {
        validateCrewExistence(crew);
        Attendances attendances = crewAttendances.get(crew);
        return new Attendances(attendances.findAllUntilStandardDate(standardDate));
    }

    public int calculateAttendanceCount(final Crew crew, final LocalDate standardDate) {
        Attendances attendances = crewAttendances.get(crew);
        return attendances.calculateAttendanceCount(standardDate);
    }

    public int calculateLateCount(final Crew crew, final LocalDate standardDate) {
        Attendances attendances = crewAttendances.get(crew);
        return attendances.calculateLateCount(standardDate);
    }

    public int calculateAbsentCount(final Crew crew, final LocalDate standardDate) {
        Attendances attendances = crewAttendances.get(crew);
        return attendances.calculateAbsentCount(standardDate);
    }

    public List<Crew> findPenaltyCrewsSortedByRisk(final List<Crew> crews, final LocalDate standardDate) {
        return crews.stream()
                .filter(crew -> {
                    Attendances attendances = crewAttendances.get(crew);
                    ExpulsionStatus expulsionStatus = attendances.findExpulsionStatusUntilStandardDate(
                            standardDate);
                    return expulsionStatus.isPenaltyGroup();
                }).sorted(sortByRiskCountAndNickname(standardDate))
                .toList();
    }

    private Comparator<Crew> sortByRiskCountAndNickname(final LocalDate standardDate) {
        return (o1, o2) -> {
            int totalRiskCount = AttendanceStatus.convertToLateCount(calculateAbsentCount(o1, standardDate)) +
                    calculateLateCount(o1, standardDate);
            int otherTotalRiskCount = AttendanceStatus.convertToLateCount(calculateAbsentCount(o2, standardDate)) +
                    calculateLateCount(o2, standardDate);
            if (totalRiskCount != otherTotalRiskCount) {
                return otherTotalRiskCount - totalRiskCount;
            }
            return o1.getNickname().compareTo(o2.getNickname());
        };
    }

    public ExpulsionStatus calculateExpulsionStatus(final Crew crew, final LocalDate standardDate) {
        Attendances attendances = crewAttendances.get(crew);
        return attendances.findExpulsionStatusUntilStandardDate(standardDate);
    }

}
