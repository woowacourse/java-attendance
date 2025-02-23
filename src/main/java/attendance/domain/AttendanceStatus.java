package attendance.domain;

import java.util.Arrays;
import java.util.EnumMap;

import static attendance.domain.AttendanceStateType.EXPULSION;
import static attendance.domain.AttendanceStateType.LATE;
import static attendance.domain.AttendanceStateType.values;

public class AttendanceStatus {

    private final EnumMap<AttendanceStateType, Integer> status;
    private final AttendanceWarningType warningType;

    public AttendanceStatus(final Attendances attendances) {
        status = new EnumMap<>(AttendanceStateType.class);

        Arrays.stream(values())
                .forEach(statusType -> {
                    int statusCount = attendances.calculateStatusUntilYesterday(statusType);
                    status.put(statusType, statusCount);
                });

        int expulsion = status.get(EXPULSION);
        int late = status.get(LATE);

        warningType = AttendanceWarningType.find(expulsion, late);
    }

    public static AttendanceStatus of(final Attendances attendances) {
        return new AttendanceStatus(attendances);
    }

    public EnumMap<AttendanceStateType, Integer> getStatus() {
        return status;
    }

    public AttendanceWarningType getWarningType() {
        return warningType;
    }
}
