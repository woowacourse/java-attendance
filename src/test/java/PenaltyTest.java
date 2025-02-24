import domain.Penalty;
import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class PenaltyTest {

    @DisplayName("제적 상태를 반환한다.")
    @ParameterizedTest
    @MethodSource("expelledSource")
    void getPenalty_1(List<Integer> list) {
        int absenceCount = list.get(0);
        int latenessCount = list.get(1);
        Assertions.assertThat(Penalty.of(absenceCount, latenessCount)).isEqualTo(Penalty.EXPELLED);
    }

    private static Stream<Arguments> expelledSource() {
        return Stream.of(
            Arguments.arguments(List.of(6, 0)),
            Arguments.arguments(List.of(4, 6)),
            Arguments.arguments(List.of(4, 7))
        );
    }

    @DisplayName("면담 상태를 반환한다.")
    @ParameterizedTest
    @MethodSource("counselingSource")
    void getPenalty_2(List<Integer> list) {
        int absenceCount = list.get(0);
        int latenessCount = list.get(1);
        Assertions.assertThat(Penalty.of(absenceCount, latenessCount)).isEqualTo(Penalty.COUNSELING);
    }

    private static Stream<Arguments> counselingSource() {
        return Stream.of(
            Arguments.arguments(List.of(3, 0)),
            Arguments.arguments(List.of(2, 6)),
            Arguments.arguments(List.of(0, 9))
        );
    }

    @DisplayName("경고 상태를 반환한다.")
    @ParameterizedTest
    @MethodSource("warningSource")
    void getPenalty_3(List<Integer> list) {
        int absenceCount = list.get(0);
        int latenessCount = list.get(1);
        Assertions.assertThat(Penalty.of(absenceCount, latenessCount)).isEqualTo(Penalty.WARNING);
    }

    private static Stream<Arguments> warningSource() {
        return Stream.of(
            Arguments.arguments(List.of(2, 0)),
            Arguments.arguments(List.of(1, 3)),
            Arguments.arguments(List.of(0, 6))
        );
    }
}
