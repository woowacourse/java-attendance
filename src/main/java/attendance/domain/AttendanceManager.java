package attendance.domain;

import static attendance.common.utill.DateTimeFormatterWrapper.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import attendance.common.exception.AttendanceArgumentException;
import attendance.common.exception.AttendanceFileException;

public class AttendanceManager {
    public static final String ATTENDANCE_ABSENCE_HISTORY = "MM월 dd일 E요일 --:-- (결석)";
    public static final String TODAY_FORMAT = "오늘은 MM월 dd일 E요일입니다. 기능을 선택해 주세요.";
    static final LocalDate ATTENDANCE_AVAILABLE_START_DATE = LocalDate.of(2024, 12, 1);
    static final LocalDate ATTENDANCE_AVAILABLE_END_DATE = LocalDate.of(2024, 12, 31);

    static final String NICKNAME_NOT_EXISTS = "출석 정보가 존재하지 않습니다.";
    static final String CANNOT_BE_EMPTY_NICKNAME = "닉네임은 공백일 수 없습니다.";
    static final String ATTENDANCE_NOT_AVAILABLE = "출석 시스템은 2024년 12월 동안만 유효합니다";

    private static final String FILE_DOESNT_EXISTS = "존재하지 않은 파일입니다.";
    private static final String FILE_INVALID = "유효하지 않은 파일입니다.";

    private final Map<String, List<Attendance>> attendances = new HashMap<>();

    public AttendanceManager(String fileName) throws AttendanceFileException {
        URL resourceUrl = getUrl(fileName);
        readFile(resourceUrl);
    }

    private URL getUrl(String fileName) throws AttendanceFileException {
        URL resourceUrl = getClass().getResource(fileName);
        if (resourceUrl == null) {
            throw new AttendanceFileException(FILE_DOESNT_EXISTS);
        }
        return resourceUrl;
    }

    private void readFile(URL resourceUrl) throws AttendanceFileException {
        try (BufferedReader bufferedReader = new BufferedReader(
            new FileReader(resourceUrl.getFile()))) {
            bufferedReader.lines()
                .skip(1)
                .forEach(this::addAttendance);
        } catch (IOException e) {
            throw new AttendanceFileException(FILE_INVALID, e);
        }
    }

    private void addAttendance(String line) {
        var lines = line.split(",");
        var nickname = lines[0];

        List<Attendance> attendanceList = attendances.computeIfAbsent(nickname, k -> new ArrayList<>());

        var dateTime = LocalDateTime.parse(lines[1], getFormatter("yyyy-MM-dd HH:mm"));
        var attendance = new Attendance(dateTime);

        attendanceList.add(attendance);
    }

    public void addAttendance(String nickname, Attendance attendance) {
        attendances.get(nickname).add(attendance);
    }

    public Attendance findAttendance(String nickname, Attendance attendance) {
        var attendanceList = attendances.get(nickname);

        return attendanceList.stream()
            .filter((i) -> i.equals(attendance))
            .findFirst()
            .orElseThrow(() -> new AttendanceArgumentException("출석 정보를 찾을 수 없습니다."));
    }
}
