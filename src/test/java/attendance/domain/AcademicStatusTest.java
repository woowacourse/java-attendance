package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class AcademicStatusTest {

    @DisplayName("결석 횟수에 따라 학적 상태가 결정된다.")
    @ParameterizedTest
    @CsvSource(value = {
            "0,0, NOT", "2,0, WARNING", "3,0, INTERVIEW", "6,0, EXPELLED"
    })
    void 결석_횟수에_따라_학적_상태가_결정된다(int absent, int late, AcademicStatus status) {

        // when & then
        assertThat(status).isEqualTo(AcademicStatus.getStatus(late, absent));
    }
}
