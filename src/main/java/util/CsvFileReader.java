package util;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class CsvFileReader {

    public static List<String> readAllLines() {
        Path path = Paths.get("src/main/resources/attendances.csv");
        Charset charset = StandardCharsets.UTF_8;
        try {
            return Files.readAllLines(path, charset);
        } catch (IOException e) {
            throw new IllegalStateException("[ERROR] 파일을 읽을 수 없습니다.");
        }
    }
}
