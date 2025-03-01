package attendance.util;

import attendance.domain.AttendanceBook;
import attendance.domain.AttendanceTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AttendanceReader {

    private static final String ATTENDANCE_FILE_PATH = "src/main/resources/attendances.csv";
    private static final String DELIMITER = ",";
    private static final DateTimeFormatter ATTENDANCE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static void initAttendances(final AttendanceBook attendanceBook) {

        List<String> contents = Reader.getContents(ATTENDANCE_FILE_PATH);
        contents.removeFirst();

        for (String content : contents) {
            String[] split = content.split(DELIMITER);
            addAttendance(attendanceBook, split);
        }
    }

    private static void addAttendance(final AttendanceBook attendanceBook, String[] split) {

        String crewName = split[0];
        LocalDateTime attendTime = LocalDateTime.parse(split[1], ATTENDANCE_TIME_FORMATTER);
        int hour = attendTime.getHour();
        int minute = attendTime.getMinute();

        attendanceBook.add(crewName, new AttendanceTime(attendTime.toLocalDate(), hour, minute));
    }
}
