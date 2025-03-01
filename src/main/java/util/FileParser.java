package util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class FileParser {

    public static List<String> readLines(String resourcePath) {
        InputStream inputStream = FileParser.class.getClassLoader().getResourceAsStream(resourcePath);

        if (inputStream == null) {
            throw new IllegalArgumentException("파일을 찾을 수 없습니다.");
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            return reader.lines()
                    .skip(1)
                    .toList();
        } catch (Exception e) {
            throw new IllegalArgumentException("파일을 읽는 중 오류가 발생했습니다.");
        }
    }
}
