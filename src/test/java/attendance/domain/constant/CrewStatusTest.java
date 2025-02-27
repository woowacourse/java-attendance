package attendance.domain.constant;

import attendance.exception.CustomException;
import attendance.exception.ErrorMessage;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class CrewStatusTest {

    @ParameterizedTest
    @CsvSource(value = {"2,2,경고", "3,3,면담", "6,1,면담", "0,5,면담", "6,4,제적"})
    void 올바른_상태_반환_검사(int lateCount, int absentCount, String expectedResult) {

        // given
        CrewStatus crewStatus = CrewStatus.from(lateCount, absentCount);

        // when & then
        Assertions.assertThat(crewStatus.getName()).isEqualTo(expectedResult);
    }

    @Test
    void 제적_경고_학생_없음(){

        // given
        int lateCounts = 0;
        int absentCounts = 1;

        // when & then
        Assertions.assertThatThrownBy(() -> CrewStatus.from(lateCounts, absentCounts))
                .isInstanceOf(CustomException.class).hasMessage(ErrorMessage.NOT_RISK_CREW.getMessage());
    }
}