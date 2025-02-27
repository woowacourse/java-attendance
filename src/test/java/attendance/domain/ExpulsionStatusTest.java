package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ExpulsionStatusTest {

    @CsvSource(value = {
            "6,EXPULSION", "5,INTERVIEW", "3,INTERVIEW", "2,WARNING", "1,NONE"
    })
    @ParameterizedTest
    void 총_결석_횟수를_알려주면_제적_위험_상태를_알려준다(int totalAbsentCount, ExpulsionStatus expectedStatus) {
        assertThat(ExpulsionStatus.findStatusByAbsentCount(totalAbsentCount)).isEqualByComparingTo(expectedStatus);
    }

}
