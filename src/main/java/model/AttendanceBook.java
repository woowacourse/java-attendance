package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AttendanceBook {
    private final List<Student> attendanceBook;

    public AttendanceBook(List<Student> attendanceBook) {
        this.attendanceBook = attendanceBook;
    }

    public AttendanceBook(Map<String, List<LocalDateTime>> fileAttendanceRecord) {
        attendanceBook = fileAttendanceRecord.entrySet().stream()
                .map(map -> new Student(map.getKey(), map.getValue()))
                .collect(Collectors.toList());
    }


    public LocalTime findStudentAttendanceTimeRecord(String name, LocalDate localDate) {
        Student student = findStudentByNickName(name);
        return student.findAttendanceLocalTimeByLocalDate(localDate);
    }
    public AttendanceStatus findStudentAttendanceStatusRecord(String name, LocalDate localDate) {
        Student student = findStudentByNickName(name);
        return student.findAttendanceStatusByLocalDate(localDate);
    }


    private Student findStudentByNickName(String name){
        return attendanceBook.stream()
                .filter(stu -> stu.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 찾는 학생이 존재하지 않습니다."));
    }

    public List<Student> findExpulsionRiskStudents() {
        return attendanceBook.stream()
                .filter(student -> student.calculateAbsentCount() >= 3)
                .sorted(Comparator.comparing(Student::calculateAbsentCount))
                .collect(Collectors.toList());
    }
}
