package util;

import exception.AppException;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class CsvReader {
    private static final String DELIMITER = ",";

    public static List<List<String>> readFile(String fileName) {
        validateFileReadable(fileName);
        Path path = Paths.get(fileName);
        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            return reader.lines().skip(1)
                    .filter(line -> line != null && !line.trim().isEmpty())
                    .map(CsvReader::parseLine)
                    .toList();
        } catch (IOException e) {
            throw new IllegalStateException();
        }
    }

    private static List<String> parseLine(String line) {
        return List.of(line.split(DELIMITER));
    }

    private static void validateFileReadable(String fileName) {
        if (fileName == null || fileName.trim().isEmpty()) {
            throw new AppException("파일 이름이 비어있습니다.");
        }
        Path path = Path.of(fileName);
        if (!Files.exists(path) || !Files.isReadable(path)) {
            throw new AppException("파일이 존재하지 않습니다.");
        }
    }
}
