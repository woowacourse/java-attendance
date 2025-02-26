package attendance.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("출결 상태 테스트")
class AttendanceStateTest {

    @ParameterizedTest
    @MethodSource
    @DisplayName("등교 시간과 시작 시간의 차이로 출결 상황을 반환한다")
    void shouldReturnStateBasedOnTimeDifference(int overTime, AttendanceState excepted) {
        // when
        AttendanceState result = AttendanceState.evaluate(overTime);

        // then
        assertThat(result).isEqualTo(excepted);
    }

    private static Stream<Arguments> shouldReturnStateBasedOnTimeDifference() {
        return Stream.of(
                Arguments.of(-1, AttendanceState.ATTENDANCE),
                Arguments.of(0, AttendanceState.ATTENDANCE),
                Arguments.of(5, AttendanceState.ATTENDANCE),
                Arguments.of(6, AttendanceState.TARDY),
                Arguments.of(30, AttendanceState.TARDY),
                Arguments.of(31, AttendanceState.ABSENCE),
                Arguments.of(Integer.MAX_VALUE, AttendanceState.ABSENCE)
        );
    }
}
