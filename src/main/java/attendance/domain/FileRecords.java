package attendance.domain;

import java.time.LocalDateTime;
import java.util.Map;

public record FileRecords(Map<String, LocalDateTime> records) {

}
