package domain.crew;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.ToIntFunction;

public class Crews {

    private final List<Crew> crews;

    public Crews(final List<Crew> crews) {
        this.crews = crews;
    }

    public Crew findByNickname(final Nickname nickname) {
        return crews.stream()
                .filter(crew -> crew.isSameAs(nickname))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 크루입니다."));

    }

    public List<Crew> findSortedDisciplinaryCrews() {
        return crews.stream()
                .sorted(sortCrew())
                .toList();
    }

    private Comparator<Crew> sortCrew() {
        return comparingByDisciplinaryStatus()
                .thenComparingInt(comparingByAdjustedAbsence())
                .thenComparing(comparingByNickname());
    }

    private Comparator<Crew> comparingByDisciplinaryStatus() {
        return Comparator.comparingInt((Crew crew) -> crew.getDisciplinaryStatus().getAbsenceCount())
                .reversed();
    }

    private ToIntFunction<Crew> comparingByAdjustedAbsence() {
        return crew -> -crew.getAttendanceRecords().getAttendanceStatusCounts()
                .getAdjustedAbsence();
    }

    private Function<Crew, String> comparingByNickname() {
        return crew -> crew.getNickname().getValue();
    }
}
