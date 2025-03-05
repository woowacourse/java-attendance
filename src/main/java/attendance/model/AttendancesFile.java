package attendance.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class AttendancesFile {

    private static final String CSV_HEADER_FORMAT = "nickname,datetime";
    private static final String COLUMN_DELIMITER = ",";
    private static final int EXPECTED_COLUMN_COUNT = CSV_HEADER_FORMAT.split(COLUMN_DELIMITER).length;
    private static final int NICKNAME_INDEX = 0;
    private static final int ATTENDANCE_DATE_TIME_INDEX = 1;
    private static final DateTimeFormatter DATETIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public AttendanceLogs load(String path) {
        try {
            List<String> rows = readAttendanceData(path);
            return convertToAttendanceLogs(rows);
        } catch (IOException e) {
            throw new IllegalStateException("출석 로그 파일 입출력 중 문제가 발생했습니다. (경로: %s)".formatted(path));
        }
    }

    private List<String> readAttendanceData(String path) throws IOException {
        List<String> lines = Files.readAllLines(Path.of(path));
        if (lines.isEmpty()) {
            throw new IllegalStateException("출석 로그 파일이 파일이 비어 있습니다. (경로: %s)".formatted(path));
        }
        return removeHeader(lines);
    }

    private List<String> removeHeader(List<String> lines) {
        String header = lines.getFirst();
        if (!CSV_HEADER_FORMAT.equals(header)) {
            throw new IllegalStateException("출석 로그 파일의 헤더 형식이 올바르지 않습니다. (헤더: %s)".formatted(header));
        }
        return lines.subList(1, lines.size());
    }

    private AttendanceLogs convertToAttendanceLogs(List<String> rows) {
        AttendanceLogs attendanceLogs = new AttendanceLogs();
        for (String row : rows) {
            attendanceLogs.add(parseAttendanceLog(row));
        }
        return attendanceLogs;
    }

    private AttendanceLog parseAttendanceLog(String row) {
        String[] columns = row.split(COLUMN_DELIMITER);
        if (columns.length != EXPECTED_COLUMN_COUNT) {
            throw new IllegalStateException("출석 로그 파일의 데이터 형식이 올바르지 않습니다. (입력된 값: %s)".formatted(row));
        }
        return createAttendanceLog(columns);
    }

    private AttendanceLog createAttendanceLog(String[] columns) {
        Nickname nickname = new Nickname(columns[NICKNAME_INDEX]);
        LocalDateTime attendanceDateTime = parseDateTime(columns[ATTENDANCE_DATE_TIME_INDEX]);
        return new AttendanceLog(nickname, attendanceDateTime.toLocalDate(), attendanceDateTime.toLocalTime());
    }

    private LocalDateTime parseDateTime(String rawDateTime) {
        try {
            return LocalDateTime.parse(rawDateTime, DATETIME_FORMAT);
        } catch (DateTimeParseException e) {
            throw new IllegalStateException("출석 로그 파일의 시간 데이터 형식이 올바르지 않습니다. (입력된 값: %s)".formatted(rawDateTime));
        }
    }
}
