package attendance.domain.attendanceBook;

import static attendance.common.utill.DateTimeFormatterWrapper.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record AttendanceBook(Map<String, AttendanceList> attendances) {

    public static AttendanceBook from(List<String> lines) {
        Map<String, AttendanceList> attendances = new HashMap<>();
        for (String line : lines) {
            addAttendance(line, attendances);
        }
        return new AttendanceBook(attendances);
    }

    private static void addAttendance(String line, Map<String, AttendanceList> attendances) {
        var lines = line.split(Format.REGEX);
        var nickname = lines[0];

        AttendanceList attendanceList = attendances.computeIfAbsent(nickname, k -> new AttendanceList());

        var dateTime = LocalDateTime.parse(lines[1], getFormatter(Format.DATETIME_FORMAT));
        var attendance = new Attendance(dateTime);

        attendanceList.add(attendance);
    }

    public Attendance findAttendance(String nickname, Attendance attendance) {
        var attendanceList = getAttendanceList(nickname);
        return attendanceList.findAttendance(attendance);
    }

    public Attendance findAttendance(String nickname, LocalDate date) {
        var attendanceList = getAttendanceList(nickname);
        return attendanceList.findAttendance(date);
    }

    private AttendanceList getAttendanceList(String nickname) {
        var attendanceList = attendances.get(nickname);
        if (attendanceList == null) {
            throw new NullPointerException();
        }
        return attendanceList;
    }

    private static final class Format {
        public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm";
        public static final String REGEX = ",";
        public static final String ATTENDANCE_ABSENCE_HISTORY = "MM월 dd일 E요일 --:-- (결석)";

        private Format() {
        }
    }
}
