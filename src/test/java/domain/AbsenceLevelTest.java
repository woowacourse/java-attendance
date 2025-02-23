package domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class AbsenceLevelTest {

    @ParameterizedTest
    @CsvSource({
            "0, 0, 정상",
            "1, 0, 정상",
            "2, 0, 경고",
            "3, 0, 면담",
            "4, 0, 면담",
            "5, 0, 면담",
            "6, 0, 제적",
            "1, 3, 경고",
            "2, 3, 면담",
            "4, 3, 면담",
            "4, 6, 제적",
            "5, 0, 면담",
            "5, 3, 제적",
            "6, 9, 제적"
    })
    @DisplayName("결석 및 지각 횟수에 따른 등급 테스트")
    void findAbsenceLevel(int absentCount, int lateCount, String expected) {
        // when
        AbsenceLevel result = AbsenceLevel.findAbsenceLevel(absentCount, lateCount);

        // then
        assertThat(result.getLevel()).isEqualTo(expected);
    }
}
