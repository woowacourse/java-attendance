package domain;

import java.util.EnumMap;
import java.util.List;
import java.util.stream.Collectors;

public class AttendanceStatusCalculator {

    private static final int INIT_NUMBER = 1;

    public AttendanceStatusCounter calculateAllCount(AttendanceRecords attendanceRecords) {
        EnumMap<AttendanceStatus, Integer> attendanceStatusesCount = attendanceRecords.attendances().stream()
                .map(Attendance::getAttendanceStatus)
                .collect(Collectors.toMap(
                        status -> status,
                        status -> INIT_NUMBER,
                        Integer::sum,
                        () -> new EnumMap<>(AttendanceStatus.class)
                ));

        return AttendanceStatusCounter.create(attendanceStatusesCount);
    }
}
