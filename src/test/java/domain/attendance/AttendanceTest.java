package domain.attendance;

import java.time.LocalDateTime;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class AttendanceTest {
    @DisplayName("캠퍼스 운영 일자/시간 테스트")
    @Nested
    public class CampusTime {
        @DisplayName("주말 또는 공휴일에 출석하면 예외를 뱉는다")
        @ParameterizedTest
        @MethodSource("holidayOrWeekend")
        void test(int month, int day) {
            // given
            LocalDateTime holidayOrWeekend = LocalDateTime.of(2024, month, day, 10, 0);

            // when & then
            Assertions.assertThatThrownBy(() -> new Attendance(holidayOrWeekend))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("주말 또는 공휴일에는 출석할 수 없습니다");
        }

        private static Stream<Arguments> holidayOrWeekend() {
            return Stream.of(
                    Arguments.arguments(12, 25),
                    Arguments.arguments(12, 29)
            );
        }

        @DisplayName("캠퍼스 운영 시간 외에 출석할 수 없다")
        @ParameterizedTest
        @MethodSource("invalidAttendanceTime")
        void test2(int hour, int minute) {
            // given
            LocalDateTime invalidAttendanceTime = LocalDateTime.of(2025, 2, 28, hour, minute);

            // when & then
            Assertions.assertThatThrownBy(() -> new Attendance(invalidAttendanceTime))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("캠퍼스 운영 시간에만 출석할 수 있습니다");
        }

        private static Stream<Arguments> invalidAttendanceTime() {
            return Stream.of(
                    Arguments.arguments(7, 59),
                    Arguments.arguments(23, 1)
            );
        }

        @DisplayName("캠퍼스 운영 시간에만 출석할 수 있다")
        @ParameterizedTest
        @MethodSource("validAttendanceTime")
        void test3(int hour, int minute) {
            // given
            LocalDateTime validAttendanceTime = LocalDateTime.of(2025, 2, 28, hour, minute);

            // when
            Attendance attendance = new Attendance(validAttendanceTime);

            // then
            Assertions.assertThat(attendance.has(validAttendanceTime.toLocalDate())).isTrue();
        }

        private static Stream<Arguments> validAttendanceTime() {
            return Stream.of(
                    Arguments.arguments(8, 0),
                    Arguments.arguments(23, 0)
            );
        }

    }
}