package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import model.StudentAttendanceRecord;

public class FileInput {
    private static final String filePath = "src/main/resources/attendances.csv";
    private static BufferedReader fileBr = null;

    public FileInput() throws IOException {
        fileBr = new BufferedReader(new FileReader(filePath));
    }

    public static ArrayList<String> readAttendanceFile() throws IOException{
        ArrayList<String> attendanceFile = new ArrayList<>();
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

    public static StudentAttendanceRecord readFileAndCreateStudentRepository() throws IOException {
        FileInput fileInput = new FileInput();
        StudentAttendanceRecord studentRecordRepository = new StudentAttendanceRecord();
        for (String information : readAttendanceFile()) {
            String[] nameAndTimeInformation = information.split(",");
            String name = nameAndTimeInformation[0];
            String timeInformation = nameAndTimeInformation[1];
            String localDateTimeFormatter = "yyyy-MM-dd HH:mm";
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(localDateTimeFormatter);
            LocalDateTime localDateTime = LocalDateTime.parse(timeInformation, dateTimeFormatter);
            studentRecordRepository.addRecord(name,localDateTime);
        }
        return studentRecordRepository;
    }

    public static StudentAttendanceRecord createStudentRepository(){
        try{
            return readFileAndCreateStudentRepository();
        } catch (IOException e){
            System.out.println(e.getMessage());
            return createStudentRepository();
        }
    }

}
