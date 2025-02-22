import domain.Attend;
import domain.AttendStatus;
import java.time.LocalTime;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class AttendStatusTest {

    private final LocalTime lateTime = LocalTime.of(10, 5);
    private final LocalTime absenceTime = LocalTime.of(10, 30);

    private static Stream<Arguments> provideStatusAndResult() {
        return Stream.of(
                Arguments.of(AttendStatus.ATTEND, LocalTime.of(10, 0), true),
                Arguments.of(AttendStatus.LATE, LocalTime.of(10, 6), true),
                Arguments.of(AttendStatus.ABSENCE, LocalTime.of(10, 31), true)
        );
    }

    @ParameterizedTest
    @MethodSource("provideStatusAndResult")
    @DisplayName("출석 시간을 기반으로 출결을 판정하는 기능")
    void test(AttendStatus attendStatus, LocalTime time, boolean actual) {
        //given
        Attend attend = Attend.fromTime(time);

        //when
        boolean result = attendStatus.match(attend, lateTime, absenceTime);

        //then
        Assertions.assertThat(result).isEqualTo(actual);
    }
}
