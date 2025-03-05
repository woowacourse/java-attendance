package attendance.domain;

import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class AcademicStatusTest {

    @ParameterizedTest
    @MethodSource("lateAndAbsentAndResult")
    void 입력_받은_지각_및_결석_일수로_크루의_학적_상태를_판단한다(final int late, final int absent, final AcademicStatus expectedResult) {

        // given

        // when
        final AcademicStatus result = AcademicStatus.getAcademicStatus(late, absent);

        // then
        Assertions.assertThat(result).isEqualTo(expectedResult);
    }

    public static Stream<Arguments> lateAndAbsentAndResult() {

        return Stream.of(
                Arguments.of(0, 6, AcademicStatus.EXPELLED),
                Arguments.of(0, 3, AcademicStatus.WARN),
                Arguments.of(0, 2, AcademicStatus.INTERVIEW),
                Arguments.of(0, 1, AcademicStatus.NOT),
                Arguments.of(3, 5, AcademicStatus.EXPELLED)
        );
    }
}
