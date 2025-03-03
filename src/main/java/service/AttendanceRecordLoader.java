package service;

import domain.AttendanceRecord;
import domain.Crew;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Stream;

public class AttendanceRecordLoader {

    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final String ATTENDANCE_RECORDS_FILE_PATH = "src/main/resources/attendances.csv";

    private AttendanceRecordLoader() {
    }

    public static List<AttendanceRecord> loadAttendanceRecordsFromFile() {
        try (Stream<String> lines = Files.lines(Path.of(ATTENDANCE_RECORDS_FILE_PATH))) {
            return lines.skip(1)
                    .map(AttendanceRecordLoader::converToAttendanceRecord)
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException("파일을 읽어오는데 실패했습니다.");
        }
    }

    private static AttendanceRecord converToAttendanceRecord(String line) {
        String[] parsed = line.split(",", -1);
        String nickname = parsed[0];
        String dateTime = parsed[1];
        LocalDateTime d = LocalDateTime.parse(dateTime, DATE_TIME_FORMAT);
        return AttendanceRecord.of(new Crew(nickname), d.toLocalDate(), d.toLocalTime());
    }
}
