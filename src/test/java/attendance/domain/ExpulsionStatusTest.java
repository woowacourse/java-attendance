package attendance.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.*;

public class ExpulsionStatusTest {

    @CsvSource(value = {
            "6,EXPULSION",
            "5,INTERVIEW",
            "3,INTERVIEW",
            "2,WARNING",
            "1,NONE"
    })
    @ParameterizedTest
    void 누적_결석_횟수를_받으면_제적_상태를_알려준다(int absentCount, ExpulsionStatus expected) {
        // When
        ExpulsionStatus expulsionStatus = ExpulsionStatus.findByAbsentCount(absentCount);

        // Then
        assertThat(expulsionStatus).isEqualTo(expected);
    }
}
