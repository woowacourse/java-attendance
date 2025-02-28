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
import model.AttendanceDateTime;

public class FileInput {

    private FileInput() {}

    private static final String FILE_PATH = "src/main/resources/attendances.csv";
    private static final String STUDENT_INFORMATION_REGEX = "[가-힣]+,\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}";

    public static Map<String, List<AttendanceDateTime>> readFileAndCreateStudentRepository(){
        Map<String, List<AttendanceDateTime>> studentInformation = new HashMap<>();
        try {
            for (String information : readAttendanceFile()) {
                if (!information.matches(STUDENT_INFORMATION_REGEX)) {
                    throw new IllegalArgumentException("[ERROR] 잘못된 파일 양식입니다.");
                }
                String[] nameAndTimeInformation = information.split(",");
                String name = nameAndTimeInformation[0];
                String timeInformation = nameAndTimeInformation[1];
                String localDateTimeFormatter = "yyyy-MM-dd HH:mm";
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(localDateTimeFormatter);
                LocalDateTime localDateTime = LocalDateTime.parse(timeInformation, dateTimeFormatter);
                studentInformation.computeIfAbsent(name, k -> new ArrayList<>()).add(new AttendanceDateTime(localDateTime));
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            throw new IllegalArgumentException();
        }
        return studentInformation;
    }

    private static List<String> readAttendanceFile() {
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
            throw new IllegalArgumentException();
        }
        return attendanceFile;
    }
}
