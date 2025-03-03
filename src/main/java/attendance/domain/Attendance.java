package attendance.domain;

import attendance.constant.Holiday;
import attendance.util.DateUtil;
import attendance.util.FormattedErrorMessage;

import java.time.LocalDate;
import java.time.LocalTime;

public record Attendance(LocalDate attendDate, LocalTime attendTime) {

    public Attendance {
        validateAttendDate(attendDate);
        validateAttendTime(attendTime);
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

    public boolean isSameYearAndMonth(LocalDate inputDate) {
        return attendDate.getYear() == inputDate.getYear()
                && attendDate.getMonthValue() == inputDate.getMonthValue();
    }
}
