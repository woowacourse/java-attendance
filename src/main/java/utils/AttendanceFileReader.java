package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AttendanceFileReader {

    private static final String PATH = "src/main/resources/attendances.csv";

    public static List<String> readCrewAttendances() {

        List<String> fileReadResult = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(PATH))) {
            String readResult;
            bufferedReader.readLine();
            while ((readResult = bufferedReader.readLine()) != null) {
                fileReadResult.add(readResult);
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("존재하지 않는 파일입니다.");
        }
        return fileReadResult;
    }

}
