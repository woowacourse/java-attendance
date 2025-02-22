package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class AcademicStatusTest {

    @DisplayName("지각 횟수와 결석 횟수을 통해 크루의 학적 상태를 반환한다.")
    @ParameterizedTest
    @MethodSource("lateCountAndAbsentCountAndStatusResult")
    void 지각_횟수와_결석_횟수을_통해_크루의_학적_상태를_반환한다(int late, int absent, AcademicStatus expectedStatus) {

        // given
        // when
        AcademicStatus resultStatus = AcademicStatus.getAcademicStatus(late, absent);
        // then
        assertThat(resultStatus).isEqualTo(expectedStatus);
    }

    public static Stream<Arguments> lateCountAndAbsentCountAndStatusResult() {
        return Stream.of(
                Arguments.of(4, 5, AcademicStatus.EXPELLED),
                Arguments.of(2, 4, AcademicStatus.INTERVIEW),
                Arguments.of(1, 2, AcademicStatus.WARNING),
                Arguments.of(3, 0, AcademicStatus.NOT)
        );
    }
}
