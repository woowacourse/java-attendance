package attendance.view;

import attendance.domain.AttendanceResult;
import attendance.domain.AttendanceType;
import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class OutputView {
    private static final String ATTENDANCE_RESULT_MESSAGE = "%d월 %d일 %s %02d:%02d (%s)";

    /**
     * 12월 13일 금요일 09:59 (출석)
     */
    public void printAttendanceResult(AttendanceResult attendanceResult) {
        LocalDateTime attendanceTime = attendanceResult.getAttendanceTime();
        int month = attendanceTime.getMonthValue();
        int day = attendanceTime.getDayOfMonth();
        DayOfWeek dayOfWeek = attendanceTime.getDayOfWeek();
        AttendanceType attendanceType = attendanceResult.getAttendanceType();
        System.out.println(ATTENDANCE_RESULT_MESSAGE.formatted(month, day, dayOfWeek, attendanceTime.getHour(), attendanceTime.getMinute(), attendanceType.getName()));
    }
}
