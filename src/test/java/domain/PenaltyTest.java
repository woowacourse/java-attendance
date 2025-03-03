package domain;

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
            assertThat(penalty).isEqualTo(Penalty.EXPELLED);
        }

        static Stream<Arguments> provideExpelledCount() {
            return Stream.of(
                Arguments.arguments(0, 6),
                Arguments.arguments(3, 5)
            );
        }

        @ParameterizedTest
        @MethodSource("provideCounselingCount")
        @DisplayName("면담 대상자를 반환한다.")
        void pickCounseling(int lateCount, int absentCount) {
            Penalty penalty = Penalty.of(lateCount, absentCount);
            assertThat(penalty).isEqualTo(Penalty.COUNSELING);
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
        @DisplayName("경고 대상자를 반환한다.")
        void pickWarning(int lateCount, int absentCount) {
            Penalty penalty = Penalty.of(lateCount, absentCount);
            assertThat(penalty).isEqualTo(Penalty.WARNING);
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
        @DisplayName("패스 대상자를 반환한다.")
        void pickPass(int lateCount, int absentCount) {
            Penalty penalty = Penalty.of(lateCount, absentCount);
            assertThat(penalty).isEqualTo(Penalty.PASS);
        }

        static Stream<Arguments> providePassCount() {
            return Stream.of(
                Arguments.arguments(0, 0),
                Arguments.arguments(0, 1),
                Arguments.arguments(3, 0)
            );
        }
    }

    @Nested
    @DisplayName("제적 확인 테스트")
    class CheckExpelledWarningTest {

        @ParameterizedTest
        @MethodSource("provideExpelledPenalty")
        @DisplayName("제적 위험 대상자를 확인한다.")
        void checkExpelled(int lateCount, int absentCount) {
            assertThat(Penalty.isNotPass(lateCount, absentCount)).isTrue();
        }

        static Stream<Arguments> provideExpelledPenalty() {
            return Stream.of(
                Arguments.arguments(0, 6),
                Arguments.arguments(7, 9),
                Arguments.arguments(3, 5)
            );
        }

        @ParameterizedTest
        @MethodSource("provideNotExpelledPenalty")
        @DisplayName("제적 위험 대상자가 아님을 확인한다.")
        void checkNotExpelled(int lateCount, int absentCount) {
            assertThat(Penalty.isNotPass(lateCount, absentCount)).isFalse();
        }

        static Stream<Arguments> provideNotExpelledPenalty() {
            return Stream.of(
                Arguments.arguments(0, 0),
                Arguments.arguments(0, 1),
                Arguments.arguments(3, 0)
            );
        }
    }
}