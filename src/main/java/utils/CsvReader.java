package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CsvReader {
    public static List<String> readExistedRecords(String filePath) {
        Path path = Path.of(filePath);

        try (Stream<String> lines = Files.lines(path)) {
            return lines.collect(Collectors.toList());
        } catch (IOException e) {
            throw new IllegalArgumentException("파일을 읽어오는 중 오류가 발생하였습니다." + e.getMessage());
        }
    }
}