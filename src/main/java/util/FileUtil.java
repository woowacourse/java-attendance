package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class FileUtil {
    public static List<String> readlines(String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            return br.lines()
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new IllegalArgumentException("파일을 읽는데 실패했습니다.");
        }
    }
}
