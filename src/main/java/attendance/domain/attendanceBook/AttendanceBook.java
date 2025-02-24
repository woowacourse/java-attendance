package attendance.domain.attendanceBook;

import static attendance.common.utill.DateTimeFormatterWrapper.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import attendance.common.exception.AttendanceArgumentException;

public record AttendanceBook(Map<String, AttendanceList> attendances) {
    private static final String NOT_REGISTERED_NICKNAME = "등록되지 않은 닉네임입니다.";

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

    public Optional<Attendance> findAttendance(String nickname, LocalDate date) {
        var attendanceList = getAttendanceList(nickname);
        return attendanceList.findAttendance(date);
    }

    public AttendanceList getAttendanceList(String nickname) {
        var attendanceList = attendances.get(nickname);
        if (attendanceList == null) {
            throw new AttendanceArgumentException(NOT_REGISTERED_NICKNAME);
        }
        return attendanceList;
    }

    private static final class Format {
        public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm";
        public static final String REGEX = ",";

        private Format() {
        }
    }
}
