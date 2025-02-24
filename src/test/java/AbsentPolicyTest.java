import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class AbsentPolicyTest {
    @Test
    @DisplayName("교육 시작 시간으로부터 5분 초과는 지각으로 간주한다")
    public void checkAttendanceStatusTest() {
        //given
        AbsentPolicy absentPolicy = new AbsentPolicy();
        LocalDateTime educationDateTime = LocalDateTime.of(2024,12,10,10,6);

        //when-then
        assertThat(absentPolicy.checkAttendanceStatus(educationDateTime)).isEqualTo("지각");
    }

}
