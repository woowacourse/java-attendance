package domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class AttendanceStatusTest {

    @Nested
    @DisplayName("화수목금")
    class NotMonday {
        @DisplayName("출석, 지각, 결석1, 결석2")
        @Test
        void test1() {
            assertAll(
                    () -> assertThat(AttendanceStatus.checkAttendanceState(LocalDateTime.of(2024, 12, 3, 9, 55))).isEqualTo(AttendanceStatus.ATTENDED),
                    () -> assertThat(AttendanceStatus.checkAttendanceState(LocalDateTime.of(2024, 12, 3, 10, 6))).isEqualTo(AttendanceStatus.LATE),
                    () -> assertThat(AttendanceStatus.checkAttendanceState(LocalDateTime.of(2024, 12, 3, 10, 31))).isEqualTo(AttendanceStatus.ABSENT),
                    () -> assertThat(AttendanceStatus.checkAttendanceState(LocalDateTime.of(2024, 12, 3, 13, 0))).isEqualTo(AttendanceStatus.ABSENT)
            );
        }
    }

    @Nested
    @DisplayName("월")
    class Monday {
        @DisplayName("출석, 출석, 출석, 지각, 결석")
        @Test
        void test1() {
            assertAll(
                    () -> assertThat(AttendanceStatus.checkAttendanceState(LocalDateTime.of(2024, 12, 2, 9, 55))).isEqualTo(AttendanceStatus.ATTENDED),
                    () -> assertThat(AttendanceStatus.checkAttendanceState(LocalDateTime.of(2024, 12, 2, 10, 6))).isEqualTo(AttendanceStatus.ATTENDED),
                    () -> assertThat(AttendanceStatus.checkAttendanceState(LocalDateTime.of(2024, 12, 2, 13, 0))).isEqualTo(AttendanceStatus.ATTENDED),
                    () -> assertThat(AttendanceStatus.checkAttendanceState(LocalDateTime.of(2024, 12, 2, 13, 6))).isEqualTo(AttendanceStatus.LATE),
                    () -> assertThat(AttendanceStatus.checkAttendanceState(LocalDateTime.of(2024, 12, 2, 13, 31))).isEqualTo(AttendanceStatus.ABSENT)

            );
        }
    }
}