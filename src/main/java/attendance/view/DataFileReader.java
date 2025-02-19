package attendance.view;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataFileReader {
    private static final String FILE_PATH = "src/main/resources/attendances.csv";

    public static List<String> read() {
        List<String> datas = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_PATH))) {
            bufferedReader.readLine();
            String input;
            while((input = bufferedReader.readLine()) != null){
                datas.add(input);
            }
        } catch (IOException e) {
            System.out.println("입출력 예외 발생: " + e.getMessage());
        }
        return datas;
    }
}
