import domain.AttendCount;
import domain.WarningStatus;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class AttendCountTest {

    private static Stream<Arguments> provideAttendCountAndWarningStatus() {
        return Stream.of(
                Arguments.of(new AttendCount(0, 0, 0), WarningStatus.CLEAR),
                Arguments.of(new AttendCount(0, 0, 1), WarningStatus.CLEAR),
                Arguments.of(new AttendCount(0, 0, 2), WarningStatus.WARNING),
                Arguments.of(new AttendCount(0, 3, 1), WarningStatus.WARNING),
                Arguments.of(new AttendCount(0, 0, 3), WarningStatus.INTERVIEW),
                Arguments.of(new AttendCount(0, 3, 3), WarningStatus.INTERVIEW),
                Arguments.of(new AttendCount(0, 0, 6), WarningStatus.EXPEL),
                Arguments.of(new AttendCount(0, 4, 5), WarningStatus.EXPEL)
        );
    }

    @ParameterizedTest
    @MethodSource("provideAttendCountAndWarningStatus")
    @DisplayName("제적 위험자를 계산하는 기능")
    void countWarningCrews(AttendCount attendCount, WarningStatus actual) {
        //when
        WarningStatus warningStatus = attendCount.judgeWarning();

        //then
        Assertions.assertThat(warningStatus).isEqualTo(actual);
    }
}
