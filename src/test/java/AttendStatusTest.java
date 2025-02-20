import domain.Attend;
import domain.AttendStatus;
import java.time.LocalTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendStatusTest {

    private final LocalTime lateTime = LocalTime.of(10, 5);
    private final LocalTime absenceTime = LocalTime.of(10, 30);

    @Test
    @DisplayName("출석 시간을 기반으로 출결을 판정하는 기능")
    void test() throws Exception {
        //given
        Attend attend = Attend.of("10:00");

        //when
        AttendStatus result = AttendStatus.calculateAttend(attend, lateTime, absenceTime);

        //then
        Assertions.assertThat(result).isEqualTo(AttendStatus.ATTEND);
    }

    @Test
    @DisplayName("출석 시간을 기반으로 출결을 판정하는 기능 - 지각")
    void test2() throws Exception {
        //given
        Attend attend = Attend.of("10:06");

        //when
        AttendStatus result = AttendStatus.calculateAttend(attend, lateTime, absenceTime);

        //then
        Assertions.assertThat(result).isEqualTo(AttendStatus.LATE);
    }

    @Test
    @DisplayName("출석 시간을 기반으로 출결을 판정하는 기능 - 결석")
    void test3() throws Exception {
        //given
        Attend attend = Attend.of("10:31");

        //when
        AttendStatus result = AttendStatus.calculateAttend(attend, lateTime, absenceTime);

        //then
        Assertions.assertThat(result).isEqualTo(AttendStatus.ABSENCE);
    }
}
