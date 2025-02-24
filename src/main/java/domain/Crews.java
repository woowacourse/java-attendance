package domain;

import error.CustomIllegalArgumentException;
import java.util.Comparator;
import java.util.List;

public class Crews {

    private final List<Crew> crews;

    public Crews(final List<Crew> crews) {
        this.crews = crews;
    }

    public Crew findByNickname(final Nickname nickname) {
        return crews.stream()
                .filter(crew -> crew.getNickname().equals(nickname))
                .findFirst()
                .orElseThrow(() -> new CustomIllegalArgumentException("크루가 존재하지 않습니다."));
    }

    public List<Crew> getSortedCrews() {
        return crews.stream().
                sorted(sortCrew())
                .toList();
    }

    private Comparator<Crew> sortCrew() {
        return punishmentOrder.thenComparing(absenceOrder)
                .thenComparing(tardinessOrder)
                .thenComparing(nameOrder);
    }

    private final Comparator<Crew> punishmentOrder = Comparator
            .comparingInt(this::getCrewPunishmentAbsenceCount)
            .reversed();

    private int getCrewPunishmentAbsenceCount(final Crew crew) {
        return findPunishment(crew).getAbsenceCount();
    }

    private Punishment findPunishment(final Crew crew) {
        return Punishment.findByAbsenceCount(crew.calculateAdjustedAbsenceCountWithTardinessCount());
    }

    private final Comparator<Crew> absenceOrder = Comparator
            .comparingInt((Crew crew) -> crew.getAttendanceCounter().getAbsence())
            .reversed();

    private final Comparator<Crew> tardinessOrder = Comparator
            .comparingInt((Crew crew) -> crew.getAttendanceCounter().getTardiness())
            .reversed();

    private final Comparator<Crew> nameOrder = Comparator.comparing(Crew::getNickname);
}
