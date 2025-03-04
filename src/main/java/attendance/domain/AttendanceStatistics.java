package attendance.domain;

import java.util.Map;

public interface AttendanceStatistics {
    Map<AttendanceStatus, Integer> getTotalStatusCount(Attendances attendances);
}
