import static org.assertj.core.api.Assertions.assertThat;

import domain.AttendCount;
import domain.WarningStatus;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class WarningStatusTest {

    private static Arguments createAttendCountArgument(long attendCount, long lateCount,
                                                       long absenceCount, WarningStatus warningStatus) {
        return Arguments.of(new AttendCount(attendCount, lateCount, absenceCount), warningStatus);
    }

    private static Stream<Arguments> provideAttendCount() {
        return Stream.of(
                createAttendCountArgument(0, 0, 1, WarningStatus.PASS),
                createAttendCountArgument(0, 3, 0, WarningStatus.PASS),
                createAttendCountArgument(0, 0, 2, WarningStatus.WARNING),
                createAttendCountArgument(0, 6, 0, WarningStatus.WARNING),
                createAttendCountArgument(0, 0, 3, WarningStatus.INTERVIEW),
                createAttendCountArgument(0, 4, 4, WarningStatus.INTERVIEW),
                createAttendCountArgument(0, 0, 6, WarningStatus.EXPEL),
                createAttendCountArgument(0, 6, 4, WarningStatus.EXPEL)
        );
    }

    @ParameterizedTest
    @MethodSource("provideAttendCount")
    @DisplayName("총 결석 횟수를 기반으로 경고 유형을 판정해야 한다")
    void judgeWarningStatusUsingTotalAbsenceCount(AttendCount attendCount, WarningStatus expected) {
        //when
        WarningStatus actual = WarningStatus.judgeWarningStatus(attendCount);

        //then
        assertThat(actual).isEqualTo(expected);
    }
}
