package attendance.view;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class DataFileReader {
    private static final String FILE_PATH = "src/main/resources/attendances.csv";
    private static final int FILE_HEADER_LINE_COUNT = 1;

    public static List<String> readFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            return reader.lines()
                    .skip(FILE_HEADER_LINE_COUNT)
                    .collect(Collectors.toList());
        } catch (FileNotFoundException e) {
            throw new RuntimeException("파일이 존재하지 않습니다.", e);
        } catch (IOException e) {
            throw new RuntimeException("파일 읽기에 실패했습니다.", e);
        }
    }
}
