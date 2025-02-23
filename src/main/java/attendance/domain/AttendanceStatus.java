package attendance.domain;

import java.util.EnumMap;

public class AttendanceStatus {

    private final EnumMap<AttendanceStatusType, Integer> status;
    private final AttendanceWarningType warningType;

    public AttendanceStatus(final Attendances attendances) {
        status = new EnumMap<>(AttendanceStatusType.class);

        int expulsion = attendances.calculateStatusUntilYesterday(AttendanceStatusType.EXPULSION);
        int late = attendances.calculateStatusUntilYesterday(AttendanceStatusType.LATE);

        status.put(AttendanceStatusType.EXPULSION, expulsion);
        status.put(AttendanceStatusType.LATE, late);
        status.put(AttendanceStatusType.ATTENDANCE, attendances.calculateStatusUntilYesterday(AttendanceStatusType.ATTENDANCE));

        warningType = AttendanceWarningType.find(expulsion, late);
    }

    public EnumMap<AttendanceStatusType, Integer> getStatus() {
        return status;
    }

    public AttendanceWarningType getWarningType() {
        return warningType;
    }
}
