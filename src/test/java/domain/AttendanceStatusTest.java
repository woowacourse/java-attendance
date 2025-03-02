package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class AttendanceStatusTest {

    @Nested
    @DisplayName("출석 상태를 계산한다.")
    class CalculateStatus {

        @DisplayName("화,수,목,금기준 출석 시간에 따라 상태를 올바르게 계산한다.")
        @ParameterizedTest
        @MethodSource("provideTimeAndAttendanceStatus")
        public void calculateStatus(final LocalTime time, final AttendanceStatus expected) throws Exception {
            // given
            final DayOfWeek tuesday = DayOfWeek.TUESDAY;

            // when
            final AttendanceStatus actual = AttendanceStatus.calculateStatus(time, tuesday);

            // then
            assertThat(actual).isEqualByComparingTo(expected);
        }

        private static Stream<Arguments> provideTimeAndAttendanceStatus() {
            return Stream.of(
                    Arguments.of(LocalTime.of(10, 0), AttendanceStatus.ATTENDANCE),
                    Arguments.of(LocalTime.of(10, 5), AttendanceStatus.ATTENDANCE),
                    Arguments.of(LocalTime.of(10, 6), AttendanceStatus.LATE),
                    Arguments.of(LocalTime.of(10, 30), AttendanceStatus.LATE),
                    Arguments.of(LocalTime.of(10, 31), AttendanceStatus.ABSENCE)
            );
        }

        @DisplayName("월요일 기준 출석 시간에 따라 상태를 올바르게 계산한다.")
        @ParameterizedTest
        @MethodSource("provideTimeAndAttendanceStatusForMonday")
        public void calculateStatusForMonday(final LocalTime time, final AttendanceStatus expected) throws Exception {
            // given
            final DayOfWeek tuesday = DayOfWeek.MONDAY;

            // when
            final AttendanceStatus actual = AttendanceStatus.calculateStatus(time, tuesday);

            // then
            assertThat(actual).isEqualByComparingTo(expected);
        }

        private static Stream<Arguments> provideTimeAndAttendanceStatusForMonday() {
            return Stream.of(
                    Arguments.of(LocalTime.of(13, 0), AttendanceStatus.ATTENDANCE),
                    Arguments.of(LocalTime.of(13, 5), AttendanceStatus.ATTENDANCE),
                    Arguments.of(LocalTime.of(13, 6), AttendanceStatus.LATE),
                    Arguments.of(LocalTime.of(13, 30), AttendanceStatus.LATE),
                    Arguments.of(LocalTime.of(13, 31), AttendanceStatus.ABSENCE)
            );
        }

    }

}
