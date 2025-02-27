package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileInput {

    private FileInput() {}

    private static final String FILE_PATH = "src/main/resources/attendances.csv";

    public static List<String> readAttendanceFile() {
        List<String> attendanceFile = new ArrayList<>();
        try (BufferedReader fileBr = new BufferedReader(new FileReader(FILE_PATH))) {
            fileBr.readLine();
            String information;
            while ((information = fileBr.readLine()) != null) {
                attendanceFile.add(information);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println("[ERROR] 파일 읽는 중 오류가 발생하였습니다.");
        }
        return attendanceFile;
    }

    public static Map<String, List<LocalDateTime>> readFileAndCreateStudentRepository(){
        Map<String, List<LocalDateTime>> studentInformation = new HashMap<>();
        for (String information : readAttendanceFile()) {
            String[] nameAndTimeInformation = information.split(",");
            String name = nameAndTimeInformation[0];
            String timeInformation = nameAndTimeInformation[1];
            String localDateTimeFormatter = "yyyy-MM-dd HH:mm";
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(localDateTimeFormatter);
            LocalDateTime localDateTime = LocalDateTime.parse(timeInformation, dateTimeFormatter);
            studentInformation.computeIfAbsent(name, k -> new ArrayList<>()).add(localDateTime);
        }
        return studentInformation;
    }
}
