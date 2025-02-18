package domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CrewTest {
    @DisplayName("정상 출석")
    @Test
    void test1() {
        Crew crew = new Crew("띠용");

        assertEquals("12월 05일 목요일 09:59 (출석)", crew.addAttendance(LocalDateTime.of(2024, 12, 5, 9, 59)).printAttendance());
    }
    @DisplayName("지각")
    @Test
    void test2() {
        Crew crew = new Crew("띠용");

        assertEquals("12월 05일 목요일 10:06 (지각)", crew.addAttendance(LocalDateTime.of(2024, 12, 5, 10, 6)).printAttendance());
    }
    @DisplayName("결석")
    @Test
    void test3() {
        Crew crew = new Crew("띠용");

        assertEquals("12월 05일 목요일 10:31 (결석)", crew.addAttendance(LocalDateTime.of(2024, 12, 5, 10, 31)).printAttendance());
    }
    @DisplayName("중복 출석 시도")
    @Test
    void test4() {
        Crew crew = new Crew("띠용");
        crew.addAttendance(LocalDateTime.of(2024, 12, 5, 8, 59));
        assertThatThrownBy(() -> crew.addAttendance(LocalDateTime.of(2024, 12, 5, 9, 59)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("출석 수정")
    @Test
    void test5() {
        Crew crew = new Crew("띠용");
        crew.addAttendance(LocalDateTime.of(2024, 12, 5, 9, 55));

        assertEquals("12월 05일 목요일 09:55 (출석) -> 10:06 (지각) 수정 완료!",
                crew.update(LocalDateTime.of(2024, 12, 5, 10, 6)));
    }

    @DisplayName("출석 수정 예외")
    @Test
    void test6() {
        Crew crew = new Crew("띠용");

        assertThatThrownBy(() -> crew.update(LocalDateTime.of(2024, 12, 5, 10, 6)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    // Todo : 테스트 시나리오 다양화
}
