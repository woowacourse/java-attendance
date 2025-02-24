package domain;

import exception.CrewsException;
import java.util.Comparator;
import java.util.List;

public class Crews {

    private static final int TARDINESS_PENALTY_MULTIPLIER = 3;

    private final List<Crew> crews;

    public Crews(final List<Crew> crews) {
        this.crews = crews;
    }

    public Crew findByNickname(final Nickname nickname) {
        return crews.stream()
                .filter(o -> o.getNickname().equals(nickname))
                .findFirst()
                .orElseThrow(
                        () -> new IllegalArgumentException(CrewsException.INVALID_EXIST_CREW.getRawMessage()));
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
            .comparingInt(
                    (Crew crew) -> Punishment.findByAbsenceCount(findPunishmentCountByCrew(crew)).getAbsenceCount())
            .reversed();

    private static int findPunishmentCountByCrew(final Crew crew) {
        final AttendanceCounter attendanceCounter = crew.getAttendanceCounter();
        return (attendanceCounter.getTardinessCount() * TARDINESS_PENALTY_MULTIPLIER)
                + attendanceCounter.getAbsenceCount();
    }

    private final Comparator<Crew> absenceOrder = Comparator
            .comparingInt((Crew crew) -> crew.getAttendanceCounter().getAbsenceCount())
            .reversed();

    private final Comparator<Crew> tardinessOrder = Comparator
            .comparingInt((Crew crew) -> crew.getAttendanceCounter().getTardinessCount())
            .reversed();

    private final Comparator<Crew> nameOrder = Comparator.comparing(Crew::getNickname);
}
