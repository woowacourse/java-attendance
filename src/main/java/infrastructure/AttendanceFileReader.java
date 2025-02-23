package infrastructure;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AttendanceFileReader {

    public List<String> readFile(String fileName) {
        List<String> rawCrews = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                rawCrews.add(line);
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("[ERROR] 출석 데이터 파일을 읽는데 실패했습니다.");
        }
        return rawCrews;
    }
}
