package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class AttendanceStatusTest {

    @DisplayName("시간 조건에 따라 출석한 시간의 상태를 반환한다.")
    @ParameterizedTest
    @MethodSource("attendanceTimeAndStatus")
    void 시간_조건에_따라_출석한_시간의_상태를_반환한다(AttendanceTime attendanceTime, AttendanceStatus expectedStatus) {

        // given
        // when
        AttendanceStatus resultStatus = AttendanceStatus.getAttendanceStatusWithCondition(
                attendanceTime,
                10, 5, 30);
        // then
        assertThat(resultStatus).isEqualTo(expectedStatus);
    }

    private static Stream<Arguments> attendanceTimeAndStatus() {
        LocalDate testDate = LocalDate.of(2025, 2, 13);
        return Stream.of(
                Arguments.of(new AttendanceTime(testDate, "10", "05", false), AttendanceStatus.ATTEND),
                Arguments.of(new AttendanceTime(testDate, "10", "30", false), AttendanceStatus.LATE),
                Arguments.of(new AttendanceTime(testDate, "10", "31", false), AttendanceStatus.ABSENT)
        );
    }
}
