package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;

public class FileReaderUtil {

    public static final String DEFAULT_ATTENDANCE_DATA_PATH = "src/main/resources/attendances.csv";
    private static final int HEADER = 1;

    public static List<String> read(String filePath) throws FileReadException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            return reader.lines()
                    .skip(HEADER)
                    .toList();
        } catch (Exception e) {
            throw new FileReadException("출석 데이터를 읽어오는데 실패했습니다: " + filePath);
        }
    }
}
