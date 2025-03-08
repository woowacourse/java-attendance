package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public final class CsvReader {
    private static final String DELIMITER = ",";
    // 첫 번째줄은 해당 열이 무엇을 뜻하는 지 섹션 타이틀이 기록되어있기에 제거
    private static final int LINES_TO_SKIP = 1;

    private CsvReader() {
    }

    public static List<String[]> readFile(final String filePath) {
        try (final BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            return br.lines()
                    .skip(LINES_TO_SKIP)
                    .map(line -> line.split(DELIMITER))
                    .toList();
        } catch (final IOException e) {
            throw new IllegalStateException("CSV 파일을 읽는 중 오류 발생: " + filePath, e);
        }
    }
}
