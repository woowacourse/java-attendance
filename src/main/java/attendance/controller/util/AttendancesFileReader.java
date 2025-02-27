package attendance.controller.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class AttendancesFileReader {
    private static final String ATTENDANCES_FILE_PATH = "src/main/resources/attendances.csv";
    private static final String NEW_LINE = "\n";

    private AttendancesFileReader() {
    }

    public static String read() {
        try {
            return readLines();
        } catch (IOException e) {
            throw new IllegalArgumentException("[ERROR] 파일을 제대로 읽을 수 없습니다.");
        }
    }

    private static String readLines() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(ATTENDANCES_FILE_PATH));
        String line;
        reader.readLine();
        StringBuilder sb = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            sb.append(line);
            sb.append(NEW_LINE);
        }
        return sb.toString();
    }
}
