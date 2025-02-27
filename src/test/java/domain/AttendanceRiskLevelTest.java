package domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class AttendanceRiskLevelTest {

    @ParameterizedTest
    @CsvSource(value =
            {
                    "6, EXPULSION",
                    "5,COUNSELING",
                    "2,WARNING",
                    "1,NORMAL"
            }
    )
    void 결석_횟수로_제적_상태_여부를_계산한다(int absenceCount, AttendanceRiskLevel riskLevel) {
        // when // then
        assertThat(AttendanceRiskLevel.calculateByAbsenceCount(absenceCount))
                .isEqualTo(riskLevel);
    }

}
