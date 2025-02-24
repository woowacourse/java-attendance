package file;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AttendanceFileReader {

    public static List<String> readFile(String fileName) {
        List<String> studentList = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            br.readLine();
            addLine(br, studentList);
        } catch (IOException e) {
            throw new RuntimeException("파일을 읽어오는 과정에서 문제가 생겼습니다.");
        }
        return studentList;
    }

    private static void addLine(BufferedReader br, List<String> studentList) throws IOException {
        String line;
        while ((line = br.readLine()) != null) {
            studentList.add(line);
        }
    }
}
