package attendance;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class Crews {

    private final List<Crew> crews;

    public Crews() {
        this.crews = new ArrayList<>();
    }

    public Crew create(final String nickname) {
        crews.stream().filter(crew -> crew.isEqualCrew(nickname))
                .findAny()
                .ifPresent(crew -> {
                    throw new IllegalArgumentException("이미 존재하는 크루입니다.");
                });

        Crew crew = new Crew(nickname);
        crews.add(crew);
        return crew;
    }

    public List<Attendance> findCrewAttendanceByNickname(final String nickname) {
        return findCrewByNickname(nickname).getAttendances();
    }

    public Crew findCrewByNickname(final String nickname) {
        return crews.stream()
                .filter(crew -> crew.isEqualCrew(nickname))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("등록되지 않은 닉네임입니다."));
    }

    public List<Attendance> getCrewAttendancesUtilYesterday(final LocalDate today, final String nickname) {
        List<Attendance> crewAttendances = findCrewAttendanceByNickname(nickname);
        List<Attendance> attendancesUtilYesterday = new ArrayList<>();

        LocalDate date = today.withDayOfMonth(1);
        while (date.isBefore(today)) {
            if (!Holiday.checkHoliday(date.atStartOfDay())) {
                attendancesUtilYesterday.add(findAttendanceForDate(crewAttendances, date));
            }
            date = date.plusDays(1);
        }
        return attendancesUtilYesterday;
    }

    private Attendance findAttendanceForDate(final List<Attendance> crewAttendances, final LocalDate date) {
        return crewAttendances.stream()
                .filter(crewAttendance -> crewAttendance.isEqualDate(date))
                .findFirst()
                .orElseGet(() -> new Attendance(LocalDateTime.of(date, LocalTime.MIN)));
    }

    public Map<AbsenceRule, List<Crew>> findWarningExpulsionCrews() {
        Map<AbsenceRule, List<Crew>> warningExpulsionCrews = new EnumMap<>(AbsenceRule.class);

        for (Crew crew : crews) {
            warningExpulsionCrews
                    .computeIfAbsent(crew.checkAbsenceRule(), k -> new ArrayList<>())
                    .add(crew);
        }

        warningExpulsionCrews.forEach((key, value) -> value.sort(Crew::compareTo));
        return warningExpulsionCrews;
    }
}
