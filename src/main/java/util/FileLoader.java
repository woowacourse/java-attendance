package util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileLoader {

    public List<String> loadLinesExcludeTitle(String filePath) {
        try {
            List<String> lines = Files.readAllLines(Path.of(filePath));
            lines.removeFirst();
            return lines;
        } catch (IOException e) {
            throw new RuntimeException("[ERROR] 파일을 읽던 중 오류가 발생하였습니다");
        }
    }
}
