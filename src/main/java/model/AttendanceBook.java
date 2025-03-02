package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AttendanceBook {
    private static final int LATE_COUNT_FOR_ONE_ABSENCE = 3;
    private final List<Student> students;

    public AttendanceBook(List<Student> attendanceBook) {
        this.students = attendanceBook;
    }

    public AttendanceBook(Map<String, List<LocalDateTime>> fileAttendanceRecord) {
        students = fileAttendanceRecord.entrySet().stream()
                .map(entry -> new Student(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    public void updateNonExistentAttendanceRecords(LocalDate today){
        for (Student student : students){
            student.updateNonAttendanceRecordStatusIsAbsent(today);
        }
    }

    public LocalTime findStudentAttendanceTimeRecord(String name, LocalDate localDate) {
        Student student = findStudentByNickName(name);
        return student.findAttendanceLocalTimeByLocalDate(localDate);
    }

    public AttendanceStatus findStudentAttendanceStatusRecord(String name, LocalDate localDate) {
        Student student = findStudentByNickName(name);
        return student.findAttendanceStatusByLocalDate(localDate);
    }

    public Student findStudentByNickName(String name){
        return students.stream()
                .filter(stu -> stu.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 찾는 학생이 존재하지 않습니다."));
    }

    public List<Student> findExpulsionRiskStudents() {
        return students.stream()
                .filter(student -> student.calculateTotalAbsentCount() >= LATE_COUNT_FOR_ONE_ABSENCE)
                .sorted(Comparator.comparing(Student::calculateTotalAbsentCount))
                .collect(Collectors.toList());
    }
}
