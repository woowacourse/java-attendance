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
        LocalDateTime attendanceDateTime = LocalDateTime.of(2024, 12, 9, 10, 10);
        Attendance attendance = Attendance.from(attendanceDateTime);

        // when
        AttendanceState attendanceState = attendance.check();

        // then
        assertThat(attendanceState).isEqualTo(AttendanceState.ATTEND);
    }

    @ParameterizedTest
    @DisplayName("출석 시간으로부터 30분 초과는 결석이다")
    @MethodSource("provideDateTimeForAbsent")
    void over_enterTime_then_absent(LocalDateTime attendanceDateTime) {
        // given
        Attendance attendance = Attendance.from(attendanceDateTime);

        // when
        AttendanceState attendanceState = attendance.check();

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

    @Nested
    class AttendanceExceptionTest {
        @Test
        @DisplayName("캠퍼스 운영시간 전에 출석하면 예외가 발생한다")
        void under_operating_time_then_exception() {
            // given
            LocalDateTime attendanceDateTime = LocalDateTime.of(2024, 12, 9, 7, 10);

            // when-then
            assertThatThrownBy(() -> Attendance.from(attendanceDateTime))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("[ERROR] 출석 시간이 아닙니다.");
        }

        @Test
        @DisplayName("캠퍼스 운영시간 후에 출석하면 예외가 발생한다")
        void over_operating_time_then_exception() {
            // given
            LocalDateTime attendanceDateTime = LocalDateTime.of(2024, 12, 9, 23, 10);

            // when-then
            assertThatThrownBy(() -> Attendance.from(attendanceDateTime))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("[ERROR] 출석 시간이 아닙니다.");
        }

        @Test
        @DisplayName("주말에 출석하면 예외가 발생한다")
        void attendance_weekend_then_exception() {
            // given
            LocalDateTime attendanceDateTime = LocalDateTime.of(2024, 12, 8, 10, 10);

            // when-then
            assertThatThrownBy(() -> Attendance.from(attendanceDateTime))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("[ERROR] 주말에는 출석할 수 없습니다.");
        }

        @Test
        @DisplayName("공휴일에 출석하면 예외가 발생한다")
        void attendance_holiday_then_exception() {
            // given
            LocalDateTime attendanceDateTime = LocalDateTime.of(2024, 12, 25, 10, 10);

            // when-then
            assertThatThrownBy(() -> Attendance.from(attendanceDateTime))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("[ERROR] 공휴일에는 출석할 수 없습니다.");
        }
    }

//    @Nested
//    class Update {
//        @ParameterizedTest
//        @DisplayName("출석 기록을 수정할 수 있다")
//        @MethodSource("provideDateAndTimeForUpdate")
//        void updateTest(LocalTime originTime, LocalDate updateDate, LocalTime updateTime, String result) {
//            // given
//            Attendance attendance = Attendance.from(attendanceDateTime);
//
//            // when
//            String expectedResult = attendance.update(originTime, nickname, updateDate, updateTime);
//
//            // then
//            assertThat(expectedResult).isEqualTo(result);
//        }
//
//        private static Stream<Arguments> provideDateAndTimeForUpdate() {
//            return Stream.of(
//                    Arguments.of(
//                            LocalTime.of(10, 7),
//                            LocalDate.of(2024, 12, 3),
//                            LocalTime.of(9, 59),
//                            "12월 03일 화요일 10:07 (지각) -> 09:59 (출석) 수정 완료!"
//                    ),
//                    Arguments.of(
//                            LocalTime.of(10, 7),
//                            LocalDate.of(2024, 12, 3),
//                            LocalTime.of(10, 12),
//                            "12월 03일 화요일 10:07 (지각) -> 10:12 (지각) 수정 완료!"
//                    ),
//                    Arguments.of(
//                            LocalTime.of(10, 7),
//                            LocalDate.of(2024, 12, 4),
//                            LocalTime.of(9, 59),
//                            "12월 04일 수요일 10:07 (지각) -> 09:59 (출석) 수정 완료!"
//                    )
//            );
//        }
//    }

}
