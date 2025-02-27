package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceDateTimeTest {
    @Test
    @DisplayName("같은 날인지 비교")
    void test1() {
        AttendanceDateTime attendanceDateTime = new AttendanceDateTime(LocalDateTime.of(2024, 12, 2, 0, 0));
        Assertions.assertTrue(attendanceDateTime.isSameAttendanceDateTime(new AttendanceDateTime(LocalDateTime.of(
                2024, 12, 2, 1, 13
        ))));
    }

    @Test
    @DisplayName("월요일인지 판단")
    void test2() {
        AttendanceDateTime attendanceDateTime = new AttendanceDateTime(LocalDateTime.of(2024, 12, 2, 0, 0));
        Assertions.assertTrue(attendanceDateTime.isMonday());
    }

    @Test
    @DisplayName("월요일이 아닌지 판단")
    void test3() {
        AttendanceDateTime attendanceDateTime = new AttendanceDateTime(LocalDateTime.of(2024, 12, 3, 0, 0));
        Assertions.assertFalse(attendanceDateTime.isMonday());

    }

    @Test
    @DisplayName("주말인지 판단")
    void test4() {
        AttendanceDateTime attendanceDateTime = new AttendanceDateTime(LocalDateTime.of(2024, 12, 1, 0, 0));
        Assertions.assertTrue(attendanceDateTime.isWeekend());
    }

    @Test
    @DisplayName("크리스마스인지 판단")
    void test5() {
        AttendanceDateTime attendanceDateTime = new AttendanceDateTime(LocalDateTime.of(2024, 12, 25, 0, 0));
        Assertions.assertTrue(attendanceDateTime.isChristmas());
    }

    @Test
    @DisplayName("00시 00분인지 판다")
    void test6() {
        AttendanceDateTime attendanceDateTime = new AttendanceDateTime(LocalDateTime.of(2024, 12, 25, 0, 0));
        Assertions.assertTrue(attendanceDateTime.isZeroTime(DateTimeFormatter.ofPattern("HH:mm")));
    }

    @Test
    @DisplayName("dayOfWeek 으로 반환 테스트")
    void test7() {
        AttendanceDateTime attendanceDateTime = new AttendanceDateTime(LocalDateTime.of(2024, 12, 25, 0, 0));
        org.assertj.core.api.Assertions.assertThat(attendanceDateTime.toDayOfWeek())
                .isEqualTo(LocalDateTime.of(2024, 12, 25, 0, 0).getDayOfWeek());
    }

    @Test
    @DisplayName("로컬 타임으로 변경 테스트")
    void test8() {
        AttendanceDateTime attendanceDateTime = new AttendanceDateTime(LocalDateTime.of(2024, 12, 25, 0, 0));
        org.assertj.core.api.Assertions.assertThat(attendanceDateTime.toLocalTime())
                .isEqualTo(LocalDateTime.of(2024, 12, 25, 0, 0).toLocalTime());
    }

    @Test
    @DisplayName("로컬 데이트 반환 비교")
    void test9 () {
        AttendanceDateTime attendanceDateTime = new AttendanceDateTime(LocalDateTime.of(2024, 12, 25, 0, 0));
        org.assertj.core.api.Assertions.assertThat(
                attendanceDateTime.toLocalDate()
        ).isEqualTo(LocalDateTime.of(2024, 12, 25, 0, 0).toLocalDate());
    }

}