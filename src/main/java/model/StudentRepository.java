package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class StudentRepository {
    private final List<Student> students = new ArrayList<>();

    public List<Student> getStudents() {
        return students;
    }

    public void notExistStudent(String studentName) {
        if (findStudentByName(studentName) == null){
            throw new IllegalArgumentException("[ERROR] 등록되지 않은 닉네임입니다.");
        }
    }

    public Student findStudentByName(String name) {
        for (Student student : students){
            if (student.getName().equals(name)){
                return student;
            }
        }
        return null;
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void createStudent(ArrayList<String> fileInformation) {
        for (String information : fileInformation) {
            String[] studentNameAndAttendanceTime = information.split(",");
            creatStudentByName(studentNameAndAttendanceTime);
        }
    }

    private void creatStudentByName(String[] studentNameAndAttendanceTime) {
        String name = studentNameAndAttendanceTime[0];
        String timeInformation = studentNameAndAttendanceTime[1];
        String localDateTimeFormatter = "yyyy-MM-dd HH:mm";
        if (findStudentByName(name) == null) {
            Student student = new Student(name);
            addStudent(student);
            makeDateTimeFormatAndUpdateStudentState(localDateTimeFormatter, student,
                    timeInformation);
            return;
        }
        Student student = findStudentByName(name);
        makeDateTimeFormatAndUpdateStudentState(localDateTimeFormatter, student,
                timeInformation);
    }

    private static void makeDateTimeFormatAndUpdateStudentState(String localDateTimeFormatter, Student student, String studentNameAndAttendanceTime) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(localDateTimeFormatter);
        LocalDateTime localDateTime = LocalDateTime.parse(studentNameAndAttendanceTime, dateTimeFormatter);
        student.updateState(localDateTime);
    }

}
