package attendance.domain;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public record SanctionStatistics(List<StatusStatistics> statistics) implements Iterable<StatusStatistics> {
    public static SanctionStatistics sortedFrom(List<StatusStatistics> statistics) {
        var sortedStatistics = statistics.stream()
            .sorted(Comparator.reverseOrder())
            .toList();

        return new SanctionStatistics(sortedStatistics);
    }

    @Override
    public Iterator<StatusStatistics> iterator() {
        return statistics.iterator();
    }
}

