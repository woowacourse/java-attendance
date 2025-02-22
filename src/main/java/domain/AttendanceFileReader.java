package domain;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AttendanceFileReader {

    public static List<String> readFile(String fileName) {
        List<String> studentList = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                studentList.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException("파일을 읽어오는 과정에서 문제가 생겼습니다.");
        }
        return studentList;
    }
}
