package model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class StudentsTest {
    Students students;

    StudentAttendanceHistory studentAttendanceHistory;
    Student student;

    List<Student> studentList;

    @BeforeEach
    void set() {
        studentAttendanceHistory = new StudentAttendanceHistory(
                Map.of(
                        new AttendanceDate(LocalDate.of(2024, 12, 12)), new AttendanceTime(LocalTime.of(8, 0))
                )  );
        student = new Student("이든", studentAttendanceHistory);
        studentList = List.of(student);
        students = new Students(studentList);
    }

    @Test
    @DisplayName("같은 이름의 학생 찾기")
    void test1() {
        String studentName = "이든";
        Assertions.assertTrue(students.isExistStudent(studentName));
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

    @Test
    @DisplayName("파일에 없는 내역 업데이트 하는 테스트")
    void test3() {
        String studentName = "이든";
        AttendanceDate today = new AttendanceDate(LocalDate.of(2024, 12, 12));

        AttendanceDate start = new AttendanceDate(LocalDate.of(2024, 12, 1));

        students.updateMissingAttendanceRecords(start, today);

        Assertions.assertTrue(
                students.findStudentByName(studentName).getStudentAttendanceHistory().isExistSameAttendanceDate(
                        new AttendanceDate(LocalDate.of(2024, 12, 4))
                )
        );

        Assertions.assertEquals(students.findStudentByName(studentName).findAttendanceTimeByAttendanceDate(
                        new AttendanceDate(LocalDate.of(2024, 12, 4))
                ),
                new AttendanceTime(LocalTime.of(0, 0)));
    }

    @Test
    @DisplayName("학생의 이름과 일자를 받아 이미 출석한 요일인지 확인하는 메서드 테스트")
    void test4() {
        String name = "이든";
        AttendanceDate attendanceDate = new AttendanceDate(LocalDate.of(2024, 12, 12));
        org.assertj.core.api.Assertions.assertThatThrownBy(
                () -> students.validateAlreadyExistAttendanceDate(name, attendanceDate)
        )
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 이미 출석한 요일입니다. 다시 출석하고 싶으면 수정 기능을 이용해 주세요.");
    }

    @Test
    @DisplayName("학생 이름, 일자, 시각을 받아 출석부에 업데이트 하는 메서드 테스트")
    void test5() {
        String name = "이든";

        AttendanceDate attendanceDate = new AttendanceDate(LocalDate.of(2024, 12, 13));
        AttendanceTime attendanceTime = new AttendanceTime(LocalTime.of(12, 0));

        students.addAttendanceDateTime(name, attendanceDate, attendanceTime);

        Assertions.assertEquals(students.findStudentByName(name).getStudentAttendanceHistory().getAttendanceHistory()
                .get(attendanceDate), attendanceTime);
    }

    @Test
    @DisplayName("학생 이름, 일자, 시각을 받아 수정하는 메서드 테스트")
    void test6() {
        String name = "이든";

        AttendanceDate attendanceDate = new AttendanceDate(LocalDate.of(2024, 12, 12));
        AttendanceTime attendanceTime = new AttendanceTime(LocalTime.of(10, 0));

        students.modifyAttendanceDateTime(name, attendanceDate, attendanceTime);

        Assertions.assertEquals(students.findStudentByName(name).getStudentAttendanceHistory().getAttendanceHistory()
                .get(attendanceDate), attendanceTime);    }
}
