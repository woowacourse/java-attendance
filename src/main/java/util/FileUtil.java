package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FileUtil {

    private FileUtil() {

    }

    public static List<String> readFile(URL fileURL) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(fileURL.toURI()))) {
            return reader.lines().skip(1).toList(); // column name이 존재하는 경우, 제거하기 위함
        } catch (IOException | URISyntaxException e) {
            throw new IllegalStateException(String.format("경로 문제: %s", fileURL.getPath()));
        }
    }
}