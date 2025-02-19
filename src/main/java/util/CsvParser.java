package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static java.nio.file.Paths.get;

public class CsvParser {
    private static final String DELIMITER = ",";

    public static List<List<String>> readFile(String fileName) {
        validateFileReadable(fileName);
        Path path = get(fileName);
        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            return reader.lines().skip(1)
                    .filter(line -> line != null && !line.trim().isEmpty())
                    .map(CsvParser::parseLine)
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
            throw new IllegalStateException();
        }
        Path path = get(fileName);
        if (!(Files.exists(path) && Files.isReadable(path))) {
            throw new IllegalStateException();
        }
    }
}
