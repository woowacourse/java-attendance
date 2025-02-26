package attendance.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CrewAttendances {

    private static final DateTimeFormatter MONTH_DAY_PATTERN = DateTimeFormatter.ofPattern("M월 d일", Locale.KOREA);
    private final Map<Crew, Attendances> crewAttendances;

    public CrewAttendances(final Map<Crew, List<LocalDateTime>> crewAttendanceDateTimes) {
        this.crewAttendances = toCrewAttendances(crewAttendanceDateTimes);
    }

    private Map<Crew, Attendances> toCrewAttendances(final Map<Crew, List<LocalDateTime>> crewAttendanceDateTimes) {
        return crewAttendanceDateTimes.keySet().stream()
                .collect(Collectors.toMap(
                        Function.identity(),
                        crew -> new Attendances(crewAttendanceDateTimes.get(crew)
                                .stream()
                                .map(Attendance::new)
                                .collect(Collectors.toList()))
                ));
    }

    public boolean hasCrewAttendanceByLocalDate(final Crew crew, final LocalDate findDate) {
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

}
