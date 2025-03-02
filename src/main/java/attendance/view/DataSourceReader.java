package attendance.view;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class DataSourceReader {
    private static final String FILE_PATH = "src/main/resources/attendances.csv";

    public static List<String> readFile() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_PATH))) {
            return bufferedReader.lines().skip(1).toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
