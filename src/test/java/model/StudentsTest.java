package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
        Assertions.assertTrue(
                students.findStudentByName(studentName).equlas(student)
        );
    }

    @Test
    @DisplayName("같은 이름의 학생을 찾고, 새로운 출석 기록 삽입 메서드 테스트")
    void test2() {
        String studentName = "이든";
        students.addAttendanceDateTime(addAttendanceDateTime);
        Assertions.assertTrue(
                students.findStudentByName(studentName).isExistSameAttendanceDateTime(addAttendanceDateTime)
        );
    }

    @Test
    @DisplayName("같은 이름의 학생을 찾고, 수정하고 싶은 기록 삽입 메서드 테스트")
    void test3() {
        String studentName = "이든";
        students.modifyDateTime(addAttendanceDateTime);
        Assertions.assertTrue(
                students.findStudentByName(studentName).isExistSameAttendanceDateTime(addAttendanceDateTime)
        );
    }

    @Test
    @DisplayName("같은 이름의 학생을 찾고, 수정하고 싶은 날짜와 같은 기록을 삭제하는 메서드 테스트")
    void test4() {
        String studentName = "이든";
        students.modifyDateTime(addAttendanceDateTime);
        Assertions.assertFalse(
                students.findStudentByName(studentName).isExistSameAttendanceDateTime(
                        LocalDateTime.of(2024, 12, 12, 12, 12)
                )
        );
    }
}
