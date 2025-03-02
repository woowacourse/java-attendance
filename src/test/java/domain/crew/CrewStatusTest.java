package domain.crew;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

import exception.ErrorException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class CrewStatusTest {

    @ParameterizedTest
    @CsvSource({"1,PASS", "2,WARNING", "3,CONSULT", "6,EXPEL"})
    @DisplayName("크루 상태 계산 기능 테스트")
    void 크루_상태_계산_기능_테스트(int absentCount, String crewStatus) {
        // given
        int lateCount = 0;
        // when & then
        assertEquals(CrewStatus.valueOf(crewStatus), CrewStatus.findStatus(lateCount, absentCount));
    }

    @Test
    @DisplayName("크루 상태 계산 예외 테스트")
    void 크루_상태_계산_예외_테스트() {
        // given
        int lateCount = 0;
        int absentCount = -1;
        // when & then
        assertThatThrownBy(() -> CrewStatus.findStatus(lateCount, absentCount))
                .isInstanceOf(ErrorException.class)
                .hasMessageContaining("[ERROR]");
    }

    @ParameterizedTest
    @CsvSource({"1,정상", "2,경고", "3,면담", "6,제적"})
    @DisplayName("크루 상태 설명 기능 테스트")
    void 크루_상태_설명_기능_테스트(int absentCount, String crewStatus) {
        // given
        int lateCount = 0;
        // when & then
        assertEquals(crewStatus, CrewStatus.findStatus(lateCount, absentCount).getDescription());
    }
}
