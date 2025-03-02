package domain;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttendancesLoader {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final int TITLE_LINE = 1;

    public Attendances load(FileReader fileReader) throws IOException {
        BufferedReader reader = new BufferedReader(fileReader);
        Map<Nickname, List<Attendance>> attendances = new HashMap<>();
        try {
            addAttendanceLogs(reader, attendances);
        } catch (Exception e) {
            throw new IOException("[ERROR] 출석 파일을 읽는 중 오류가 발생했습니다.");
        }

        return new Attendances(attendances);
    }

    private void addAttendanceLogs(BufferedReader reader, Map<Nickname, List<Attendance>> attendances) throws IOException {
        List<AttendanceLog> validLogs = readFileLogs(reader).stream()
                .map(log -> {
                    String[] nameAndDateTime = log.split(",");
                    return new AttendanceLog(new Nickname(nameAndDateTime[0]), LocalDateTime.parse(nameAndDateTime[1], formatter));
                })
                .filter(log -> !log.localDateTime().toLocalDate().isAfter(TimeMachine.dateOfNow()))
                .toList();

        for (AttendanceLog log : validLogs) {
            attendances.computeIfAbsent(log.nickname(), k -> new ArrayList<>()).add(new Attendance(log.localDateTime()));
        }
    }

    private List<String> readFileLogs(BufferedReader reader) {
        return reader.lines()
                .skip(TITLE_LINE)
                .toList();
    }
}
