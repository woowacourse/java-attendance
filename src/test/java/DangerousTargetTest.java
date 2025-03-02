import static org.assertj.core.api.Assertions.assertThat;

import domain.DangerousTarget;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DangerousTargetTest {

    @DisplayName("지각 횟수와 결석 횟수를 통해 제적대상자인지를 판별할 수 있다")
    @Test
    void canCheckIsDismissalCrew() {
        int lateCount = 3;
        int absentCount = 2;

        assertThat(DangerousTarget.getWarningStatus(lateCount, absentCount)).isEqualTo(DangerousTarget.ONE_ON_ONE);
    }

    @DisplayName("지각 횟수와 결석 횟수를 통해 제적대상자가 아닌지인지를 판별할 수 있다")
    @Test
    void canCheckIsGoodCrew() {
        int lateCount = 0;
        int absentCount = 0;

        assertThat(DangerousTarget.getWarningStatus(lateCount, absentCount)).isEqualTo(DangerousTarget.SAFE);
    }
}
