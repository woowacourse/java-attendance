package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AttendanceSystem {

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
        return crew.addAttendance(localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
    }

    public void validateCrewByName(final String name) {
        if (!existCrewByName(name)) {
            throw new IllegalArgumentException("크루가 존재하지 않습니다.");
        }
    }

    private boolean existCrewByName(final String name) {
        return crews.stream().anyMatch(crew -> crew.isSameName(name));
    }

    public boolean existTodayAttendanceByCrewName(final String name, final LocalDate today) {
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
