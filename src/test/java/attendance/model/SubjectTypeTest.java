package attendance.model;

import static org.assertj.core.api.Assertions.assertThat;

import attendance.domain.model.SubjectType;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

class SubjectTypeTest {

    @DisplayName("출석, 지각, 결석 횟수를 통해 제적 위험자인지 결정한다")
    @ParameterizedTest
    @CsvSource({
            "0,0,NOT_APPLICABLE",
            "0,2,WARNING",
            "0,3,INTERVIEW",
            "0,6,EXPULSION",

            "6,0,WARNING",
            "9,0,INTERVIEW",
            "18,0,EXPULSION",

            "5,2,INTERVIEW"
    })
    void determineSubjectTypeTest(final int lateCount,
                                  final int absentCount, final String subjectTypeName) {
        // Given

        // When
        SubjectType expected = SubjectType.from(absentCount, lateCount);

        // Then
        assertThat(expected.name()).isEqualTo(subjectTypeName);
    }

    @Test
    @DisplayName("지각 횟수를 계산한다")
    void 지각_횟수를_계산한다() {
        // Given
        int lateCount = 2;
        int absentCount = 3;

        // When
        int totalLateCount = SubjectType.calculateTotalLateCount(lateCount, absentCount);

        // Then
        assertThat(totalLateCount).isEqualTo(11);
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("적용 가능한 타입인지 확인한다")
    void 적용_가능한_타입인지_확인한다(final SubjectType subjectType, final boolean expected) {
        // Given

        // When & Then
        assertThat(subjectType.isApplicable()).isEqualTo(expected);
    }

    private static Stream<Arguments> 적용_가능한_타입인지_확인한다() {
        return Stream.of(
                Arguments.of(SubjectType.WARNING, true),
                Arguments.of(SubjectType.INTERVIEW, true),
                Arguments.of(SubjectType.EXPULSION, true),
                Arguments.of(SubjectType.NOT_APPLICABLE, false)
        );
    }
}
