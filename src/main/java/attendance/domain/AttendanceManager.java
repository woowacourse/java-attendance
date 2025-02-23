package attendance.domain;

import static attendance.common.utill.DateTimeFormatterWrapper.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import attendance.common.exception.AttendanceArgumentException;

public class AttendanceManager {
    private final Map<String, AttendanceList> attendances = new HashMap<>();

    public AttendanceManager(List<String> lines) {
        lines.forEach(this::addAttendance);
    }

    private void addAttendance(String line) {
        var lines = line.split(Format.REGEX);
        var nickname = lines[0];

        AttendanceList attendanceList = attendances.computeIfAbsent(nickname, k -> new AttendanceList());

        var dateTime = LocalDateTime.parse(lines[1], getFormatter(Format.DATETIME_FORMAT));
        var attendance = new Attendance(dateTime);

        attendanceList.add(attendance);
    }

    public Attendance findAttendance(String nickname, Attendance attendance) {
        var attendanceList = attendances.get(nickname);
        return attendanceList.findAttendance(attendance);
    }

    public void addAttendance(String nickname, Attendance attendance) {
        try {
            var attendanceList = attendances.get(nickname);
            isDuplicateAttendance(attendance, attendanceList);
            attendanceList.add(attendance);
        } catch (NullPointerException e) {
            throw new AttendanceArgumentException(Error.NOT_REGISTERED_NICKNAME.getMessage());
        }
    }

    private void isDuplicateAttendance(Attendance attendance, AttendanceList attendanceList) {
        if (attendanceList.contains(attendance)) {
            throw new AttendanceArgumentException(Error.DUPLICATE_DATE.getMessage());
        }
    }

    private enum Error {
        ATTENDANCE_NOT_AVAILABLE("출석 시스템은 2024년 12월 동안만 유효합니다"),
        DUPLICATE_DATE("이미 출석되었습니다. 수정 기능을 이용해주세요."),
        CANT_FIND_INFO("출석 정보를 찾을 수 없습니다."),

        NOT_REGISTERED_NICKNAME("등록되지 않은 닉네임입니다."),

        NOT_EXIST_FILE("존재하지 않은 파일입니다."),
        INVALID_FILE("유효하지 않은 파일입니다."),
        ;
        private final String message;

        Error(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

    private static final class Format {
        public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm";
        public static final String REGEX = ",";
        public static final String ATTENDANCE_ABSENCE_HISTORY = "MM월 dd일 E요일 --:-- (결석)";

        private Format() {
        }
    }
}
