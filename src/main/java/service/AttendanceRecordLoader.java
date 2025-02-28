package service;

import domain.AttendanceRecord;
import domain.Crew;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AttendanceRecordLoader {

    private static final DateTimeFormatter DATETIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final String ATTENDANCE_RECORDS_FILE_PATH = "src/main/resources/attendances.csv";

    private AttendanceRecordLoader() {
    }

    public static List<AttendanceRecord> loadAttendanceRecordsFromFile() {
        try (FileReader fr = new FileReader(ATTENDANCE_RECORDS_FILE_PATH);
             BufferedReader br = new BufferedReader(fr);
        ) {
            return br.lines().skip(1)
                    .map(AttendanceRecordLoader::converToAttendanceRecord)
                    .toList();
        } catch (IOException e) {
            System.out.println("출석 기록 파일을 찾는데 실패했습니다.");
        }
        throw new RuntimeException("출석 기록을 읽어오는데 실패했습니다.");
    }

    private static AttendanceRecord converToAttendanceRecord(String line) {
        String[] parsed = line.split(",", -1);
        String nickname = parsed[0];
        String dateTime = parsed[1];
        LocalDateTime d = LocalDateTime.parse(dateTime, DATETIME_FORMAT);
        return AttendanceRecord.of(new Crew(nickname), d.toLocalDate(), d.toLocalTime());
    }
}
