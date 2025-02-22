package domain.date;

import static org.assertj.core.api.Assertions.assertThat;

import domain.attendance.AttendanceType;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class AttendanceDateTimeTest {

    private static Stream<Arguments> getTestCasesForTestGetAttendanceType() {
        return Stream.of(
                Arguments.of(13, 0, AttendanceType.PRESENT),
                Arguments.of(13, 6, AttendanceType.LATE),
                Arguments.of(14, 0, AttendanceType.ABSENCE)
        );
    }

    @ParameterizedTest
    @MethodSource("getTestCasesForTestGetAttendanceType")
    @DisplayName("조건에 따라 알맞은 출석 타입을 반환한다")
    void testGetAttendanceType(int hour, int minute, AttendanceType expected) {
        // given
        AttendanceDateTime attendanceDateTime = AttendanceDateTime.of(2, hour, minute);

        // when
        AttendanceType actual = attendanceDateTime.getAttendanceType();

        // then
        assertThat(expected).isEqualTo(actual);
    }
}
