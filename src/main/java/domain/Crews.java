package domain;

import java.util.Comparator;
import java.util.List;

public class Crews {
    private List<Crew> crews;

    public Crews(final List<Crew> crews) {
        this.crews = crews;
    }

    public Crew findByNickname(final Nickname nickname) {
        return crews.stream()
                .filter(o -> o.nickname.equals(nickname))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("크루가 존재하지 않습니다."));
    }

    public List<Crew> getSortedCrews() {
        return crews.stream().
                sorted(
                        punishmentOrder
                                .thenComparing(absenceOrder)
                                .thenComparing(tardinessOrder)
                                .thenComparing(nameOrder)
                )
                .toList();
    }

    private final Comparator<Crew> punishmentOrder = Comparator
            .comparingInt((Crew crew) -> Punishment.findByAbsenceCount(getPunishment(crew)).getAbsenceCount())
            .reversed();

    private static int getPunishment(final Crew crew) {
        final AttendanceCounter attendanceCounter = crew.getAttendanceCounter();
        return (attendanceCounter.getTardiness() * 3) + attendanceCounter.getAbsence();
    }

    private final Comparator<Crew> absenceOrder = Comparator
            .comparingInt((Crew crew) -> crew.getAttendanceCounter().getAbsence())
            .reversed();

    private final Comparator<Crew> tardinessOrder = Comparator
            .comparingInt((Crew crew) -> crew.getAttendanceCounter().getTardiness())
            .reversed();

    private final Comparator<Crew> nameOrder = Comparator.comparing(Crew::getNickname);

}
