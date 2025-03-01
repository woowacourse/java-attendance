import static org.assertj.core.api.Assertions.assertThat;

import domain.AttendCount;
import domain.WarningStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class WarningStatusTest {

    @Test
    @DisplayName("출석 상태 횟수를 기반으로 총 결석 횟수를 계산해야 한다")
    void calculateTotalAbsenceCount() {
        //given
        AttendCount attendCount = new AttendCount(0, 4, 1);

        //when
        long actual = WarningStatus.calculateTotalAbsence(attendCount);

        //then
        assertThat(actual).isEqualTo(2);
    }
}
