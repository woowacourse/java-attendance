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
        AttendanceTime wantToCompareTime = new AttendanceTime(LocalTime.of(8, 7));
        Assertions.assertTrue(
                attendanceTime.isAfter(wantToCompareTime)
        );
    }

    @Test
    @DisplayName("시간이 더 이른 걸 계산하는 테스트")
    void test2() {
        AttendanceTime wantToCompareTime = new AttendanceTime(LocalTime.of(8, 7));
        Assertions.assertTrue(
                attendanceTime.isBefore(wantToCompareTime)
        );
    }

    @Test
    @DisplayName("시간이 같은 걸 계산하는 테스트")
    void test3() {
        AttendanceTime wantToCompareTime = new AttendanceTime(LocalTime.of(8, 6));
        Assertions.assertTrue(
                attendanceTime.isEqual(wantToCompareTime)
        );
    }
}
