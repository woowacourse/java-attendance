import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class AttendanceTest {

    @Test
    @DisplayName("출석 시간을 입력하면 출석할 수 있다")
    void input_enterTime_then_attendance() {
        // given
        LocalDateTime today = LocalDateTime.of(2024, 12, 9, 10, 10);
        Attendance attendance = new Attendance();

        // when
        String message = attendance.check(today);

        // then
        assertThat(message).isEqualTo("12월 09일 월요일 10:10 (출석)");
    }

    @ParameterizedTest
    @DisplayName("출석 시간으로부터 30분 초과는 결석이다")
    @MethodSource("provideDateTimeForAbsent")
    void over_enterTime_then_absent(LocalDateTime today, String result) {
        // given
        Attendance attendance = new Attendance();

        // when
        String message = attendance.check(today);

        // then
        assertThat(message).isEqualTo(result);
    }

    private static Stream<Arguments> provideDateTimeForAbsent() {
        return Stream.of(
                Arguments.of(
                        LocalDateTime.of(2024, 12, 10, 10, 31),
                        "12월 10일 화요일 10:31 (결석)"
                        ),
                Arguments.of(
                        LocalDateTime.of(2024, 12, 9, 13, 31),
                        "12월 09일 월요일 13:31 (결석)"
                ),
                Arguments.of(
                        LocalDateTime.of(2024, 12, 9, 14, 31),
                        "12월 09일 월요일 14:31 (결석)"
                ),
                Arguments.of(
                        LocalDateTime.of(2024, 12, 10, 11, 31),
                        "12월 10일 화요일 11:31 (결석)"
                )
        );
    }

    @Nested
    class 출석_예외_테스트{
        @Test
        @DisplayName("캠퍼스 운영시간 전에 출석하면 예외가 발생한다")
        void under_operating_time_then_exception() {
            // given
            LocalDateTime today = LocalDateTime.of(2024, 12, 9, 7, 10);
            Attendance attendance = new Attendance();

            // when-then
            assertThatThrownBy(() -> attendance.check(today))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("[ERROR] 출석 시간이 아닙니다.");
        }

        @Test
        @DisplayName("캠퍼스 운영시간 후에 출석하면 예외가 발생한다")
        void over_operating_time_then_exception() {
            // given
            LocalDateTime today = LocalDateTime.of(2024, 12, 9, 23, 10);
            Attendance attendance = new Attendance();

            // when-then
            assertThatThrownBy(() -> attendance.check(today))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("[ERROR] 출석 시간이 아닙니다.");
        }

        @Test
        @DisplayName("주말에 출석하면 예외가 발생한다")
        void attendance_weekend_then_exception() {
            // given
            LocalDateTime today = LocalDateTime.of(2024, 12, 8, 10, 10);
            Attendance attendance = new Attendance();

            // when-then
            assertThatThrownBy(() -> attendance.check(today))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("[ERROR] 주말에는 출석할 수 없습니다.");
        }

        @Test
        @DisplayName("공휴일에 출석하면 예외가 발생한다")
        void attendance_holiday_then_exception() {
            // given
            LocalDateTime today = LocalDateTime.of(2024, 12, 25, 10, 10);
            Attendance attendance = new Attendance();

            // when-then
            assertThatThrownBy(() -> attendance.check(today))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("[ERROR] 공휴일에는 출석할 수 없습니다.");
        }
    }

}
