package domain;

import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class AbsencePolicyTest {

    @DisplayName("지각과 결석 횟수를 입력받아 경고 위험자를 확인할 수 있다.")
    @ParameterizedTest
    @MethodSource("warningProvider")
    void getWarningPolicy(List<Integer> count) {
        // when
        String actual = AbsencePolicy.getAbsencePolicy(count.get(0), count.get(1)).getDescription();

        // then
        Assertions.assertThat(actual).isEqualTo("경고");
    }

    static Stream<Arguments> warningProvider() {
        return Stream.of(
                Arguments.of(List.of(0, 6)),
                Arguments.of(List.of(0, 8)),
                Arguments.of(List.of(2, 0)),
                Arguments.of(List.of(2, 2))

        );
    }

    @DisplayName("지각과 결석 횟수를 입력받아 면담 위험자를 확인할 수 있다.")
    @ParameterizedTest
    @MethodSource("interviewProvider")
    void getInterviewPolicy(List<Integer> count) {
        // when
        String actual = AbsencePolicy.getAbsencePolicy(count.get(0), count.get(1)).getDescription();

        // then
        Assertions.assertThat(actual).isEqualTo("면담");
    }

    static Stream<Arguments> interviewProvider() {
        return Stream.of(
                Arguments.of(List.of(0, 9)),
                Arguments.of(List.of(2, 3)),
                Arguments.of(List.of(4, 3)),
                Arguments.of(List.of(4, 5)),
                Arguments.of(List.of(0, 17))
        );
    }

    @DisplayName("지각과 결석 횟수를 입력받아 제적 위험자를 확인할 수 있다.")
    @ParameterizedTest
    @MethodSource("dismissedProvider")
    void getDismissedPolicy(List<Integer> count) {
        // when
        String actual = AbsencePolicy.getAbsencePolicy(count.get(0), count.get(1)).getDescription();

        // then
        Assertions.assertThat(actual).isEqualTo("제적");
    }

    static Stream<Arguments> dismissedProvider() {
        return Stream.of(
                Arguments.of(List.of(4, 6)),
                Arguments.of(List.of(6, 0)),
                Arguments.of(List.of(0, 18))
        );
    }

}
