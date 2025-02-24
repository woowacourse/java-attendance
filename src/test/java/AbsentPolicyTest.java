import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AbsentPolicyTest {
    @Test
    @DisplayName("교육 시작 시간으로부터 5분 초과는 지각으로 간주한다")
    public void validateEducationTimeTest() {
        //given
        AbsentPolicy absentPolicy = new AbsentPolicy();
        LocalTime educationTime = LocalTime.of(10,6);

        //when-then
        assertThat(absentPolicy.checkAttendanceStatus(educationTime)).isEqualTo("지각");
    }
}
