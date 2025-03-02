package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class CrewPenaltyTest {
    @DisplayName("정상: 크루 결석 횟수에 따른 징계 상태 확인")
    @ParameterizedTest
    @CsvSource(value = {"1:NONE", "2:WARNING", "3:MEETING", "4:MEETING", "5:MEETING", "6:EXPEL"}, delimiter = ':')
    void successExecutionPenalty(String penaltyCount, String expectedPenalty) {

        assertThat(CrewPenalty.of(Integer.parseInt(penaltyCount))).isEqualTo(CrewPenalty.valueOf(expectedPenalty));
    }
}
