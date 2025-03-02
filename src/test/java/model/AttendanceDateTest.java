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
        Assertions.assertTrue(saturdayAttendanceDate.isHoliday());
        Assertions.assertTrue(sundayAttendanceDate.isHoliday());
        Assertions.assertTrue(christmasAttendanceDate.isHoliday());
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

    @Test
    @DisplayName("같은 요일 비교하는 메서드 테스트")
    void test7() {
        AttendanceDate attendanceDate = new AttendanceDate(LocalDate.of(2024, 12, 12));
        AttendanceDate attendanceDate1 = new AttendanceDate(LocalDate.of(2024, 12, 12));
        Assertions.assertTrue(
                attendanceDate.isSameDate(attendanceDate1)
        );
    }

    @Test
    @DisplayName("12월인지 검사하는 메서드 테스트")
    void test9() {
        AttendanceDate attendanceDate = new AttendanceDate(LocalDate.of(2024, 11, 30));
        Assertions.assertFalse(
                attendanceDate.isDecemberDay()
        );
    }

    @Test
    @DisplayName("하루를 더해주는 메서드 리턴")
    void test10() {
        AttendanceDate attendanceDate = new AttendanceDate(LocalDate.of(2024, 11, 30));
        Assertions.assertEquals(
                attendanceDate.plusOneDay(), new AttendanceDate(LocalDate.of(2024, 12, 1))
        );
    }

    @Test
    @DisplayName("정렬을 위한 메서드 테스트")
    void test11() {
        AttendanceDate earlyAttendanceDate = new AttendanceDate(LocalDate.of(2024, 12, 1));
        AttendanceDate lateAttendanceDate = new AttendanceDate(LocalDate.of(2024, 12, 12));

        Assertions.assertTrue(earlyAttendanceDate.compareTo(lateAttendanceDate) < 0);
    }

    @Test
    @DisplayName("month 리턴하는 메서드 테스트")
    void test12() {
        AttendanceDate attendanceDate = new AttendanceDate(LocalDate.of(2024, 12, 1));
        Assertions.assertEquals(
                attendanceDate.getMonth(), 12
        );
    }

    @Test
    @DisplayName("day 리턴하는 메서드 테스트")
    void test13() {
        AttendanceDate attendanceDate = new AttendanceDate(LocalDate.of(2024, 12, 1));
        Assertions.assertEquals(
                attendanceDate.getDate(), 1
        );
    }

    @Test
    @DisplayName("요일 리턴하는 메서드 테스ㅌ")
    void test14() {
        AttendanceDate attendanceDate = new AttendanceDate(LocalDate.of(2024, 12, 1));
        Assertions.assertEquals(
                attendanceDate.getDay(), "월요일"
        );
    }

}
