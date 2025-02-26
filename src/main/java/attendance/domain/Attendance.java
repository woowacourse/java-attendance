package attendance.domain;

import attendance.constant.Holiday;
import attendance.util.DateUtil;
import attendance.util.FormattedErrorMessage;

import java.time.LocalDate;

public class Attendance {

    private final LocalDate attendDate;

    public Attendance(LocalDate attendDate) {
        validateAttendDate(attendDate);
        this.attendDate = attendDate;
    }

    private void validateAttendDate(LocalDate attendDate) {
        if (DateUtil.isWeekend(attendDate)) {
            throw new IllegalArgumentException(FormattedErrorMessage.INVALID_ATTENDANCE_ERROR.getDateFormatMessage(attendDate));
        }

        if (Holiday.isHoliday(attendDate)) {
            throw new IllegalArgumentException(FormattedErrorMessage.INVALID_ATTENDANCE_ERROR.getDateFormatMessage(attendDate));
        }
    }
}
