package attendance.domain.constant;

import attendance.exception.CustomException;
import attendance.exception.ErrorMessage;
import java.util.Arrays;

public enum CrewStatus {

    DISMISS("제적", 6),
    COUNSELING("면담", 3),
    WARNING("경고", 2);

    private final String status;
    private final int limitCount;

    CrewStatus(final String status, final int limitCount) {
        this.status = status;
        this.limitCount = limitCount;
    }

    public static CrewStatus from(int lateCount, int absentCount) {
        int crewLimitCount = absentCount + lateCount / 3;

        return Arrays.stream(CrewStatus.values())
                .filter(crewStatus -> crewStatus.limitCount <= crewLimitCount)
                .findFirst()
                .orElseThrow(() -> CustomException.from(ErrorMessage.NOT_RISK_CREW));
    }

    public String getName() {
        return status;
    }

    public int getLimitCount() {
        return limitCount;
    }

}
