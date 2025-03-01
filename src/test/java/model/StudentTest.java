package model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class StudentTest {
    AttendanceDate addAttendanceDate;
    AttendanceDate deleteAttendanceDate;
    AttendanceTime addAttendanceTime = new AttendanceTime(LocalTime.of(8, 1));
    StudentAttendanceHistory studentAttendanceHistory;
    Student student;

    @BeforeEach
    void set() {
        addAttendanceDate = new AttendanceDate(LocalDate.of(2024, 12, 12));
        deleteAttendanceDate = new AttendanceDate(LocalDate.of(2024, 12, 13));
        studentAttendanceHistory = new StudentAttendanceHistory(
                Map.of(
                        new AttendanceDate(LocalDate.of(2024, 12, 12)), new AttendanceTime(LocalTime.of(8, 0))
                ) );
        student = new Student("이든", studentAttendanceHistory);
    }

    @Test
    @DisplayName("새로운 출석 정보를 저장하는 메서드 테스트")
    void test1() {
        student.addAttendanceDateTime(addAttendanceDate, addAttendanceTime);
        Assertions.assertTrue(
                student.isExistAttendanceDate(addAttendanceDate)
        );
    }

    @Test
    @DisplayName("수정하고 싶은 날짜의 일자를 비교하여 삭제하는 메서드 테스트")
    void test2() {
        student.modifyAttendanceDateTime(addAttendanceDate, addAttendanceTime);

        Assertions.assertEquals(student.getStudentAttendanceHistory().getAttendanceHistory()
                .get(new AttendanceDate(LocalDate.of(2024, 12, 12))), new AttendanceTime(LocalTime.of(8, 1)));
    }

    @Test
    @DisplayName("수정하고 싶은 날짜의 일자를 저장하는 메서드 테스트")
    void test3() {
        student.modifyAttendanceDateTime(addAttendanceDate, addAttendanceTime);
        Assertions.assertTrue(
                student.isExistAttendanceDate(addAttendanceDate)
        );
    }

    @Test
    @DisplayName("수정하고 싶은 날짜의 일자를 저장하는 메서드 테스트")
    void test4() {
        student.modifyAttendanceDateTime(addAttendanceDate, addAttendanceTime);
        Assertions.assertEquals(student.getStudentAttendanceHistory().getAttendanceHistory().get(addAttendanceDate),
                addAttendanceTime);
    }

    @Test
    @DisplayName("이름이 같은지 확이하는 메서드 테스트")
    void test5() {
        Assertions.assertTrue(
                student.isSameName("이든")
        );
    }
}
