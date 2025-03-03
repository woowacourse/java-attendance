import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class OutputView {
    public static void printAttendanceHistory(AttendanceHistory attendanceHistory) {
        LocalDateTime attendAt = attendanceHistory.getAttendAt();
        String parsedAttendAt = InputParser.parseDateTimeToString(attendAt);
        String parsedAttendanceType = InputParser.parseAttendanceType(
                AttendanceType.findAttendanceTypeByDateTime(attendAt));

        System.out.printf("%s (%s)%n", parsedAttendAt, parsedAttendanceType);
    }

    public static void printAttendanceHistories(Crew crew, Map<LocalDateTime, AttendanceType> historiesOfCrew) {
        System.out.printf("이번달 %s의 출석 기록입니다.%n", crew.getName());

        List<Entry<LocalDateTime, AttendanceType>> sortedHistories = new ArrayList<>(historiesOfCrew.entrySet());
        sortedHistories.sort(Map.Entry.comparingByKey());
        sortedHistories.forEach(entry -> {
            String attendAt = InputParser.parseDateTimeToString(entry.getKey());
            String attendanceType = InputParser.parseAttendanceType(entry.getValue());
            System.out.printf("%s (%s)%n", attendAt, attendanceType);
        });
    }
}
