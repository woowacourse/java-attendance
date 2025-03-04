import static org.assertj.core.api.Assertions.assertThat;

import domain.AttendCount;
import domain.AttendStatus;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendCountTest {

    @Test
    @DisplayName("출석 상태의 개수를 세야 한다.")
    void countAttendStatus() {
        //given
        List<AttendStatus> attendStatus = Arrays.stream(AttendStatus.values()).toList();

        //when
        AttendCount actual = AttendCount.createCount(attendStatus);

        //then
        AttendCount expected = new AttendCount(1, 1, 1);
        assertThat(actual).isEqualTo(expected);
    }
}
