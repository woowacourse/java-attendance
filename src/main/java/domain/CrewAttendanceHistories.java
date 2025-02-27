package domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record CrewAttendanceHistories(List<CrewAttendanceHistory> crewAttendanceHistories,
                                      CrewDismiss crewDismiss) {
    public static CrewAttendanceHistories from(List<CrewAttendanceHistory> crewAttendanceHistories) {
        Map<AttendanceStatus, Integer> crewAttendanceStatusCount = new HashMap<>();
        for (CrewAttendanceHistory attendanceHistory : crewAttendanceHistories) {
            CrewAttendance crewAttendance = attendanceHistory.crewAttendance();
            AttendanceStatus attendanceStatus = crewAttendance.attendanceStatus();
            crewAttendanceStatusCount.put(attendanceStatus,
                    crewAttendanceStatusCount.getOrDefault(attendanceStatus, 0) + 1);
        }
        return new CrewAttendanceHistories(crewAttendanceHistories, new CrewDismiss(crewAttendanceStatusCount));
    }
}
