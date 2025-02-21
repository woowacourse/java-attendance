package domain;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class AttendanceTest {
    @Nested
    @DisplayName("화수목금")
    class NotMonday {
        @DisplayName("출석한 경우")
        @Test
        void test2() {
            Attendance attendance = new Attendance(LocalDateTime.of(2024, 12, 3, 9, 55));
            assertThat(attendance.getState()).isEqualTo("출석");
        }

        @DisplayName("지각한 경우")
        @Test
        void test3() {
            Attendance attendance = new Attendance(LocalDateTime.of(2024, 12, 3, 10, 6));
            assertThat(attendance.getState()).isEqualTo("지각");
        }

        @DisplayName("결석한 경우")
        @Test
        void test4() {
            Attendance attendance = new Attendance(LocalDateTime.of(2024, 12, 3, 10, 31));
            assertThat(attendance.getState()).isEqualTo("결석");
        }

        @DisplayName("1시 이후인 경우")
        @Test
        void test5() {
            Attendance attendance = new Attendance(LocalDateTime.of(2024, 12, 3, 13, 0));
            assertThat(attendance.getState()).isEqualTo("결석");
        }
    }

    @Nested
    @DisplayName("월")
    class Monday {
        @DisplayName("출석한 경우")
        @Test
        void test2() {
            Attendance attendance = new Attendance(LocalDateTime.of(2024, 12, 2, 9, 55));
            assertThat(attendance.getState()).isEqualTo("출석");
        }

        @DisplayName("지각한 경우")
        @Test
        void test3() {
            Attendance attendance = new Attendance(LocalDateTime.of(2024, 12, 2, 13, 6));
            assertThat(attendance.getState()).isEqualTo("지각");
        }

        @DisplayName("결석한 경우")
        @Test
        void test4() {
            Attendance attendance = new Attendance(LocalDateTime.of(2024, 12, 2, 13, 31));
            assertThat(attendance.getState()).isEqualTo("결석");
        }
    }

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
}
