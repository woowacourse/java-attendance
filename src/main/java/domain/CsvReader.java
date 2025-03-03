package domain;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CsvReader {

    private static final String CSV_DELIMITER = ",";

    private CsvReader() {
    }

    public static List<String> readFile(String filePath) {
        URL fileURL = createURL(filePath);
        isExistFileURL(fileURL);
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(fileURL.toURI()))) {
            return new ArrayList<>(reader.lines().toList());
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

    public static void removeFirstRow(List<String> rows) {
        rows.removeFirst();
    }

    public static List<String> splitRow(String row) {
        List<String> split = List.of(row.split(CSV_DELIMITER));
        isNotMatchRowFormat(split);
        return split;
    }

    private static void isNotMatchRowFormat(final List<String> split) {
        if (split.size() != 2) {
            throw new IllegalStateException("행 구조가 잘못되었습니다");
        }
    }
}
