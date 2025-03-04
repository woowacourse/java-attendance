package model;

import java.time.LocalDate;
import java.time.LocalTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceStatusEvaluatorTest {
    @Test
    @DisplayName("월요일과 시간을 받아 출석 판단하는 메서드 테스트")
    void test1() {
        Assertions.assertThat(AttendanceStatusEvaluator.calculateAttendanceStatus(
                        new AttendanceDate(LocalDate.of(2024,12,2))
                        , new AttendanceTime(LocalTime.of(13,0))))
                .isEqualTo(AttendanceStatus.ATTENDANCE);
    }

    @Test
    @DisplayName("월요일과 시간을 받아 결석 판단하는 메서드 테스트")
    void test2() {
        Assertions.assertThat(AttendanceStatusEvaluator.calculateAttendanceStatus(
                        new AttendanceDate(LocalDate.of(2024,12,2))
                        ,
                        new AttendanceTime(LocalTime.of(13,59))
                        ))
                .isEqualTo(AttendanceStatus.ABSENT);
    }

    @Test
    @DisplayName("월요일과 시간을 받아 지각을 판단하는 메서드 테스트")
    void test3() {
        Assertions.assertThat(AttendanceStatusEvaluator.calculateAttendanceStatus(
                        new AttendanceDate(LocalDate.of(2024,12,2))
                        ,
                        new AttendanceTime(LocalTime.of(13,6))
                        ))
                .isEqualTo(AttendanceStatus.LATE);
    }@Test
    @DisplayName("화요일과 시간을 받아 출석 판단하는 메서드 테스트")
    void test4() {
        Assertions.assertThat(AttendanceStatusEvaluator.calculateAttendanceStatus(
                        new AttendanceDate(LocalDate.of(2024,12,3))
                        ,
                        new AttendanceTime(LocalTime.of(10,0))
                        ))
                .isEqualTo(AttendanceStatus.ATTENDANCE);
    }@Test
    @DisplayName("화요일과 시간을 받아 결석 판단하는 메서드 테스트")
    void test5() {
        Assertions.assertThat(AttendanceStatusEvaluator.calculateAttendanceStatus(
                        new AttendanceDate(LocalDate.of(2024,12,3))
                        ,
                        new AttendanceTime(LocalTime.of(13,59))
                        ))
                .isEqualTo(AttendanceStatus.ABSENT);
    }@Test
    @DisplayName("화요일과 시간을 받아 지각을 판단하는 메서드 테스트")
    void test6() {
        Assertions.assertThat(AttendanceStatusEvaluator.calculateAttendanceStatus(
                        new AttendanceDate(LocalDate.of(2024,12,3))
                        ,
                        new AttendanceTime(LocalTime.of(10,6))
                        ))
                .isEqualTo(AttendanceStatus.LATE);
    }@Test
    @DisplayName("0시 0분에 입실한 학생은 결석 처리하는 메서드 테스트")
    void test7() {
        Assertions.assertThat(AttendanceStatusEvaluator.calculateAttendanceStatus(
                        new AttendanceDate(LocalDate.of(2024,12,3))
                        ,
                        new AttendanceTime(LocalTime.of(0,0))
                        ))
                .isEqualTo(AttendanceStatus.ABSENT);
    }
}
