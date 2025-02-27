package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@Nested
public class AttendanceStatusTest {

    @Nested
    @DisplayName("시간별 상태 생성 테스트")
    class createStatusTest {

        @ParameterizedTest
        @MethodSource("providePresentTime")
        @DisplayName("출석 상태를 반환한다.")
        void pickPresent(LocalDateTime dateTime) {
            LocalDate date = dateTime.toLocalDate();
            LocalTime time = dateTime.toLocalTime();

            AttendanceStatus status = AttendanceStatus.of(date.getDayOfWeek(), time);
            assertThat(status).isEqualTo(AttendanceStatus.PRESENT);
        }

        static Stream<LocalDateTime> providePresentTime() {
            return Stream.of(
                LocalDateTime.of(2024, 12, 2, 13, 0),
                LocalDateTime.of(2025, 12, 3, 10, 5),
                LocalDateTime.of(2025, 12, 4, 9, 30)
            );
        }

        @ParameterizedTest
        @MethodSource("provideLateTime")
        @DisplayName("지각 상태를 반환한다.")
        void pickLate(LocalDateTime dateTime) {
            LocalDate date = dateTime.toLocalDate();
            LocalTime time = dateTime.toLocalTime();

            AttendanceStatus status = AttendanceStatus.of(date.getDayOfWeek(), time);
            assertThat(status).isEqualTo(AttendanceStatus.LATE);
        }

        static Stream<LocalDateTime> provideLateTime() {
            return Stream.of(
                LocalDateTime.of(2024, 12, 2, 13, 6),
                LocalDateTime.of(2025, 12, 3, 10, 30)
            );
        }

        @ParameterizedTest
        @MethodSource("provideAbsentTime")
        @DisplayName("결석 상태를 반환한다.")
        void pickAbsent(LocalDateTime dateTime) {
            LocalDate date = dateTime.toLocalDate();
            LocalTime time = dateTime.toLocalTime();

            AttendanceStatus status = AttendanceStatus.of(date.getDayOfWeek(), time);
            assertThat(status).isEqualTo(AttendanceStatus.ABSENT);
        }

        static Stream<LocalDateTime> provideAbsentTime() {
            return Stream.of(
                LocalDateTime.of(2024, 12, 2, 13, 31),
                LocalDateTime.of(2025, 12, 3, 10, 59),
                LocalDateTime.of(2025, 12, 4, 11, 59)
            );
        }
    }
}