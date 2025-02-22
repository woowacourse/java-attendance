package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class AttendanceTimeTest {

    @Nested
    @DisplayName("출석 시간 확인 테스트")
    class AttendanceStateTest {
        @Test
        @DisplayName("출석 시간을 입력하면 출석할 수 있다")
        void input_enterTime_then_attendance() {
            // given
            LocalDateTime attendanceDateTime = LocalDateTime.of(2024, 12, 9, 10, 10);
            AttendanceDateTime attendanceTime = AttendanceDateTime.from(attendanceDateTime);

            // when
            AttendanceState attendanceState = attendanceTime.check();

            // then
            assertThat(attendanceState).isEqualTo(AttendanceState.ATTEND);
        }

        @ParameterizedTest
        @DisplayName("출석 시간으로부터 30분 초과는 결석이다")
        @MethodSource("provideDateTimeForAbsent")
        void over_enterTime_then_absent(LocalDateTime attendanceDateTime) {
            // given
            AttendanceDateTime attendanceTime = AttendanceDateTime.from(attendanceDateTime);

            // when
            AttendanceState attendanceState = attendanceTime.check();

            // then
            assertThat(attendanceState).isEqualTo(AttendanceState.ABSENT);
        }

        private static Stream<Arguments> provideDateTimeForAbsent() {
            return Stream.of(
                    Arguments.of(
                            LocalDateTime.of(2024, 12, 10, 10, 31)
                    ),
                    Arguments.of(
                            LocalDateTime.of(2024, 12, 9, 13, 31)
                    ),
                    Arguments.of(
                            LocalDateTime.of(2024, 12, 9, 14, 31)
                    ),
                    Arguments.of(
                            LocalDateTime.of(2024, 12, 10, 11, 31)
                    )
            );
        }

        @ParameterizedTest
        @DisplayName("출석 시간으로부터 5분 초과는 지각이다")
        @MethodSource("provideDateTimeForLate")
        void over_enterTime_then_late(LocalDateTime attendanceDateTime) {
            // given
            AttendanceDateTime attendanceTime = AttendanceDateTime.from(attendanceDateTime);

            // when
            AttendanceState attendanceState = attendanceTime.check();

            // then
            assertThat(attendanceState).isEqualTo(AttendanceState.LATE);
        }

        private static Stream<Arguments> provideDateTimeForLate() {
            return Stream.of(
                    Arguments.of(
                            LocalDateTime.of(2024, 12, 10, 10, 6)
                    ),
                    Arguments.of(
                            LocalDateTime.of(2024, 12, 9, 13, 6)
                    )
            );
        }
    }

    @Nested
    @DisplayName("출석 시간 예외 테스트")
    class AttendanceTimeExceptionTest {
        @Test
        @DisplayName("캠퍼스 운영시간 전에 출석하면 예외가 발생한다")
        void under_operating_time_then_exception() {
            // given
            LocalDateTime attendanceDateTime = LocalDateTime.of(2024, 12, 9, 7, 10);

            // when-then
            assertThatThrownBy(() -> AttendanceDateTime.from(attendanceDateTime))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("[ERROR] 출석 시간이 아닙니다.");
        }

        @Test
        @DisplayName("캠퍼스 운영시간 후에 출석하면 예외가 발생한다")
        void over_operating_time_then_exception() {
            // given
            LocalDateTime attendanceDateTime = LocalDateTime.of(2024, 12, 9, 23, 10);

            // when-then
            assertThatThrownBy(() -> AttendanceDateTime.from(attendanceDateTime))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("[ERROR] 출석 시간이 아닙니다.");
        }

        @Test
        @DisplayName("주말에 출석하면 예외가 발생한다")
        void attendance_weekend_then_exception() {
            // given
            LocalDateTime attendanceDateTime = LocalDateTime.of(2024, 12, 8, 10, 10);

            // when-then
            assertThatThrownBy(() -> AttendanceDateTime.from(attendanceDateTime))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("[ERROR] 주말에는 출석할 수 없습니다.");
        }

        @Test
        @DisplayName("공휴일에 출석하면 예외가 발생한다")
        void attendance_holiday_then_exception() {
            // given
            LocalDateTime attendanceDateTime = LocalDateTime.of(2024, 12, 25, 10, 10);

            // when-then
            assertThatThrownBy(() -> AttendanceDateTime.from(attendanceDateTime))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("[ERROR] 공휴일에는 출석할 수 없습니다.");
        }
    }

    @Nested
    @DisplayName("출석 기록 수정 테스트")
    class Update {
        @ParameterizedTest
        @DisplayName("출석 기록을 수정할 수 있다")
        @MethodSource("provideOriginDateTimeForUpdate")
        void updateTest(LocalDateTime originDateTime, LocalTime updateTime) {
            // given
            AttendanceDateTime attendanceTime = AttendanceDateTime.from(originDateTime);

            // when-then
            assertThatCode(() -> attendanceTime.update(updateTime))
                    .doesNotThrowAnyException();
        }

        private static Stream<Arguments> provideOriginDateTimeForUpdate() {
            return Stream.of(
                    Arguments.of(
                            LocalDateTime.of(2024, 12, 3, 10, 7),
                            LocalTime.of(9, 59)
                    ),
                    Arguments.of(
                            LocalDateTime.of(2024, 12, 3, 10, 7),
                            LocalTime.of(10, 12)
                    ),
                    Arguments.of(
                            LocalDateTime.of(2024, 12, 4, 10, 7),
                            LocalTime.of(9, 59)
                    )
            );
        }
    }

    @Nested
    @DisplayName("날짜 일치 테스트")
    class DayEqualsTest {
        @Test
        @DisplayName("날짜 일치 여부를 테스트할 수 있다.")
        void isDaySameTest() {
            //given
            AttendanceDateTime attendanceDatetime = AttendanceDateTime.from(LocalDateTime.of(2024, 12, 13, 10, 10));
            AttendanceSheet attendanceSheet = new AttendanceSheet("링크", attendanceDatetime);

            //when
            assertThat(attendanceDatetime.isSame(attendanceSheet)).isTrue();
        }
    }
}
