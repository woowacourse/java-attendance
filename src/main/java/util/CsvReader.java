package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public final class CsvReader {

    private static final String DELIMITER = ",";
    private static final int LINES_TO_SKIP = 1;

    private CsvReader() {
    }

    public static List<String[]> readFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            return br.lines()
                    .skip(LINES_TO_SKIP)
                    .map(line -> line.split(DELIMITER))
                    .toList();
        } catch (IOException e) {
            throw new IllegalStateException("[ERROR] CSV 파일을 읽는 중 오류 발생: " + filePath, e);
        }
    }
}
