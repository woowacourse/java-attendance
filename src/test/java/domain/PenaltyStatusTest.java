package domain;

import static constants.NumberConstants.EXPULSION_COUNT;
import static constants.NumberConstants.INTERVIEW_COUNT;
import static constants.NumberConstants.WARNING_COUNT;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PenaltyStatusTest {
    @Test
    @DisplayName("패널티_횟수를_넣으면_패널티_상태를_반환한다")
    void 패널티_횟수를_넣으면_패널티_상태를_반환한다() {
        assertThat(PenaltyStatus.getByPenaltyCount(EXPULSION_COUNT + 1)).isEqualTo(PenaltyStatus.EXPULSION);
        assertThat(PenaltyStatus.getByPenaltyCount(INTERVIEW_COUNT)).isEqualTo(PenaltyStatus.INTERVIEW);
        assertThat(PenaltyStatus.getByPenaltyCount(WARNING_COUNT)).isEqualTo(PenaltyStatus.WARNING);
    }
}