package domain;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.ToIntFunction;

public class Crews {

    private final List<Crew> crews;

    public Crews(final List<Crew> crews) {
        this.crews = crews;
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
