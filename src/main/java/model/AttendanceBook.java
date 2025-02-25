package model;

import java.util.List;

public class AttendanceBook {
    private final List<Student> students;

    public AttendanceBook(List<Student> students) {
        this.students = students;
    }

    public void notExistStudent(String studentName) {
        if (findStudentByName(studentName) == null) {
            throw new IllegalArgumentException("[ERROR] 등록되지 않은 닉네임입니다.");
        }
    }

    public Student findStudentByName(String name) {
        return students.stream()
                .filter(s -> s.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    public List<Student> getStudents() {
        return students;
    }
}
