package model;

import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class SubjectTypeTest {

    @DisplayName("출석, 지각, 결석 횟수를 통해 제적 위험자인지 결정한다")
    @ParameterizedTest
    @CsvSource({
            "0,0,0,해당없음",
            "0,0,2,경고",
            "0,0,3,면담",
            "0,0,6,제적",

            "0,6,0,경고",
            "0,9,0,면담",
            "0,18,0,제적",

            "3,5,2,면담"
    })
    void determineSubjectTypeTest(final int attendanceCount, final int lateCount,
                                  final int absentCount, final String subjectTypeName) {
        // Given
        Map<AttendanceType, Integer> result = Map.of(
                AttendanceType.ATTENDANCE, attendanceCount,
                AttendanceType.LATE, lateCount,
                AttendanceType.ABSENCE, absentCount
        );

        // When
        SubjectType expected = SubjectType.from(result);

        // Then
        Assertions.assertThat(expected.getName()).isEqualTo(subjectTypeName);
    }
}
