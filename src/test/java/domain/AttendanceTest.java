package domain;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class AttendanceTest {
    @Nested
    @DisplayName("주말 및 공휴일 출석")
    class HollyDays{
        @Test
        @DisplayName("성탄절")
        void test5() {
            assertThatThrownBy(() -> new Attendance(LocalDateTime.of(2024, 12, 25, 9, 55)))
                    .isInstanceOf(IllegalArgumentException.class);
        }
        @Test
        @DisplayName("주말")
        void test6() {
            assertThatThrownBy(() -> new Attendance(LocalDateTime.of(2024, 12, 8, 9, 55)))
                    .isInstanceOf(IllegalArgumentException.class);
        }

    }


    @Nested
    @DisplayName("캠퍼스 운영 시간이 아닌 경우")
    class WrongTime{
        @Test
        @DisplayName("8시 전인 경우")
        void test5() {
            assertThatThrownBy(() -> new Attendance(LocalDateTime.of(2024, 12, 3, 7, 59)))
                    .isInstanceOf(IllegalArgumentException.class);
        }
        @Test
        @DisplayName("23시를 초과한 경우")
        void test6() {
            assertThatThrownBy(() -> new Attendance(LocalDateTime.of(2024, 12, 3, 23, 1)))
                    .isInstanceOf(IllegalArgumentException.class);
        }

    }
}
