import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AttendanceStoreService {
    public List<Object> parse(String s) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String[] parsed = s.split(",");
        Crew crew = new Crew(parsed[0]);
        LocalDateTime attendanceTime = LocalDateTime.parse(parsed[1], formatter);
        return List.of(crew, attendanceTime);
    }

    public List<String> load(String file) {
        List<String> lines = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            reader.readLine();
            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                lines.add(line);
            }
            return lines;
        } catch (IOException e) {
            throw new RuntimeException("파일 로드 중에 오류가 발생했습니다.");
        }
    }
}
