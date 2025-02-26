package util;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface AttendanceDataReader {
    Map<String, List<LocalDateTime>> loadAttendanceData();
}