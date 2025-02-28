package domain;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CrewAttendanceFileReader {
    public static List<String> readFile(String filePath) {
        List<String> crewAttendance = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            bufferedReader.readLine();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                crewAttendance.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return crewAttendance;
    }
}
