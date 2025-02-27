package model;

import java.util.Collections;
import java.util.List;

public class Students {

    private final List<Student> students;

    public Students(List<Student> studentRepository) {
        this.students = studentRepository;
    }

    public boolean isExistStudent(String name) {
        return students.stream()
                .anyMatch(student -> student.isSameName(name));
    }

    public Student findStudentByName(String name) {
        return students.stream()
                .filter(student -> student.isSameName(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 존재하지 않는 학생입니다."));
    }

    public void updateMissingAttendanceRecords(TodayDate todayDate) {
        for (Student student : students) {
            student.updateNoInformationInFile(todayDate.getTodayDateTIme());
        }
    }

    public List<Student> getStudents() {
        return Collections.unmodifiableList(students);
    }
}
