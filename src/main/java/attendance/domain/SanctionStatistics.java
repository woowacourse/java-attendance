package attendance.domain;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public record SanctionStatistics(List<StatusStatistics> statistics) implements Iterable<StatusStatistics> {

    public static SanctionStatistics sortedFrom(List<StatusStatistics> statistics) {
        return new SanctionStatistics(getSortedStatusStatistics(statistics));
    }

    private static List<StatusStatistics> getSortedStatusStatistics(List<StatusStatistics> statistics) {
        return statistics.stream()
            .sorted(Comparator.reverseOrder())
            .toList();
    }

    @Override
    public Iterator<StatusStatistics> iterator() {
        return statistics.iterator();
    }
}

