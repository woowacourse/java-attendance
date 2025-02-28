package model;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class StudentTest {
    AttendanceDateTime addAttendanceDateTime;
    AttendanceDateTime deleteAttendanceDateTime;
    StudentAttendanceHistory studentAttendanceHistory;

    @BeforeEach
    void set() {
        addAttendanceDateTime = new AttendanceDateTime(LocalDateTime.of(2024, 12, 12, 12, 12));
        deleteAttendanceDateTime = new AttendanceDateTime(LocalDateTime.of(2024, 12, 13, 12, 12));
        studentAttendanceHistory = new StudentAttendanceHistory(
                List.of(new AttendanceDateTime(LocalDateTime.of(2024, 12, 12, 12, 12)),
                        new AttendanceDateTime(LocalDateTime.of(2024, 12, 13, 12, 12)))
        );
        Student student = new Student("이든", studentAttendanceHistory);
    }

    @Test
    @DisplayName("새로운 출석 정보를 저장하는 메서드 테스트")
    void test1() {
        student.addAttendanceDateTime(addAttendanceDateTime);
        Assertions.assertTrue(
                student.isExistSameDay(addAttendanceDateTime)
        );
    }

    @Test
    @DisplayName("수정하고 싶은 날짜의 일자를 비교하여 삭제하는 메서드 테스트")
    void test2() {
        student.modifyAttendanceDateTime(deleteAttendanceDateTime);
        Assertions.assertFalse(
                student.isExistSameDay(deleteAttendanceDateTime)
        );
    }

    @Test
    @DisplayName("수정하고 싶은 날짜의 일자를 저장하는 메서드 테스트")
    void test3() {
        student.modifyAttendanceDateTime(deleteAttendanceDateTime);
        Assertions.assertTrue(
                student.isExistSameDay(deleteAttendanceDateTime)
        );
    }
}
