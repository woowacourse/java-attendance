package model;

import Constant.DateFormatInformation;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class StudentRepository {
    private final List<Student> students = new ArrayList<>();

    public List<Student> getStudents() {
        return students;
    }

    public void createStudent(ArrayList<String> fileInformation) {
        for (String information : fileInformation) {
            String[] studentNameAndAttendanceTime = information.split(",");
            createStudentByName(studentNameAndAttendanceTime);
        }
    }

    public void notExistStudent(String studentName) {
        if (findStudentByName(studentName) == null){
            throw new IllegalArgumentException("[ERROR] 등록되지 않은 닉네임입니다.");
        }
    }

    public Student findStudentByName(String name) {
        return students.stream()
                .filter(s -> s.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    private void createStudentByName(String[] studentNameAndAttendanceTime) {
        String name = studentNameAndAttendanceTime[0];
        String timeInformation = studentNameAndAttendanceTime[1];
        if (findStudentByName(name) == null) {
            Student student = new Student(name);
            students.add(student);
            makeDateTimeFormatAndUpdateStudentState(student, timeInformation);
            return;
        }
        Student student = findStudentByName(name);
        makeDateTimeFormatAndUpdateStudentState(student, timeInformation);
    }

    private static void makeDateTimeFormatAndUpdateStudentState(Student student, String studentNameAndAttendanceTime) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DateFormatInformation.LOCAL_DATE_TIME_FORMATTER);
        LocalDateTime localDateTime = LocalDateTime.parse(studentNameAndAttendanceTime, dateTimeFormatter);
        student.updateState(localDateTime);
    }

}
