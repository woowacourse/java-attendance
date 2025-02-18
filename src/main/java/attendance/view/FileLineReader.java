package attendance.view;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

public class FileLineReader {

    public List<String> readAllLines(final String filePath, final String fileName) {
        try (Stream<String> fileLines = Files.lines(Paths.get(filePath + fileName), StandardCharsets.UTF_8)) {
            return fileLines.toList();
        } catch (IOException e) {
            throw new IllegalArgumentException("올바른 경로를 입력해주세요.", e);
        }
    }

}
