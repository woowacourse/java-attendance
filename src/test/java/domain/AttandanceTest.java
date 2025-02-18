package domain;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttandanceTest {
    @DisplayName("출석 시간이 5분 이하면 출석이다")
    @Test
    void test1(){
        LocalDateTime originalTime = LocalDateTime.of(2024,12,2,13,0);
        Attandance attandance = new Attandance(originalTime);
        Assertions.assertSame(attandance.calculateAttendanceStatus(), AttendanceStatus.PRESENT);
    }

    @DisplayName("출석 시간이 5분 초과, 30분 이하면 지각이다")
    @Test
    void test2(){
        LocalDateTime originalTime = LocalDateTime.of(2024,12,2,13,6);
        Attandance attandance = new Attandance(originalTime);
        Assertions.assertSame(attandance.calculateAttendanceStatus(), AttendanceStatus.LATE);
    }

    @DisplayName("출석 시간이 30분 초과면 결석한다")
    @Test
    void test3(){
        LocalDateTime originalTime = LocalDateTime.of(2024,12,2,13,31);
        Attandance attandance = new Attandance(originalTime);
        Assertions.assertSame(attandance.calculateAttendanceStatus(), AttendanceStatus.ABSENT);
    }
}