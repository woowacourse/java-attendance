package domain;

import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;


class PunishmentTest {

    @ParameterizedTest
    @MethodSource("methodSources")
    void 결석_개수에_맞는_처벌_종류를_선정한다(int absenceCount, Punishment expectedPunishment) {
        // given
        // when
        final Punishment punishment = Punishment.findByAbsenceCount(absenceCount);
        // than
        Assertions.assertThat(punishment).isEqualTo(expectedPunishment);
    }

    private static Stream<Arguments> methodSources() {
        return Stream.of(
                Arguments.arguments(2, Punishment.WARNING),
                Arguments.arguments(3, Punishment.INTERVIEW),
                Arguments.arguments(6, Punishment.EXPULSION)
        );
    }
}
