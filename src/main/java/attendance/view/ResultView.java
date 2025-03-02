package attendance.view;

import attendance.domain.AttendanceState;
import java.time.LocalDateTime;
import java.util.Map;

public class ResultView {

    private static final String LINE = System.lineSeparator();
    private static final Map<AttendanceState, String> ATTENDANCE_STATE_KOREAN = Map.of(
            AttendanceState.ATTENDANCE, "출석",
            AttendanceState.TARDINESS, "지각",
            AttendanceState.ABSENCE, "결석"
    );

    public void showAttendance(final LocalDateTime attendanceTime, final AttendanceState attendanceState) {
        showln(LINE + TimeFormatter.makeDateTimeMessage(attendanceTime)
                + String.format(" (%s)", ATTENDANCE_STATE_KOREAN.get(attendanceState)));
    }

    private void showln(String line) {
        System.out.println(line);
    }
}
