package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class StudentRepository {
    List<Student> students = new ArrayList<>();

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
            if (findStudentByName(studentNameAndAttendanceTime[0]) == null) {
                Student student = new Student(studentNameAndAttendanceTime[0]);
                addStudent(student);
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                LocalDateTime localDateTime = LocalDateTime.parse(studentNameAndAttendanceTime[1], dateTimeFormatter);
                student.updateState(localDateTime);
                continue;
            }
            Student student = findStudentByName(studentNameAndAttendanceTime[0]);
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime localDateTime = LocalDateTime.parse(studentNameAndAttendanceTime[1], dateTimeFormatter);
            student.updateState(localDateTime);
        }
    }

}
