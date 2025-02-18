package domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AttendanceTest {
    @DisplayName("출석인지 확인한다.")
    @Test
    void test1() {
        // given
        Attendance attendance = new Attendance(new Crew("밍곰"), LocalDateTime.of(2025, 2, 17, 13, 5));

        // when
        String status = attendance.getStatus();

        // then
        assertThat(status).isEqualTo("출석");
    }

    @DisplayName("지각인지 확인한다.")
    @Test
    void test2() {
        // given

        // when

        // then
    }

    @DisplayName("결석인지 확인한다.")
    @Test
    void test3() {
        // given

        // when

        // then
    }
}
