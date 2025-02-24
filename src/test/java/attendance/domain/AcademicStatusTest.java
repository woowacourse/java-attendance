package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class AcademicStatusTest {

    @DisplayName("지각과 결석의 횟수에 의해 학적 상태가 결정된다.")
    @ParameterizedTest
    @CsvSource(value = {"6,제적", "3,면담", "2,경고", "1,없음"})
    void 지각과_결석의_횟수에_의해_학적_상태가_결정된다(int count, String academicStatusValue) {

        // given

        // when
        String academicStatus = AcademicStatus.getAcademicStatus(0, count);

        // then
        assertThat(academicStatus).isEqualTo(academicStatusValue);
    }

}
