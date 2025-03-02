package attendance.domain;

import java.util.stream.Stream;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class SanctionLevelTest {

    @ParameterizedTest
    @MethodSource("methodForTestGetSanctionLevel")
    @DisplayName("가중치를 입력하면 제적 상태를 반환한다.")
    void test_matchSanctionLevel(int weight, SanctionLevel sanctionLevel) {
        Assertions.assertThat(SanctionLevel.matchLevel(weight)).isEqualTo(sanctionLevel);
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
