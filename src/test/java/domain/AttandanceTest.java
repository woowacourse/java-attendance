package domain;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AttandanceTest {
    @Test
    void test1(){
        LocalDateTime originalTime = LocalDateTime.of(2024,12,2,13,0);
        Attandance attandance = new Attandance(originalTime);
        Assertions.assertSame(attandance.calculateAttendanceStatus(), AttendanceStatus.PRESENT);
    }

    @Test
    void test2(){
        LocalDateTime originalTime = LocalDateTime.of(2024,12,2,13,6);
        Attandance attandance = new Attandance(originalTime);
        Assertions.assertSame(attandance.calculateAttendanceStatus(), AttendanceStatus.LATE);
    }

    @Test
    void test3(){
        LocalDateTime originalTime = LocalDateTime.of(2024,12,2,13,31);
        Attandance attandance = new Attandance(originalTime);
        Assertions.assertSame(attandance.calculateAttendanceStatus(), AttendanceStatus.ABSENT);
    }
}