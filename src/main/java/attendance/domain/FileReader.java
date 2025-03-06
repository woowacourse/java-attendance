package attendance.domain;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FileReader {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public AttendanceBook readCSV() {
        File file = new File("src/main/resources/attendances.csv");

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
            br.readLine();
            Map<Crew, AttendanceLog> crewAttendanceLogRecord = new HashMap<>();
            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                Crew crew = new Crew(parts[0]);
                LocalDateTime localDateTime = LocalDateTime.parse(parts[1], dateTimeFormatter);

                AttendanceLog attendanceLog = crewAttendanceLogRecord.getOrDefault(crew, new AttendanceLog(new ArrayList<>()));
                attendanceLog.registerAttendance(localDateTime);

                crewAttendanceLogRecord.put(crew, attendanceLog);
            }

            return new AttendanceBook(crewAttendanceLogRecord);
        } catch (Exception e) {
            throw new IllegalArgumentException("파일을 읽어들이는 중에 오류가 발생했습니다.");
        }
    }
}
