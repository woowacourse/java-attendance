package util;

import constant.DateFormatInformation;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import model.Student;

public class FileInput {

    private final String filePath = "src/main/resources/attendances.csv";
    private final BufferedReader fileBr;

    public FileInput() throws IOException {
        fileBr = new BufferedReader(new FileReader(filePath));
    }

    public List<Student> createStudents() throws IOException {
        List<String> readAttendanceFile = readAttendanceFile();
        List<Student> students = new ArrayList<>();

        for (String information : readAttendanceFile) {
            String[] studentNameAndAttendanceTime = information.split(",");
            createStudentByName(students, studentNameAndAttendanceTime);
        }
        return students;
    }

    private List<String> readAttendanceFile() throws IOException{
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

    private void createStudentByName(List<Student> students, String[] studentNameAndAttendanceTime) {
        String name = studentNameAndAttendanceTime[0];
        String timeInformation = studentNameAndAttendanceTime[1];
        if (findStudentByName(students, name) == null) {
            Student student = new Student(name);
            students.add(student);
            makeDateTimeFormatAndUpdateStudentState(student, timeInformation);
            return;
        }
        Student student = findStudentByName(students, name);
        makeDateTimeFormatAndUpdateStudentState(student, timeInformation);
    }
    private Student findStudentByName(List<Student> students, String name){
        return students.stream().filter(s -> s.getName().equals(name)).findFirst().orElse(null);
    }

    private static void makeDateTimeFormatAndUpdateStudentState(Student student, String timeInformation) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DateFormatInformation.LOCAL_DATE_TIME_FORMATTER);
        LocalDateTime localDateTime = LocalDateTime.parse(timeInformation, dateTimeFormatter);
        student.getAttendanceRecords().createAttendanceRecords(localDateTime);
    }


}
