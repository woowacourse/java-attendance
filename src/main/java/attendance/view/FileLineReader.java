package attendance.view;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileLineReader {
    public static List<String> readAllLines(final String path, final String fileName) {
        try {
            return Files.readAllLines(Path.of(path + fileName));
        } catch (IOException exception) {
            throw new IllegalArgumentException("올바른 파일 경로를 입력해 주세요.");
        }
    }
}
