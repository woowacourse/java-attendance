import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PenaltyResultOfCrewTest {
    @Test
    @DisplayName("from 을 이용해서 객체를 생성한다")
    void test1() {
        // given
        PenaltyResultOfCrew penaltyResultOfCrew = PenaltyResultOfCrew.from(new Crew("히로"), AttendanceTypeCount.from(3, 3));
        PenaltyType penaltyType = PenaltyType.findByAbsenceCount(4);

        // when & then
        assertThat(penaltyResultOfCrew.penaltyType()).isEqualTo(penaltyType);
    }
}
