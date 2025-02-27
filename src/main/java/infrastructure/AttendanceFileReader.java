package infrastructure;

import domain.Crews;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class AttendanceFileReader {

    public Crews readFile(String fileName) {
        Crews crews = new Crews();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String nickname = line.split(",")[0];
                String attendTime = line.split(",")[1];
                crews.saveCrew(nickname, attendTime);
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("[ERROR] 출석 데이터 파일을 읽는데 실패했습니다.");
        }
        return crews;
    }
}
