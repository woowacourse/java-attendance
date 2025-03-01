package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceDateTimeTest {

    @Test
    @DisplayName("AttendanceTime 을 통해 오늘이 주말인지 공휴일인지 확인하는 메서드 테스트")
    void test1() {
        AttendanceDateTime saturdayAttendanceDateTime = new AttendanceDateTime(LocalDateTime.of(2024, 12, 7, 0, 0));
        AttendanceDateTime sundayAttendanceDateTime = new AttendanceDateTime(LocalDateTime.of(2024, 12, 8, 0, 0));
        AttendanceDateTime christmasAttendanceDateTime = new AttendanceDateTime(LocalDateTime.of(2024, 12, 25, 0, 0));
        Assertions.assertTrue(saturdayAttendanceDateTime.isWeekend());
        Assertions.assertTrue(sundayAttendanceDateTime.isWeekend());
        Assertions.assertTrue(christmasAttendanceDateTime.isChristmas());
    }

    @Test
    @DisplayName("AttendanceTime 멤버 변수를 LocalDate 로 바꾸는 메서드 테스트")
    void test2() {
        AttendanceDateTime attendanceDateTime = new AttendanceDateTime(LocalDateTime.of(2024, 12, 12, 12, 0));
        Assertions.assertEquals(attendanceDateTime.toLocalDate(), LocalDate.of(2024, 12, 12));
    }

    @Test
    @DisplayName("다른 객체이지만 LocalDateTime 값을 비교하여 같은지 확인하는 메서드 테스트")
    void test6() {
        AttendanceDateTime attendanceDateTime = new AttendanceDateTime(LocalDateTime.of(2024, 12, 12, 12, 12));
        AttendanceDateTime attendanceDateTime1 = new AttendanceDateTime(LocalDateTime.of(2024, 12, 12, 12, 12));
        Assertions.assertEquals(attendanceDateTime, attendanceDateTime1);
    }

    @Test
    @DisplayName("Date 까지만 비교하여 같은 년,월,일 인지 비교하는 메서드 테스트")
    void test7() {
        AttendanceDateTime attendanceDateTime = new AttendanceDateTime(LocalDateTime.of(2024, 12, 12, 12, 12));
        AttendanceDateTime attendanceDateTime1 = new AttendanceDateTime(LocalDateTime.of(2024, 12, 12, 13, 13));
        Assertions.assertTrue(
                attendanceDateTime.isSameDate(attendanceDateTime1)
        );
    }

    @Test
    @DisplayName("월요일인지 비교하는 테스트")
    void test8() {
        AttendanceDateTime monday = new AttendanceDateTime(LocalDateTime.of(2024, 12, 2, 12, 12));
        AttendanceDateTime notMonday = new AttendanceDateTime(LocalDateTime.of(2024, 12, 3, 12, 12));
        Assertions.assertTrue(monday.isMonday());
        Assertions.assertFalse(notMonday.isMonday());
    }

}
