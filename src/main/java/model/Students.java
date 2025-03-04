package model;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

public class Students {
    private final List<Student> students;

    public Students(List<Student> students) {
        this.students = new ArrayList<>(students);
    }

    public void validateAlreadyExistAttendanceDate(String studentName, AttendanceDate today) {
        students.stream()
                .filter(student -> student.isSameName(studentName))
                .findFirst()
                .ifPresent(student -> student.validateAlreadyExistAttendanceDate(today));
    }

    public Student findStudentByName(String studentName) {
        return students.stream()
                .filter(student -> student.isSameName(studentName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 존재하지 않는 학생의 이름입니다."));
    }

    public boolean isExistStudent(String studentName) {
        return students.stream()
                .anyMatch(student -> student.isSameName(studentName));
    }

    public void updateMissingAttendanceRecords(AttendanceDate attendanceStartDate, AttendanceDate todayDate) {
        for (Student student : students) {
            student.updateMissingAttendanceRecords(attendanceStartDate, todayDate);
        }
    }

    public List<Student> getStudents() {
        return students;
    }
}
