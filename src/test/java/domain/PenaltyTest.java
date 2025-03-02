package domain;

import static domain.Penalty.COUNSELING;
import static domain.Penalty.EXPELLED;
import static domain.Penalty.PASS;
import static domain.Penalty.WARNING;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@Nested
public class PenaltyTest {

    @Nested
    @DisplayName("경고 및 면담 기준 테스트")
    class CreatePenaltyTest {

        @ParameterizedTest
        @MethodSource("provideExpelledCount")
        @DisplayName("제적 대상자를 반환한다.")
        void pickExpelled(int lateCount, int absentCount) {
            Penalty penalty = Penalty.of(lateCount, absentCount);
            assertThat(penalty).isEqualTo(EXPELLED);
        }

        static Stream<Arguments> provideExpelledCount() {
            return Stream.of(
                Arguments.arguments(0, 6),
                Arguments.arguments(3, 5)
            );
        }

        @ParameterizedTest
        @MethodSource("provideCounselingCount")
        @DisplayName("제적 대상자를 반환한다.")
        void pickCounseling(int lateCount, int absentCount) {
            Penalty penalty = Penalty.of(lateCount, absentCount);
            assertThat(penalty).isEqualTo(COUNSELING);
        }

        static Stream<Arguments> provideCounselingCount() {
            return Stream.of(
                Arguments.arguments(0, 3),
                Arguments.arguments(3, 2),
                Arguments.arguments(0, 5)
            );
        }

        @ParameterizedTest
        @MethodSource("provideWarningCount")
        @DisplayName("제적 대상자를 반환한다.")
        void pickWarning(int lateCount, int absentCount) {
            Penalty penalty = Penalty.of(lateCount, absentCount);
            assertThat(penalty).isEqualTo(WARNING);
        }

        static Stream<Arguments> provideWarningCount() {
            return Stream.of(
                Arguments.arguments(0, 2),
                Arguments.arguments(3, 1),
                Arguments.arguments(6, 0)
            );
        }

        @ParameterizedTest
        @MethodSource("providePassCount")
        @DisplayName("제적 대상자를 반환한다.")
        void pickPass(int lateCount, int absentCount) {
            Penalty penalty = Penalty.of(lateCount, absentCount);
            assertThat(penalty).isEqualTo(PASS);
        }

        static Stream<Arguments> providePassCount() {
            return Stream.of(
                Arguments.arguments(0, 0),
                Arguments.arguments(0, 1),
                Arguments.arguments(3, 0)
            );
        }
    }
}