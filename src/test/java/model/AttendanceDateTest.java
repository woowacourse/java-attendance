package model;

import java.time.LocalDate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceDateTest {

    @Test
    @DisplayName("AttendanceTime 을 통해 오늘이 주말인지 공휴일인지 확인하는 메서드 테스트")
    void test1() {
        AttendanceDate saturdayAttendanceDate = new AttendanceDate(LocalDate.of(2024, 12, 7));
        AttendanceDate sundayAttendanceDate = new AttendanceDate(LocalDate.of(2024, 12, 8));
        AttendanceDate christmasAttendanceDate = new AttendanceDate(LocalDate.of(2024, 12, 25));
        Assertions.assertTrue(saturdayAttendanceDate.isWeekend());
        Assertions.assertTrue(sundayAttendanceDate.isWeekend());
        Assertions.assertTrue(christmasAttendanceDate.isChristmas());
    }

    @Test
    @DisplayName("다른 객체이지만 LocalDate 값을 비교하여 같은지 확인하는 메서드 테스트")
    void test6() {
        AttendanceDate attendanceDate = new AttendanceDate(LocalDate.of(2024, 12, 12));
        AttendanceDate attendanceDate1 = new AttendanceDate(LocalDate.of(2024, 12, 12));
        Assertions.assertEquals(attendanceDate, attendanceDate1);
    }

    @Test
    @DisplayName("월요일인지 비교하는 테스트")
    void test8() {
        AttendanceDate monday = new AttendanceDate(LocalDate.of(2024, 12, 2));
        AttendanceDate notMonday = new AttendanceDate(LocalDate.of(2024, 12, 3));
        Assertions.assertTrue(monday.isMonday());
        Assertions.assertFalse(notMonday.isMonday());
    }

}
