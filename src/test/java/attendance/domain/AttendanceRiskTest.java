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
    void 결석_횟수로_위험도를_판단해_반환한다(int absence, AttendanceRisk excepted) {
        // when
        AttendanceRisk result = AttendanceRisk.evaluate(absence);

        // then
        Assertions.assertThat(result).isEqualTo(excepted);
    }

    private static Stream<Arguments> 결석_횟수로_위험도를_판단해_반환한다() {
        return Stream.of(
                Arguments.of(2, AttendanceRisk.WARNING),
                Arguments.of(3, AttendanceRisk.INTERVIEW),
                Arguments.of(6, AttendanceRisk.WEEDING)
        );
    }
}
