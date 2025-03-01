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

    @Test
    @DisplayName("키 값 보유 테스트")
    void test3() {
        Assertions.assertTrue(
                studentAttendanceHistoryMap.isExistSameAttendanceDate(new AttendanceDate(LocalDate.of(
                        2024, 12, 12
                )))
        );
    }

    @Test
    @DisplayName("날짜를 입력 받고, 해당 날짜의 입실 시간 리턴하는 메서드 테스트")
    void test4() {
        Assertions.assertEquals(
                studentAttendanceHistoryMap.findAttendanceTimeByAttendanceDate(new AttendanceDate(LocalDate.of(2024, 12, 12))),
                new AttendanceTime(LocalTime.of(8, 0))
        );
    }

}
