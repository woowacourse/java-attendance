package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class FileReaderUtil {

    private static final int HEADER = 1;

    private final String filePath;

    public FileReaderUtil(String filePath) {
        this.filePath = filePath;
    }

    public List<String> read() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            return reader.lines()
                    .skip(HEADER)
                    .toList();

        } catch (IOException e) {
            throw new IllegalArgumentException("[ERROR] 출석 데이터를 읽어오는데 실패했습니다");
        }
    }
}
