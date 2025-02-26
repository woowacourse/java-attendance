package attendance.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class AttendanceFileReader {

    private static final String PATH = "src/main/resources/attendances";

    public static BufferedReader read() throws IOException {
        return new BufferedReader(new FileReader(PATH));
    }

    public static Map<String, LocalDateTime> readCrewAttendances(final BufferedReader br)
        throws IOException {
        String string;
        br.readLine();
        Map<String, LocalDateTime> fileReadResult = new HashMap<>();
        while ((string = br.readLine()) != null) {
            String[] split = string.split(",");
            String nickname = split[0];
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime dateTime = LocalDateTime.parse(split[1], formatter);
            fileReadResult.put(nickname, dateTime);
        }
        return fileReadResult;
    }
}

