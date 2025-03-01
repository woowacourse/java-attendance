package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class RiskTest {

    @ParameterizedTest
    @CsvSource({
        "3,1,WARNING",
        "6,1,INTERVIEW",
        "0,6,EXPULSION",
    })
    @DisplayName("지각, 결석 횟수에 맞게 제적 대상자를 판단한다")
    void fromTest(int lateness, int absence, Risk risk) {
        Map<AttendanceStatus, Integer> param = Map.of(
            AttendanceStatus.LATENESS, lateness,
            AttendanceStatus.ABSENCE, absence);

        assertThat(Risk.of(param)).isEqualTo(risk);
    }
}
