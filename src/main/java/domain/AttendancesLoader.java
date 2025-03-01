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

    public Attendances load(FileReader fileReader) throws IOException {
        BufferedReader reader = new BufferedReader(fileReader);
        Map<String, List<Attendance>> attendances = new HashMap<>();
        try {
            skipTitleLine(reader);
            addAttendanceLog(reader, attendances);
        } catch (Exception e) {
            throw new IOException("[ERROR] 출석 파일을 읽는 중 오류가 발생했습니다.");
        }

        return new Attendances(attendances);
    }

    private void addAttendanceLog(BufferedReader reader, Map<String, List<Attendance>> attendances) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] nameAndDatetime = line.split(",");
            String nickname = nameAndDatetime[0];
            String datetime = nameAndDatetime[1];
            LocalDateTime localDateTime = LocalDateTime.parse(datetime, formatter);

            attendances.computeIfAbsent(nickname, k -> new ArrayList<>()).add(new Attendance(localDateTime));
        }
    }

    private void skipTitleLine(BufferedReader reader) throws IOException {
        reader.readLine();
    }
}
