import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AbsentPolicyTest {
    @Test
    @DisplayName("교육 시간에 맞지 않으면 예외가 발생한다")
    public void validateEducationTimeTest() {
        //given
        AbsentPolicy absentPolicy = new AbsentPolicy();
        LocalTime educationTime = LocalTime.of(9,0);

        //when-then
        assertThatThrownBy(() -> absentPolicy.validateEducationTime(educationTime))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
