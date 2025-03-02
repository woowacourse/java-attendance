package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class PenaltyTest {

    @ParameterizedTest
    @MethodSource
    @DisplayName("결석 횟수 기준으로 패널티를 찾는다.")
    void test1(final int count, final Penalty penalty) {
        //should
        assertThat(Penalty.findByAbsenceCount(count)).isEqualTo(penalty);
    }

    private static Stream<Arguments> test1() {
        return Stream.of(
                Arguments.of(5, Penalty.INTERVIEW),
                Arguments.of(6, Penalty.EXPLUSION),
                Arguments.of(2, Penalty.WARNING),
                Arguments.of(1, Penalty.NONE),
                Arguments.of(0, Penalty.NONE)
        );
    }
}
