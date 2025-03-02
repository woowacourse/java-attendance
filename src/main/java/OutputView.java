import java.time.LocalDateTime;

public class OutputView {
    public static void printAttendanceHistory(AttendanceHistory attendanceHistory) {
        LocalDateTime attendAt = attendanceHistory.getAttendAt();
        String parsedAttendAt = InputParser.parseDateTimeToString(attendAt);
        String parsedAttendanceType = InputParser.parseAttendanceType(
                AttendanceType.findAttendanceTypeByDateTime(attendAt));

        System.out.printf("%s (%s)", parsedAttendAt, parsedAttendanceType);
    }
}
