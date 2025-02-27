package domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PenaltyTest {

    @Test
    @DisplayName("제적 위험자에 해당할 경우 제적 위험자 여부도 확인할 수도 있다.")
    void penaltyTest1() {
        assertThat(Penalty.findPenaltyMessageByAttendanceStatusCount(0, 0))
                .isEqualTo(Penalty.NONE.getMessage());
        assertThat(Penalty.findPenaltyMessageByAttendanceStatusCount(0, Penalty.PENALTY_THRESHOLD_WARNING))
                .isEqualTo(Penalty.WARNING.getMessage());
        assertThat(Penalty.findPenaltyMessageByAttendanceStatusCount(0, Penalty.PENALTY_THRESHOLD_INTERVIEW))
                .isEqualTo(Penalty.INTERVIEW.getMessage());
        assertThat(Penalty.findPenaltyMessageByAttendanceStatusCount(0, Penalty.PENALTY_THRESHOLD_EXPULSION))
                .isEqualTo(Penalty.EXPULSION.getMessage());
    }
}