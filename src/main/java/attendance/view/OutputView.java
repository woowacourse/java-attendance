package attendance.view;

import attendance.domain.AttendanceTime;
import attendance.domain.AttendanceType;
import attendance.domain.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

public class OutputView {

    private static final String ATTENDANCE_RESULT = "%s월 %s일 %s %02d:%02d (%s) ";

    private OutputView() {}

    public static OutputView create() {
        return new OutputView();
    }

    public void printAttendanceInfo(AttendanceTime attendanceTime, AttendanceType attendanceType) {
        LocalDate date = attendanceTime.getDate();
        LocalTime time = attendanceTime.getTime();
        DayOfWeek dayOfWeek = DayOfWeek.findDayOfWeek(date);
        System.out.println(ATTENDANCE_RESULT.formatted(
            date.getMonthValue(), date.getDayOfMonth(), dayOfWeek.getDayOfWeekName(),
            time.getHour(), time.getMinute(), attendanceType.getType()));
    }

}
