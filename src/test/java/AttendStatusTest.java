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

    private static Stream<Arguments> provideStatusAndResult() {
        return Stream.of(
                Arguments.of(AttendStatus.ATTEND, LocalTime.of(10, 0)),
                Arguments.of(AttendStatus.LATE, LocalTime.of(10, 6)),
                Arguments.of(AttendStatus.ABSENCE, LocalTime.of(10, 31))
        );
    }

    @ParameterizedTest
    @MethodSource("provideStatusAndResult")
    @DisplayName("출석 시간을 기반으로 출결을 판정하는 기능")
    void test(AttendStatus actual, LocalTime time) {
        //given
        Attend attend = Attend.fromTime(time);

        //when
        AttendStatus result = AttendStatus.findAttendStatus(attend);

        //then
        Assertions.assertThat(result).isEqualTo(actual);
    }
}
