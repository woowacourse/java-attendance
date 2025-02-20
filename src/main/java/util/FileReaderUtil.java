package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class FileReaderUtil {

    // TODO 인자로 받아서 생성자에서 사용하도록...
    private static final String ATTENDANCE_DATA_PATH = "src/main/resources/attendance.csv";
    private static final int HEADER = 1;

    public List<String> read() {
        try (BufferedReader reader = new BufferedReader(new FileReader(ATTENDANCE_DATA_PATH))) {

            return reader.lines()
                    .skip(HEADER)
                    .toList();

        } catch (IOException e) {
            throw new IllegalArgumentException("[ERROR] 출석 데이터를 읽어오는데 실패했습니다");
        }
    }
}
