package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
    @DisplayName("AttendanceTime 멤버 변수를 LocalTime 으로 바꾸는 메서드 테스트")
    void test3() {
        AttendanceDateTime attendanceDateTime = new AttendanceDateTime(LocalDateTime.of(2024, 12, 12, 12, 0));
        Assertions.assertEquals(attendanceDateTime.toLocalTime(), LocalTime.of(12, 0));
    }

    @Test
    @DisplayName("AttendanceTime 의 멤버 변수를 LocalTime 으로 바꾸고, 08:00 ~ 23:00 사이에 있는지 확인하는 메서드")
    void test4() {
        AttendanceDateTime attendanceDateTime = new AttendanceDateTime(LocalDateTime.of(2024, 12, 12, 7, 0));
        Assertions.assertTrue(
                attendanceDateTime.isNotOpeningTime()
        );
    }

    @Test
    @DisplayName("AttendanceTime 의 멤버 변수를 LocalTime 으로 바꾸고, 08:00 ~ 23:00 사이에 있는지 확인하는 메서드")
    void test5() {
        AttendanceDateTime attendanceDateTime = new AttendanceDateTime(LocalDateTime.of(2024, 12, 12, 23, 10));
        Assertions.assertTrue(
                attendanceDateTime.isNotOpeningTime()
        );
    }

    @Test
    @DisplayName("다른 객체이지만 LocalDateTime 값을 비교하여 같은지 확인하는 메서드 테스트")
    void test6() {
        AttendanceDateTime attendanceDateTime = new AttendanceDateTime(LocalDateTime.of(2024, 12, 12, 12, 12));
        AttendanceDateTime attendanceDateTime1 = new AttendanceDateTime(LocalDateTime.of(2024, 12, 12, 12, 12));
        Assertions.assertTrue(
                attendanceDateTime.equals(attendanceDateTime1)
        );
    }
}
