package attendance.domain;

import attendance.constant.Holiday;
import attendance.util.DateUtil;
import attendance.util.FormattedErrorMessage;

import java.time.LocalDate;
import java.time.LocalTime;

public class Attendance {

    private final LocalDate attendDate;
    private final LocalTime attendTime;

    public Attendance(LocalDate attendDate, LocalTime attendTime) {
        validateAttendDate(attendDate);
        validateAttendTime(attendTime);
        this.attendDate = attendDate;
        this.attendTime = attendTime;
    }

    private void validateAttendDate(LocalDate attendDate) {
        if (DateUtil.isWeekend(attendDate)) {
            throw new IllegalArgumentException(FormattedErrorMessage.INVALID_ATTEND_DATE_ERROR.getDateFormatMessage(attendDate));
        }

        if (Holiday.isHoliday(attendDate)) {
            throw new IllegalArgumentException(FormattedErrorMessage.INVALID_ATTEND_DATE_ERROR.getDateFormatMessage(attendDate));
        }
    }

    private void validateAttendTime(LocalTime attendTime) {
        if (CampusOperatingTime.notInOperation(attendTime)) {
            throw new IllegalArgumentException(FormattedErrorMessage.INVALID_ATTEND_TIME_ERROR.getTimeFormatMessage(attendTime));
        }
    }

    public AttendanceStatus determineStatus() {
        return AttendanceStatus.determine(attendDate, attendTime);
    }

    public boolean isSameDate(Attendance newAttendance) {
        return attendDate.isEqual(newAttendance.attendDate);
    }

    public boolean isSameDate(LocalDate inputDate) {
        return attendDate.isEqual(inputDate);
    }

    public LocalDate getAttendDate() {
        return attendDate;
    }

    public LocalTime getAttendTime() {
        return attendTime;
    }
}
