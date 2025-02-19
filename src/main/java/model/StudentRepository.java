package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class StudentRepository {
    List<Student> students = new ArrayList<>();

    public boolean notExistStudent(String studentName) {
        return false;
    }

    public Student findStudentByName(String name) {
        for (Student student : students){
            if (student.name.equals(name)){
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
            Student student = new Student(studentNameAndAttendanceTime[0]);
            students.add(student);
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime localDateTime = LocalDateTime.parse(information, dateTimeFormatter);
            student.updateState(localDateTime);
        }
    }

}
