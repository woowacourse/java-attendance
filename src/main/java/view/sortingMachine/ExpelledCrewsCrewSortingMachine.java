package view.sortingMachine;

import constant.Constants;
import domain.Crew;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ExpelledCrewsCrewSortingMachine implements CrewSortingMachine {

    @Override
    public List<Crew> sortCrews(List<Crew> crews) {
        List<Crew> sortedCrews = new ArrayList<>(crews);
        sortedCrews.sort(Comparator.comparing(Crew::getCrewStatusSequence)
                .thenComparing(crew -> crew.getExpelledAbsentCount(Constants.NOW_DATE))
                .reversed()
                .thenComparing(Crew::getName));
        return sortedCrews;
    }
}
