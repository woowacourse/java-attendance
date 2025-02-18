package domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

public class CrewTest {
    @Test
    void test1() {
        Crew crew = new Crew("띠용");

        assertEquals("12월 05일 목요일 09:59 (출석)", crew.addAttendance(LocalDateTime.of(2024, 12, 5, 9, 59)).printAttendance());
    }

    @Test
    void test2() {
        Crew crew = new Crew("띠용");

        assertEquals("12월 05일 목요일 10:06 (지각)", crew.addAttendance(LocalDateTime.of(2024, 12, 5, 10, 6)).printAttendance());
    }

    @Test
    void test3() {
        Crew crew = new Crew("띠용");

        assertEquals("12월 05일 목요일 10:31 (결석)", crew.addAttendance(LocalDateTime.of(2024, 12, 5, 10, 31)).printAttendance());
    }

    @Test
    void test4() {
        Crew crew = new Crew("띠용");
        crew.addAttendance(LocalDateTime.of(2024, 12, 5, 8, 59));
        assertThatThrownBy(() -> crew.addAttendance(LocalDateTime.of(2024, 12, 5, 9, 59)))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
