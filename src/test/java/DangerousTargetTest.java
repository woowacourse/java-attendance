import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class DangerousTargetTest {
    @DisplayName("지각 횟수와 결석 횟수를 통해 제적대상자인지를 판별할 수 있다")
    @Test
    void test1(){
        int lateCount=3;
        int absentCount=2;

        assertThat(DangerousTarget.getWarningStatus(lateCount,absentCount)).isEqualTo(DangerousTarget.ONE_ON_ONE);
    }

}
