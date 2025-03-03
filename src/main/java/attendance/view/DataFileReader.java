package attendance.view;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class DataFileReader {
    public static List<String> readFile(String path, int skipLineCount) {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            return reader.lines()
                    .skip(skipLineCount)
                    .collect(Collectors.toList());
        } catch (FileNotFoundException e) {
            throw new RuntimeException("파일이 존재하지 않습니다.", e);
        } catch (IOException e) {
            throw new RuntimeException("파일 읽기에 실패했습니다.", e);
        }
    }
}
