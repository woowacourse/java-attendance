package attendance.service;

import attendance.domain.Attendance;
import attendance.domain.AttendanceStatus;
import attendance.domain.Crew;
import attendance.domain.Crews;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CrewsService {
    private static final LocalDate CHRISTMAS = LocalDate.of(2024, 12, 25);
    private static final List<DayOfWeek> WEEKENDS = List.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);

    public Crews init(Map<String, List<LocalDateTime>> crewsAttendances, LocalDate now) {
        List<Crew> crews = new ArrayList<>();
        LocalDate firstDay = LocalDate.of(now.getYear(), now.getMonth(), 1);
        for (Map.Entry<String, List<LocalDateTime>> crewAttendances : crewsAttendances.entrySet()) {
            List<Attendance> attendances = initAttendances(now, crewAttendances.getValue(), firstDay);
            crews.add(new Crew(crewAttendances.getKey(), attendances));
        }
        return new Crews(crews);
    }

    private List<Attendance> initAttendances(LocalDate now, List<LocalDateTime> crewAttendances, LocalDate firstDay) {
        List<Attendance> attendances = new ArrayList<>();
        for (LocalDate day = firstDay; day.isBefore(now); day = day.plusDays(1L)) {
            if (isHoliday(day)) {
                continue;
            }
            attendances.add(createAttendanceByDate(crewAttendances, day));
        }
        return attendances;
    }

    private Attendance createAttendanceByDate(List<LocalDateTime> attendances, LocalDate day) {
        return attendances.stream()
                .filter(attendance -> day.equals(LocalDate.from(attendance)))
                .findFirst()
                .map(Attendance::new)
                .orElse(new Attendance(day.atStartOfDay(), AttendanceStatus.ABSENCE));
    }

    private boolean isHoliday(LocalDate day) {
        return WEEKENDS.contains(day.getDayOfWeek()) || day.equals(CHRISTMAS);
    }
}
