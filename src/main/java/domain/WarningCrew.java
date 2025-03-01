package domain;

import java.util.Comparator;
import java.util.List;

public record WarningCrew(String name, AttendCount attendCount, WarningStatus warningStatus) {

    public static List<WarningCrew> sort(List<WarningCrew> warningCrews) {
        return warningCrews.stream()
                .sorted(Comparator.comparing(WarningCrew::rank).reversed()
                        .thenComparing(WarningCrew::name))
                .toList();
    }

    public long rank() {
        return this.attendCount.rank();
    }
}
