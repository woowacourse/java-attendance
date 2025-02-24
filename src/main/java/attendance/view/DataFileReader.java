package attendance.view;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataFileReader {
    private static final String FILE_PATH = "src/main/resources/attendances.csv";

    public static List<String> read() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_PATH))) {
            return readLines(bufferedReader);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("데이터 파일을 찾을 수 없습니다.", e);
        } catch (IOException e) {
            throw new RuntimeException("데이터 입력 도중 오류가 발생했습니다.", e);
        }
    }

    private static List<String> readLines(BufferedReader bufferedReader) {
        List<String> datas = new ArrayList<>();
        bufferedReader.lines()
                .skip(1)
                .forEach(data -> datas.add(data));
        return datas;
    }
}
