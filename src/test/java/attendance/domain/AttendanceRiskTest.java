package attendance.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

@DisplayName("출결 위험도 테스트")
class AttendanceRiskTest {

    @ParameterizedTest
    @MethodSource
    @DisplayName("결석 횟수로 위험도를 판단해 반환한다")
    void 결석과_지각_횟수로_위험도를_판단해_반환한다(int absence, int tardy, AttendanceRisk excepted) {
        // when
        AttendanceRisk result = AttendanceRisk.evaluate(absence, tardy);

        // then
        Assertions.assertThat(result).isEqualTo(excepted);
    }

    private static Stream<Arguments> 결석과_지각_횟수로_위험도를_판단해_반환한다() {
        return Stream.of(
                Arguments.of(1, 3, AttendanceRisk.WARNING),
                Arguments.of(2, 2, AttendanceRisk.WARNING),
                Arguments.of(2, 3, AttendanceRisk.INTERVIEW),
                Arguments.of(5, 2, AttendanceRisk.INTERVIEW),
                Arguments.of(5, 3, AttendanceRisk.WEEDING),
                Arguments.of(7, 0, AttendanceRisk.WEEDING)
        );
    }
}
