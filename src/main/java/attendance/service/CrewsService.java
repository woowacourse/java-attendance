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

    public Crews init(Map<String, List<LocalDateTime>> crewsAttendances, LocalDate now) {
        List<Crew> crews = new ArrayList<>();
        LocalDate firstDay = LocalDate.of(now.getYear(), now.getMonth(), 1);
        for (Map.Entry<String, List<LocalDateTime>> crewAttendances : crewsAttendances.entrySet()) {
            List<Attendance> attendances = initAttendances(now, crewAttendances, firstDay);
            crews.add(new Crew(crewAttendances.getKey(), attendances));
        }
        return new Crews(crews);
    }

    private List<Attendance> initAttendances(LocalDate now, Map.Entry<String, List<LocalDateTime>> crewAttendances, LocalDate firstDay) {
        List<Attendance> attendances = new ArrayList<>();
        for (LocalDate day = firstDay; day.isBefore(now); day = day.plusDays(1L)) {
            // TODO : indent 줄이기
            if (isHoliday(day)) {
                continue;
            }
            attendances.add(createAttendanceByDate(crewAttendances.getValue(), day));
        }
        return attendances;
    }

    private boolean isHoliday(LocalDate day) {
        List<DayOfWeek> weekends = List.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);
        return weekends.contains(day.getDayOfWeek()) || day.getDayOfMonth() == 25;
    }

    private Attendance createAttendanceByDate(List<LocalDateTime> attendances, LocalDate day) {
        return attendances.stream()
                .filter(attendance -> day.equals(LocalDate.from(attendance)))
                .findFirst()
                .map(Attendance::new)
                .orElse(new Attendance(day.atStartOfDay(), AttendanceStatus.ABSENCE));
    }
}
