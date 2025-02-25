package attendance.domain;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class AttendanceRiskCrews {

    private final Map<String, AttendanceStatus> riskCrews;

    private AttendanceRiskCrews(final Map<String, AttendanceStatus> riskCrews) {
        this.riskCrews = riskCrews.entrySet().stream()
                .filter(entry -> entry.getValue().isNotNoneState())
                .sorted(Map.Entry.<String, AttendanceStatus>comparingByValue()
                        .thenComparing(Map.Entry.comparingByKey()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (existing, replacement) -> existing,
                        LinkedHashMap::new
                ));
    }

    public static AttendanceRiskCrews of(final Map<String, AttendanceStatus> risks) {
        return new AttendanceRiskCrews(risks);
    }

    public Map<String, AttendanceStatus> getRiskCrews() {
        return Collections.unmodifiableMap(riskCrews);
    }
}
