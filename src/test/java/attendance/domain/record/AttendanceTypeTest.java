package attendance.domain.record;

import static attendance.domain.record.AttendanceType.ATTENDANCE;
import static attendance.domain.record.AttendanceType.EXPULSION;
import static attendance.domain.record.AttendanceType.LATE;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class AttendanceTypeTest {

    final static LocalTime START_TIME = LocalTime.of(10, 0, 0);
    final static LocalTime LATE_START_TIME = LocalTime.of(10, LATE.getOverMinutes(), 0);
    final static LocalTime EXPULSION_START_TIME = LocalTime.of(10, EXPULSION.getOverMinutes(), 0);

    @DisplayName("기준 시간과 실제 도착 시간을 통해 출석 상태를 확인한다")
    @ParameterizedTest
    @MethodSource()
    void 기준_시간과_실제_도착_시간을_통해_출석_상태를_확인한다(
            LocalTime arriveTime, AttendanceType expectedType
    ) {
        AttendanceType actualType = AttendanceType.parse(START_TIME, arriveTime);
        assertThat(actualType).isEqualTo(expectedType);
    }

    static Stream<Arguments> 기준_시간과_실제_도착_시간을_통해_출석_상태를_확인한다() {
        return Stream.of(
                Arguments.of(START_TIME.minusSeconds(1), ATTENDANCE),
                Arguments.of(START_TIME, ATTENDANCE),
                Arguments.of(LATE_START_TIME.minusSeconds(1), ATTENDANCE),
                Arguments.of(LATE_START_TIME, LATE),
                Arguments.of(EXPULSION_START_TIME.minusSeconds(1), LATE),
                Arguments.of(EXPULSION_START_TIME, EXPULSION),
                Arguments.of(EXPULSION_START_TIME.plusSeconds(1), EXPULSION)
        );
    }
}
