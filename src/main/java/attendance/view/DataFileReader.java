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
        List<String> datas = new ArrayList<>();
        BufferedReader bufferedReader = createReader();
        readLine(bufferedReader);
        String input;
        while ((input = readLine(bufferedReader)) != null) {
            datas.add(input);
        }
        return datas;
    }

    private static BufferedReader createReader() {
        try {
            return new BufferedReader(new FileReader(FILE_PATH));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("파일을 찾을 수 없습니다.", e);
        }
    }

    private static String readLine(BufferedReader bufferedReader) {
        try {
            return bufferedReader.readLine();
        } catch (IOException e) {
            throw new RuntimeException("입출력 오류 발생", e);
        }
    }
}
