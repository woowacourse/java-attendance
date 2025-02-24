package domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class AbsencePolicyStatistics {
    private final Map<AbsencePolicy, Integer> absencePolicies;

    public AbsencePolicyStatistics(Map<AbsencePolicy, Integer> absencePolicies) {
        this.absencePolicies = absencePolicies;
    }

    public static void calculateAttendanceHistory(Crew crew, List<LocalDateTime> localDateTimes) {
        
    }
}
