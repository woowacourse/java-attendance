package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class AttendanceStatusTest {

    public static Stream<Arguments> findAttendanceStatusTest() {
        return Stream.of(
                Arguments.of(0, 0, AttendanceStatus.NORMAL),
                Arguments.of(0, 1, AttendanceStatus.NORMAL),
                Arguments.of(0, 2, AttendanceStatus.WARNING),
                Arguments.of(3, 1, AttendanceStatus.WARNING),
                Arguments.of(5, 1, AttendanceStatus.WARNING),
                Arguments.of(0, 3, AttendanceStatus.INTERVIEW),
                Arguments.of(0, 4, AttendanceStatus.INTERVIEW),
                Arguments.of(6, 3, AttendanceStatus.INTERVIEW),
                Arguments.of(0, 6, AttendanceStatus.EXPULSION),
                Arguments.of(4, 5, AttendanceStatus.EXPULSION)
        );
    }

    @ParameterizedTest
    @DisplayName("제적 상태 조회 테스트")
    @MethodSource
    void findAttendanceStatusTest(int lateCount, int absenceCount, AttendanceStatus expected) {
        // when
        AttendanceStatus attendanceStatus = AttendanceStatus.findAttendanceStatus(lateCount, absenceCount);
        // then
        assertThat(attendanceStatus).isEqualTo(expected);
    }
}