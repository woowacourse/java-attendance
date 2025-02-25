package attendance.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static attendance.domain.AttendanceRisk.COUNSELING;
import static attendance.domain.AttendanceRisk.EXPULSION;
import static attendance.domain.AttendanceRisk.NONE;
import static attendance.domain.AttendanceRisk.WARNING;
import static attendance.domain.AttendanceRisk.find;
import static org.assertj.core.api.Assertions.assertThat;

class AttendanceRiskTest {

    @ParameterizedTest
    @MethodSource()
    @DisplayName("출결 상황에 따라 알맞은 경고를 반환한다.")
    void 출결_상황에_따라_알맞은_경고를_반환한다(int absence, int late, AttendanceRisk type) {
        // when
        AttendanceRisk result = find(absence, late);

        // then
        assertThat(result)
                .isEqualTo(type);
    }

    static Stream<Arguments> 출결_상황에_따라_알맞은_경고를_반환한다() {
        return Stream.of(
                Arguments.of(0, 0, NONE),
                Arguments.of(0, 5, NONE),
                Arguments.of(2, 0, WARNING),
                Arguments.of(0, 8, WARNING),
                Arguments.of(3, 0, COUNSELING),
                Arguments.of(0, 15, COUNSELING),
                Arguments.of(6, 0, EXPULSION),
                Arguments.of(0, 18, EXPULSION)
        );
    }
}
