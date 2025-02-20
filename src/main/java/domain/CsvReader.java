package domain;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CsvReader {
    private static final String INVALID_FILE_PATH = "[ERROR] 파일이 존재하지 않습니다: ";
    private static final String FILE_READING_FAIL = "[ERROR] 파일을 읽어오는 도중 오류가 발생했습니다: ";
    private static final String INVALID_FILE_TYPE = "[ERROR] 해당 디렉토리는 읽을 수 없습니다: ";

    // CSV 파일의 내용을 읽고 List<String> 반환
    public List<String> readCsv(String csvFilePath) {
        Path path = Path.of(csvFilePath);
        validateFilePath(path);

        try (Stream<String> lines = Files.lines(path)) {  // try-with-resources 사용
            return lines.collect(Collectors.toList());
        } catch (IOException e) {
            throw new IllegalArgumentException(FILE_READING_FAIL + e.getMessage(), e);
        }
    }

    private void validateFilePath(Path filePath) {
        if (!Files.exists(filePath)) {
            throw new IllegalArgumentException(INVALID_FILE_PATH + filePath);
        }
        if (!Files.isRegularFile(filePath)) {
            throw new IllegalArgumentException(INVALID_FILE_TYPE + filePath);
        }
    }
}