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
    private static final String filePath = "src/main/resources/attendances.csv";
    private static BufferedReader fileBr = null;

    public FileInput() throws IOException {
        fileBr = new BufferedReader(new FileReader(filePath));
    }

    public static List<String> readAttendanceFile() throws IOException{
        List<String> attendanceFile = new ArrayList<>();
        fileBr.readLine();
        while(true) {
            String information = fileBr.readLine();
            if (information == null) {
                break;
            }
            attendanceFile.add(information);
        }
        return attendanceFile;
    }

    public static Map<String, List<LocalDateTime>> readFileAndCreateStudentRepository() throws IOException {
        FileInput fileInput = new FileInput();
        Map<String, List<LocalDateTime>> studentInformation = new HashMap<>();
        for (String information : readAttendanceFile()) {
            String[] nameAndTimeInformation = information.split(",");
            String name = nameAndTimeInformation[0];
            String timeInformation = nameAndTimeInformation[1];
            String localDateTimeFormatter = "yyyy-MM-dd HH:mm";
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(localDateTimeFormatter);
            LocalDateTime localDateTime = LocalDateTime.parse(timeInformation, dateTimeFormatter);
            if (!studentInformation.containsKey(name)) {
                studentInformation.put(name, new ArrayList<>());
                studentInformation.get(name).add(localDateTime);
                continue;
            }
            studentInformation.get(name).add(localDateTime);
        }
        return studentInformation;
    }

    public static Map<String, List<LocalDateTime>> createStudentRepository(){
        try{
            return readFileAndCreateStudentRepository();
        } catch (IOException e){
            System.out.println(e.getMessage());
            return createStudentRepository();
        }
    }

}
