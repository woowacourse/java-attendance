package attendance.domain.checker;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class AttendanceTypeTest {

    final static LocalTime EDUCATION_START_TIME = LocalTime.of(10, 0);

    @DisplayName("기준을 넘긴 시간에 맞춰 출석 상태를 판별할 수 있다")
    @ParameterizedTest
    @MethodSource
    void 기준을_넘긴_시간에_맞춰_출석_상태를_판별할_수_있다(LocalTime arrivalTime, AttendanceType expectedType) {
        AttendanceType actualType = AttendanceType.parse(EDUCATION_START_TIME, arrivalTime);
        assertThat(actualType).isEqualTo(expectedType);
    }

    static Stream<Arguments> 기준을_넘긴_시간에_맞춰_출석_상태를_판별할_수_있다() {
        return Stream.of(
                Arguments.of(EDUCATION_START_TIME, AttendanceType.ATTENDANCE),
                Arguments.of(EDUCATION_START_TIME.plusMinutes(4).plusSeconds(59), AttendanceType.ATTENDANCE),
                Arguments.of(EDUCATION_START_TIME.plusMinutes(5), AttendanceType.LATE),
                Arguments.of(EDUCATION_START_TIME.plusMinutes(29).plusSeconds(59), AttendanceType.LATE),
                Arguments.of(EDUCATION_START_TIME.plusMinutes(30), AttendanceType.ABSENCE),
                Arguments.of(EDUCATION_START_TIME.plusMinutes(30).plusSeconds(1), AttendanceType.ABSENCE)
        );
    }
}