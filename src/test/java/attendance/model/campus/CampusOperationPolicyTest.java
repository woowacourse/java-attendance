package attendance.model.campus;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class CampusOperationPolicyTest {

    private final CampusOperationPolicy policy = new CampusOperationPolicy();

    private static Stream<Arguments> isCampusOpenTestCases() {
        return Stream.of(
                Arguments.of(LocalTime.of(7, 59), false),
                Arguments.of(LocalTime.of(8, 0), true),
                Arguments.of(LocalTime.of(8, 1), true),
                Arguments.of(LocalTime.of(22, 59), true),
                Arguments.of(LocalTime.of(23, 0), true),
                Arguments.of(LocalTime.of(23, 1), false)
        );
    }

    @DisplayName("특정 시간에 캠퍼스가 열려있는지 확인한다.")
    @ParameterizedTest
    @MethodSource("isCampusOpenTestCases")
    void isCampusOpen(final LocalTime time, final boolean expected) {

        // When
        final boolean actual = policy.isCampusOpen(time);

        // Then
        assertThat(actual).isEqualTo(expected);
    }
}
