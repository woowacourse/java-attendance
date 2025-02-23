package model;

import constant.DateFormatInformation;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class StudentRepository {
    private final List<Student> students;

    public StudentRepository(List<Student> students) {
        this.students = students;
    }

    public List<Student> getStudents() {
        return students;
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
}
