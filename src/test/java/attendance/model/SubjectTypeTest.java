package attendance.model;

import attendance.domain.model.SubjectType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class SubjectTypeTest {

    @DisplayName("출석, 지각, 결석 횟수를 통해 제적 위험자인지 결정한다")
    @ParameterizedTest
    @CsvSource({
            "0,0,해당없음",
            "0,2,경고",
            "0,3,면담",
            "0,6,제적",

            "6,0,경고",
            "9,0,면담",
            "18,0,제적",

            "5,2,면담"
    })
    void determineSubjectTypeTest(final int lateCount,
                                  final int absentCount, final String subjectTypeName) {
        // Given

        // When
        SubjectType expected = SubjectType.from(absentCount, lateCount);

        // Then
        Assertions.assertThat(expected.getName()).isEqualTo(subjectTypeName);
    }
}
