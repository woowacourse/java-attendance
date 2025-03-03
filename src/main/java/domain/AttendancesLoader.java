package domain;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AttendancesLoader {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final int TITLE_LINE = 1;

    private final FileReader fileReader;

    public AttendancesLoader(FileReader fileReader) {
        this.fileReader = fileReader;
    }

    public List<AttendanceLog> load() throws IOException {
        BufferedReader reader = new BufferedReader(fileReader);

        try {
            return loadAttendanceLogs(reader);
        } catch (Exception e) {
            throw new IOException("[ERROR] 출석 파일을 읽는 중 오류가 발생했습니다.");
        }
    }

    private List<AttendanceLog> loadAttendanceLogs(BufferedReader reader) {
        return readFileLogs(reader).stream()
                .map(log -> {
                    String[] nameAndDateTime = log.split(",");
                    return new AttendanceLog(new Nickname(nameAndDateTime[0]), LocalDateTime.parse(nameAndDateTime[1], formatter));
                })
                .filter(log -> !log.localDateTime().toLocalDate().isAfter(TimeMachine.dateOfNow()))
                .toList();

    }

    private List<String> readFileLogs(BufferedReader reader) {
        return reader.lines()
                .skip(TITLE_LINE)
                .toList();
    }
}
