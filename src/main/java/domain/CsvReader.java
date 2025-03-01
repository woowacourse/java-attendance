package domain;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class CsvReader {

    private CsvReader() {
    }

    public static List<String> readFile(String filePath) {
        URL fileURL = createURL(filePath);
        isExistFileURL(fileURL);
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(fileURL.toURI()))) {
            return removeFirstRow(reader);
        } catch (IOException | URISyntaxException e) {
            throw new IllegalStateException("파일 경로가 잘못되었습니다: " + fileURL.getPath());
        }
    }

    private static URL createURL(String path) {
        return CsvReader.class.getClassLoader().getResource(path);
    }

    private static void isExistFileURL(final URL fileURL) {
        if (fileURL == null) {
            throw new IllegalStateException("파일 경로가 잘못되었습니다");
        }
    }

    private static List<String> removeFirstRow(BufferedReader reader) {
        return reader.lines().skip(1).toList();
    }
}
