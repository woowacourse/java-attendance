package model;

import java.time.LocalDateTime;
import java.time.LocalTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class AttendanceCalculatorTest {
    @Test
    @DisplayName("월요일과 시간을 받아 출석 판단하는 메서드 테스트")
    void test1() {
        Assertions.assertThat(AttendanceCalculator.calculateAttendance(
                new AttendanceDateTime(LocalDateTime.of(2024,12,2,13,0))
                , LocalTime.of(13,0)))
                .isEqualTo(AttendanceStatus.ATTENDANCE);
    }

    @Test
    @DisplayName("월요일과 시간을 받아 결석 판단하는 메서드 테스트")
    void test2() {
        Assertions.assertThat(AttendanceCalculator.calculateAttendance(
                new AttendanceDateTime(LocalDateTime.of(2024,12,2,13,0))
                ,
                        LocalTime.of(13,59)))
                .isEqualTo(AttendanceStatus.ABSENT);
    }

    @Test
    @DisplayName("월요일과 시간을 받아 지각을 판단하는 메서드 테스트")
    void test3() {
        Assertions.assertThat(AttendanceCalculator.calculateAttendance(
                new AttendanceDateTime(LocalDateTime.of(2024,12,2,13,0))
                ,
                        LocalTime.of(13,6)))
                .isEqualTo(AttendanceStatus.LATE);
    }@Test
    @DisplayName("화요일과 시간을 받아 출석 판단하는 메서드 테스트")
    void test4() {
        Assertions.assertThat(AttendanceCalculator.calculateAttendance(
                new AttendanceDateTime(LocalDateTime.of(2024,12,3,13,0))
                ,
                        LocalTime.of(10,0)))
                .isEqualTo(AttendanceStatus.ATTENDANCE);
    }@Test
    @DisplayName("화요일과 시간을 받아 결석 판단하는 메서드 테스트")
    void test5() {
        Assertions.assertThat(AttendanceCalculator.calculateAttendance(
                new AttendanceDateTime(LocalDateTime.of(2024,12,3,13,0))
                ,
                        LocalTime.of(13,59)))
                .isEqualTo(AttendanceStatus.ABSENT);
    }@Test
    @DisplayName("화요일과 시간을 받아 지각을 판단하는 메서드 테스트")
    void test6() {
        Assertions.assertThat(AttendanceCalculator.calculateAttendance(
                new AttendanceDateTime(LocalDateTime.of(2024,12,3,13,0))
                ,
                        LocalTime.of(10,6)))
                .isEqualTo(AttendanceStatus.LATE);
    }@Test
    @DisplayName("0시 0분에 입실한 학생은 결석 처리하는 메서드 테스트")
    void test7() {
        Assertions.assertThat(AttendanceCalculator.calculateAttendance(
                new AttendanceDateTime(LocalDateTime.of(2024,12,3,13,0))
                ,
                        LocalTime.of(0,0)))
                .isEqualTo(AttendanceStatus.ABSENT);
    }
    @ParameterizedTest
    @DisplayName("주말 및 공휴일을 검사하는 메서드 테스트")
    @CsvSource({
            "2024, 12, 25, true",
            "2024, 12, 14, true",
            "2024, 12, 15, true",
            "2024, 12, 16, false"
    })
    void testCheckHoliday(int year, int month, int day, boolean expected) {
        LocalDateTime date = LocalDateTime.of(year, month, day, 0, 0);
        org.junit.jupiter.api.Assertions.assertEquals(expected, AttendanceCalculator.checkHoliday(new AttendanceDateTime(date)));
    }

}