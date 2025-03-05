package attendance.domain;

import attendance.exception.CustomException;
import attendance.exception.ErrorMessage;

public enum CampusOperationTime {
    OPEN(8),
    CLOSE(23);

    private final int hour;

    CampusOperationTime(final int hour) {
        this.hour = hour;
    }

    public static void isOperation(final int hour) {
        if (hour < OPEN.hour || hour >= CLOSE.hour) {
            throw CustomException.from(ErrorMessage.NOT_OPEN_CAMPUS);
        }
    }

}
