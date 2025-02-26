package domain.attendance;

import java.util.Arrays;
import java.util.Optional;

public enum AttendanceWarning {
    WEEDING("제적", 6),
    INTERVIEW("면담", 3),
    WARNING("경고", 2),
    NONE("", 0),
    ;

    private final String status;
    private final int absenceCount;

    AttendanceWarning(String status, int absenceCount) {
        this.status = status;
        this.absenceCount = absenceCount;
    }

    public String getStatus() {
        return status;
    }

    public static AttendanceWarning determineAttendanceWarning(int absenceIncludingTardyCount) {
        return Arrays.stream(values())
                .filter(value -> value.absenceCount <= absenceIncludingTardyCount)
                .findAny()
                .orElse(NONE);
    }
}
