package model;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class StudentsTest {
    Students students;

    StudentAttendanceHistory studentAttendanceHistory;
    Student student;

    List<Student> studentList;
    AttendanceDateTime addAttendanceDateTime = new AttendanceDateTime(LocalDateTime.of(2024, 12, 12, 13, 13));

    @BeforeEach
    void set() {
        studentAttendanceHistory = new StudentAttendanceHistory(
                List.of(new AttendanceDateTime(LocalDateTime.of(2024, 12, 12, 12, 12)),
                        new AttendanceDateTime(LocalDateTime.of(2024, 12, 13, 12, 12)))
        );
        student = new Student("이든", studentAttendanceHistory);
        studentList = List.of(student);
        students = new Students(studentList);
    }

    @Test
    @DisplayName("같은 이름의 학생 찾기")
    void test1() {
        String studentName = "이든";
        Assertions.assertEquals(students.findStudentByName(studentName), student);
    }

    @Test
    @DisplayName("없는 이름을 입력하면 예외 발생 테스트")
    void test2() {
        String studentName = "말론";
        org.assertj.core.api.Assertions.assertThatThrownBy(
                () -> students.findStudentByName(studentName)
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 존재하지 않는 학생의 이름입니다.");
    }
}
