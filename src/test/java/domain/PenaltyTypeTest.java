package domain;

import domain.attendance.PenaltyType;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class PenaltyTypeTest {

    static Stream<Arguments> providePenaltyTypeTestArgs() {
        return Stream.of(
                Arguments.of(-42, PenaltyType.NONE),
                Arguments.of(0, PenaltyType.NONE),
                Arguments.of(2, PenaltyType.WARNING),
                Arguments.of(3, PenaltyType.ONE_ON_ONE),
                Arguments.of(4, PenaltyType.ONE_ON_ONE),
                Arguments.of(5, PenaltyType.ONE_ON_ONE),
                Arguments.of(6, PenaltyType.BAN)
        );
    }

    @ParameterizedTest
    @MethodSource("providePenaltyTypeTestArgs")
    void getPenaltyTypeTest1(int absenceCount, PenaltyType expectedPenaltyType) {
        PenaltyType actualPenaltyType = PenaltyType.getPenaltyType(absenceCount);
        Assertions.assertThat(actualPenaltyType).isEqualTo(expectedPenaltyType);
    }
}