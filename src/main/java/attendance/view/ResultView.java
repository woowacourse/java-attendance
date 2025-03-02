package attendance.view;

import attendance.domain.AttendanceState;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;

public class ResultView {

    private static final String LINE = System.lineSeparator();
    private static final Map<AttendanceState, String> ATTENDANCE_STATE_KOREAN = Map.of(
            AttendanceState.ATTENDANCE, "출석",
            AttendanceState.TARDINESS, "지각",
            AttendanceState.ABSENCE, "결석"
    );
    private static final String TITLE_ATTENDANCE = "%s (%s)";
    private static final String TITLE_MODIFYING = "%s (%s) -> %s (%s) 수정 완료!";

    public void showBlank() {
        System.out.println();
    }

    public void showAttendance(final LocalDateTime attendanceTime, final AttendanceState attendanceState) {
        showBlank();
        System.out.printf(TITLE_ATTENDANCE, TimeFormatter.makeDateTimeMessage(attendanceTime),
                getAttendanceState(attendanceState));
    }

    public void showModifyingAttendance(final LocalDateTime previousDateTime,
                                        final AttendanceState previousAttendanceState,
                                        final LocalTime modifyingTime,
                                        final AttendanceState afterAttendanceState) {
        showBlank();
        System.out.printf(TITLE_MODIFYING, TimeFormatter.makeDateTimeMessage(previousDateTime),
                getAttendanceState(previousAttendanceState), TimeFormatter.makeTimeMessage(modifyingTime),
                getAttendanceState(afterAttendanceState));
    }

    private String getAttendanceState(final AttendanceState attendanceState) {
        return ATTENDANCE_STATE_KOREAN.get(attendanceState);
    }
}
