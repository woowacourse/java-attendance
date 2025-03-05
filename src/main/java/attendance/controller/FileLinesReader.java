package attendance.controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

public class FileLinesReader {

    private FileLinesReader() {}

    public static List<String> readLinesWithoutFirstLine(final String filePath, final String fileName) {
        try (Stream<String> allLines = Files.lines(Paths.get(filePath, fileName), StandardCharsets.UTF_8)) {
            return allLines.skip(1L)
                    .toList();
        } catch (IOException e) {
            throw new IllegalArgumentException("해당 파일이 존재하지 않습니다.");
        }
    }

}
