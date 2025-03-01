package model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class StudentAttendanceHistoryTest {
    StudentAttendanceHistory studentAttendanceHistoryMap;

    @BeforeEach
    void set() {
        studentAttendanceHistoryMap = new StudentAttendanceHistory(
                Map.of(
                        new AttendanceDate(LocalDate.of(2024, 12, 12)), new AttendanceTime(LocalTime.of(8, 0))
                )
        );
    }

    @Test
    @DisplayName("출석 요일, 출석 시간 추가 기능 테스트")
    void test1() {
        studentAttendanceHistoryMap.addStudentAttendanceHistory(
                new AttendanceDate(LocalDate.of(2024, 12, 13)),
                new AttendanceTime(LocalTime.of(10, 31))
        );
        Assertions.assertTrue(
                studentAttendanceHistoryMap.getAttendanceHistory().containsKey(new AttendanceDate(LocalDate.of(2024, 12, 13)))
        );
    }

    @Test
    @DisplayName("출석 요일, 출석 시간 수정 기능 테스트")
    void test2() {
        studentAttendanceHistoryMap.modifyStudentAttendanceHistory(
                new AttendanceDate(LocalDate.of(2024, 12, 12)),
                new AttendanceTime(LocalTime.of(9, 0))
        );

        Assertions.assertTrue(studentAttendanceHistoryMap.getAttendanceHistory().containsKey(
                new AttendanceDate(LocalDate.of(2024, 12, 12))
        ));

        Assertions.assertEquals(studentAttendanceHistoryMap.getAttendanceHistory().get(
                new AttendanceDate(LocalDate.of(2024, 12, 12))
        ), new AttendanceTime(LocalTime.of(9, 0)));
    }

}
