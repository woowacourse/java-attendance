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
        @DisplayName("출석 처리를 할 수 있다.")
        @Test
        void test2() {
            Attendance attendance = new Attendance(LocalDateTime.of(2024, 12, 3, 9, 55));
            assertThat(attendance.getStatus()).isEqualTo(Status.ATTEND);
        }

        @DisplayName("지각 처리를 할 수 있다.")
        @Test
        void test3() {
            Attendance attendance = new Attendance(LocalDateTime.of(2024, 12, 3, 10, 6));
            assertThat(attendance.getStatus()).isEqualTo(Status.LATE);
        }

        @DisplayName("결석 처리를 할 수 있다.")
        @Test
        void test4() {
            Attendance attendance = new Attendance(LocalDateTime.of(2024, 12, 3, 10, 31));
            assertThat(attendance.getStatus()).isEqualTo(Status.ABSENCE);
        }
    }

    @Nested
    @DisplayName("월")
    class Monday {
        @DisplayName("출석 처리를 할 수 있다.")
        @Test
        void test2() {
            Attendance attendance = new Attendance(LocalDateTime.of(2024, 12, 2, 13, 5));
            assertThat(attendance.getStatus()).isEqualTo(Status.ATTEND);
        }

        @DisplayName("지각 처리를 할 수 있다.")
        @Test
        void test3() {
            Attendance attendance = new Attendance(LocalDateTime.of(2024, 12, 2, 13, 30));
            assertThat(attendance.getStatus()).isEqualTo(Status.LATE);
        }

        @DisplayName("결석 처리를 할 수 있다.")
        @Test
        void test4() {
            Attendance attendance = new Attendance(LocalDateTime.of(2024, 12, 2, 13, 31));
            assertThat(attendance.getStatus()).isEqualTo(Status.ABSENCE);
        }
    }

    @Nested
    @DisplayName("주말 및 공휴일 출석")
    class HollyDays {
        @Test
        @DisplayName("성탄절은 공휴일이라 출석을 시도할 경우 예외가 발생한다.")
        void test5() {
            assertThatThrownBy(() -> new Attendance(LocalDateTime.of(2024, 12, 25, 9, 55)))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("주말에 출석을 시도할 경우 예외가 발생한다.")
        void test6() {
            assertThatThrownBy(() -> new Attendance(LocalDateTime.of(2024, 12, 8, 9, 55)))
                    .isInstanceOf(IllegalArgumentException.class);
        }

    }
}
