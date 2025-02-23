package util;

import domain.ErrorCode;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CsvReader {
    public List<String> readCsv(String csvFilePath) {
        Path path = Path.of(csvFilePath);
        validateFilePath(path);

        try (Stream<String> lines = Files.lines(path)) {
            return lines.collect(Collectors.toList());
        } catch (IOException e) {
            throw new IllegalArgumentException(ErrorCode.CSV_FILE_READING_FAIL.getMessage() + e.getMessage(), e);
        }
    }

    private void validateFilePath(Path filePath) {
        if (!Files.exists(filePath)) {
            throw new IllegalArgumentException(ErrorCode.CSV_INVALID_FILE_PATH.getMessage() + filePath);
        }
        if (!Files.isRegularFile(filePath)) {
            throw new IllegalArgumentException(ErrorCode.CSV_INVALID_FILE_TYPE.getMessage() + filePath);
        }
    }
}