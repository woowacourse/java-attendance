package attendance.domain;

import java.time.LocalDate;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class AttendanceStatusTest {

    @ParameterizedTest
    @MethodSource("attendanceTimeAndResult")
    void 입력_받은_출석_시간에_따라_출석_상태를_판단할_수_있다(AttendanceTime attendanceTime, AttendanceStatus expectedResult) {

        // given

        // when
        AttendanceStatus result = AttendanceStatus.getAttendanceStatus(attendanceTime);

        // then
        Assertions.assertThat(result).isEqualTo(expectedResult);
    }

    private static Stream<Arguments> attendanceTimeAndResult() {

        return Stream.of(
                Arguments.of(new AttendanceTime(LocalDate.of(2025, 2, 27), 10, 5), AttendanceStatus.ATTEND),
                Arguments.of(new AttendanceTime(LocalDate.of(2025, 2, 27), 10, 30), AttendanceStatus.LATE),
                Arguments.of(new AttendanceTime(LocalDate.of(2025, 2, 27), 10, 31), AttendanceStatus.ABSENT),
                Arguments.of(new AttendanceTime(LocalDate.of(2025, 2, 24), 13, 5), AttendanceStatus.ATTEND),
                Arguments.of(new AttendanceTime(LocalDate.of(2025, 2, 24), 13, 30), AttendanceStatus.LATE),
                Arguments.of(new AttendanceTime(LocalDate.of(2025, 2, 24), 13, 31), AttendanceStatus.ABSENT)
        );
    }
}
