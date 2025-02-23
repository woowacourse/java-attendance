package domain;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AttendanceFileReader {

    public static List<String> readFile(String fileName) {
        List<String> studentList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                studentList.add(line);
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("[ERROR] 출석 데이터 파일을 읽는데 실패했습니다.");
        }
        return studentList;
    }
}
