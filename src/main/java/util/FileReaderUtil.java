package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class FileReaderUtil {

    public static final String DEFAULT_ATTENDANCE_DATA_PATH = "src/main/resources/attendances.csv";
    private static final int HEADER = 1;

    public static List<String> read(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            return reader.lines()
                    .skip(HEADER)
                    .toList();
        } catch (IOException e) {
            throw new IOException("출석 데이터를 읽어오는데 실패했습니다: " + filePath);
        }
    }
}
