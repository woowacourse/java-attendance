package attendance.domain;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import attendance.exception.AttendanceArgumentException;

class SanctionLevelTest {

    @ParameterizedTest
    @MethodSource("methodForTestGetSanctionLevel")
    @DisplayName("가중치를 입력하면 제적 상태를 반환한다.")
    void test_matchSanctionLevel(int weight, SanctionLevel sanctionLevel) {
        assertThat(SanctionLevel.matchLevel(weight)).isEqualTo(sanctionLevel);
    }

    @Test
    @DisplayName("음수 가중치를 입력할 경우, 예외가 발생한다.")
    void error_matchWeightNotPositive() {
        assertThatThrownBy(() -> SanctionLevel.matchLevel(-1))
            .isInstanceOf(AttendanceArgumentException.class)
            .hasMessageContaining("가중치는 음수가 될 수 없습니다");
    }

    private static Stream<Arguments> methodForTestGetSanctionLevel() {
        return Stream.of(
            Arguments.arguments(1, SanctionLevel.NONE),
            Arguments.arguments(2, SanctionLevel.WARNING),
            Arguments.arguments(4, SanctionLevel.NEED_MEETING),
            Arguments.arguments(8, SanctionLevel.DISMISS)
        );
    }
}
