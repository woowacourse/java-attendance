package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PenaltyPolicyTest {

    @ParameterizedTest
    @CsvSource({
            "0, 0, 1, NONE",
            "0, 0, 2, WARNING",
            "0, 0, 3, MEETING",
            "0, 3, 5, EXPULSION",
            "0, 0, 6, EXPULSION"
    })
    void 제적위험도를_판단한다(int successCount, int lateCount, int absenceCount, PenaltyPolicy expected) {
        //given
        Map<AttendanceType, Integer> counts = Map.of(
                AttendanceType.SUCCESS, successCount,
                AttendanceType.LATE, lateCount,
                AttendanceType.ABSENCE, absenceCount
        );
        //when
        PenaltyPolicy actual = PenaltyPolicy.judgePenalty(counts);
        //then
        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
            "NONE, false",
            "WARNING, true",
            "MEETING, true",
            "EXPULSION, true"
    })
    void 제적위험_상태인지_판단한다(PenaltyPolicy penalty, boolean expected) {
        //when
        boolean isDanger = penalty.isDanger();
        //then
        assertThat(isDanger).isEqualTo(expected);
    }
}
