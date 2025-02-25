import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/*
input : 지각 3회 -> 결석 1회
1. 제적 테스트
2. 면담 테스트
3. 경고 테스트
4. 정상 테스트
 */
public class ExpellPolicyTest {
    @Test
    @DisplayName("결석 회수가 5회 초과이면 제적 대상자이다")
    public void expellPolicyTest() {
        //given
        ExpellPolicy expellPolicy = new ExpellPolicy();
        int lateCount = 0;
        int absentCount = 6;

        //when-then
        assertThat(expellPolicy.checkExpellStatus(lateCount, absentCount)).isEqualTo("제적");

    }
}
