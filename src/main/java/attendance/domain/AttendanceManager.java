package attendance.domain;

import static attendance.common.utill.DateTimeFormatterWrapper.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import attendance.common.exception.AttendanceArgumentException;
import attendance.common.exception.AttendanceFileException;

public class AttendanceManager {
    private static final int csvInfo = 1;
    private final Map<String, AttendanceList> attendances = new HashMap<>();

    public AttendanceManager(String fileName) throws AttendanceFileException {
        URL resourceUrl = getUrl(fileName);
        readFile(resourceUrl);
    }

    private URL getUrl(String fileName) throws AttendanceFileException {
        URL resourceUrl = getClass().getResource(fileName);
        if (resourceUrl == null) {
            throw new AttendanceFileException(Error.NOT_EXIST_FILE.getMessage());
        }
        return resourceUrl;
    }

    private void readFile(URL resourceUrl) throws AttendanceFileException {
        try (BufferedReader bufferedReader = new BufferedReader(
            new FileReader(resourceUrl.getFile()))) {
            bufferedReader.lines()
                .skip(csvInfo)
                .forEach(this::addAttendance);
        } catch (IOException e) {
            throw new AttendanceFileException(Error.INVALID_FILE.getMessage(), e);
        }
    }

    private void addAttendance(String line) {
        var lines = line.split(Format.REGEX);
        var nickname = lines[0];

        AttendanceList attendanceList = attendances.computeIfAbsent(nickname, k -> new AttendanceList());

        var dateTime = LocalDateTime.parse(lines[1], getFormatter(Format.DATETIME_FORMAT));
        var attendance = new Attendance(dateTime);

        attendanceList.add(attendance);
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

    public Attendance findAttendance(String nickname, Attendance attendance) {
        var attendanceList = attendances.get(nickname);
        return attendanceList.findAttendance(attendance);
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
