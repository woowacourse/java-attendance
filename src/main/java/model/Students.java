package model;

import java.util.List;

public class Students {
    private final List<Student> students;

    public Students(List<Student> students) {
        this.students = students;
    }

    public Student findStudentByName(String studentName) {
        return students.stream()
                .filter(student -> student.isSameName(studentName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 존재하지 않는 학생의 이름입니다."));
    }
}
