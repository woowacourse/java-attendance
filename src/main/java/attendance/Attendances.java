package attendance;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Attendances {

    public void readAttendancesFile(String filePath) {
        try{
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            do {
                String input = br.readLine();
                if (input == null) {
                    break;
                }
                System.out.println(input);
            } while (true);
        } catch (IOException e) {
            throw new IllegalArgumentException("파일을 읽어올 수 없습니다.");
        }
    }
}
