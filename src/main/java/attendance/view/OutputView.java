package attendance.view;

import attendance.domain.AttendanceStatus;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Optional;

public class OutputView {
    private static final String ERROR_MESSAGE_PREFIX = "[ERROR] ";
    private static final String ATTENDANCE_DESCRIPTION_FORMAT = "%02d월 %02d일 %s %s (%s)\n";
    private static final String MODIFY_ATTENDANCE_PRINT_FORMAT = "%02d월 %02d일 %s %s (%s) -> %s (%s) 수정 완료!\n";
    private static final DateTimeFormatter PRINT_ATTENDANCE_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    private static final String EMPTY_ATTENDANCE_TIME_MESSAGE = "--:--";
    private static final String EMPTY_ATTENDANCE_STATUS_MESSAGE = "--";

    public void printCheckAttendanceResult(LocalTime enterTime) {
        System.out.print(createAttendanceDescription(LocalDate.now(), enterTime));
    }

    private String createAttendanceDescription(LocalDate date, LocalTime enterTime) {
        return String.format(ATTENDANCE_DESCRIPTION_FORMAT,
                date.getMonthValue(),
                date.getDayOfMonth(),
                getDisplayName(date),
                AttendanceStatus.from(date, enterTime).getStatus()
        );
    }

    public void printModifyAttendanceResult(LocalTime prevTime, LocalDate date, LocalTime modifyTime) {
        System.out.printf(MODIFY_ATTENDANCE_PRINT_FORMAT,
                date.getMonthValue(),
                date.getDayOfMonth(),
                getDisplayName(date),
                formatAttendanceTime(prevTime),
                formatAttendanceStatus(date, prevTime),
                formatAttendanceTime(modifyTime),
                formatAttendanceStatus(date, modifyTime)
        );
    }

    private String formatAttendanceTime(LocalTime time) {
        if (Optional.ofNullable(time).isEmpty()) {
            return EMPTY_ATTENDANCE_TIME_MESSAGE;

        }
        return toTimeString(time);
    }

    private String toTimeString(LocalTime time) {
        return time.format(PRINT_ATTENDANCE_TIME_FORMATTER);
    }

    private String formatAttendanceStatus(LocalDate date, LocalTime time) {
        if (Optional.ofNullable(time).isEmpty()) {
            return EMPTY_ATTENDANCE_STATUS_MESSAGE;
        }
        return AttendanceStatus.from(date, time).getStatus();
    }

    public void printErrorMessage(Exception error) {
        System.out.println(ERROR_MESSAGE_PREFIX + error);
    }

    private static String getDisplayName(LocalDate date) {
        return date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA);
    }
}
