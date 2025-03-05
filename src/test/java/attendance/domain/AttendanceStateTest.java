package attendance.domain;

import attendance.util.DateGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("출결 상태 테스트")
class AttendanceStateTest {

    private static final DateGenerator dateGenerator = new TestDateGenerator();

    @ParameterizedTest(name = "등교 시간: {0}, 출결 상황: {1}")
    @MethodSource
    @DisplayName("등교 시간으로 출결 상황을 반환한다")
    void shouldReturnAttendanceStatusBasedOnArrivalTime(LocalDateTime dateTime, AttendanceState excepted) {
        // when
        AttendanceState result = AttendanceState.evaluate(dateTime);

        // then
        assertThat(result).isEqualTo(excepted);
    }

    private static Stream<Arguments> shouldReturnAttendanceStatusBasedOnArrivalTime() {
        LocalDate nowDate = dateGenerator.generate();

        return Stream.of(
                Arguments.of(LocalDateTime.of(nowDate, LocalTime.of(9, 59)), AttendanceState.ATTENDANCE),
                Arguments.of(LocalDateTime.of(nowDate, LocalTime.of(10, 0)), AttendanceState.ATTENDANCE),
                Arguments.of(LocalDateTime.of(nowDate, LocalTime.of(10, 5)), AttendanceState.ATTENDANCE),
                Arguments.of(LocalDateTime.of(nowDate, LocalTime.of(10, 6)), AttendanceState.TARDY),
                Arguments.of(LocalDateTime.of(nowDate, LocalTime.of(10, 30)), AttendanceState.TARDY),
                Arguments.of(LocalDateTime.of(nowDate, LocalTime.of(10, 31)), AttendanceState.ABSENCE),
                Arguments.of(LocalDateTime.of(nowDate, LocalTime.MAX), AttendanceState.ABSENCE)
        );
    }

    private static class TestDateGenerator implements DateGenerator {

        @Override
        public LocalDate generate() {
            return LocalDate.of(2025, 3, 19);
        }
    }
}
