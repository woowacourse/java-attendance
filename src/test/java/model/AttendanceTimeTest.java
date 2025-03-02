package model;

import java.time.LocalTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceTimeTest {
    AttendanceTime attendanceTime = new AttendanceTime(LocalTime.of(8, 6));

    @Test
    @DisplayName("시간이 더 늦는 걸 계산하는 테스트")
    void test1() {
        AttendanceTime wantToCompareTime = new AttendanceTime(LocalTime.of(8, 5));
        Assertions.assertTrue(
                attendanceTime.isAfter(wantToCompareTime)
        );
    }

    @Test
    @DisplayName("캠퍼스 운영 시간이 아닌 경우를 판단하는 테스트")
    void test4() {
        AttendanceTime attendanceTime1 = new AttendanceTime(LocalTime.of(7, 59));
        Assertions.assertTrue(
                attendanceTime1.isNotOpeningTime()
        );
    }

    @Test
    @DisplayName("캠퍼스 운영 시간이 아닌 경우를 판단하는 테스트")
    void test5() {
        AttendanceTime attendanceTime1 = new AttendanceTime(LocalTime.of(23, 1));
        Assertions.assertTrue(
                attendanceTime1.isNotOpeningTime()
        );
    }

    @Test
    @DisplayName("00시 00분 을 판단하는 메서드 테스트")
    void test6() {
        AttendanceTime attendanceTime1 = new AttendanceTime(LocalTime.of(0, 0));
        Assertions.assertTrue(
                attendanceTime1.isZeroTime()
        );
    }
}
