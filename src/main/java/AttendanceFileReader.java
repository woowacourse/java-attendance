import domain.AttendanceManager;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AttendanceFileReader {

    private final static String path = "src/main/resources/attendances.csv";

    public void readFiles(AttendanceManager attendanceManager) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String nickname = line.split(",")[0];
                String attendanceTime = line.split(",")[1];
                LocalDateTime parsedAttendanceTime = parseAttendanceTime(attendanceTime);
                attendanceManager.addCrew(nickname, parsedAttendanceTime);
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("[ERROR] 크루 출석 기록 파일을 읽는데 실패했습니다.");
        }
    }

    private LocalDateTime parseAttendanceTime(String attendanceTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(attendanceTime, formatter);
    }
}
