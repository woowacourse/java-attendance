package attendance;

import attendance.domain.Attendance;
import attendance.domain.dto.AttendanceResult;
import java.time.LocalDateTime;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class AttendanceTest {


    private static Stream<Arguments> determineAttendanceStatusTestCases() {
        //given
        return Stream.of(
                Arguments.of(LocalDateTime.of(2024, 12, 3, 9, 50), "출석"),
                Arguments.of(LocalDateTime.of(2024, 12, 3, 10, 6), "지각"),
                Arguments.of(LocalDateTime.of(2024, 12, 3, 10, 50), "결석"),
                Arguments.of(LocalDateTime.of(2024, 12, 2, 13, 0), "출석"),
                Arguments.of(LocalDateTime.of(2024, 12, 2, 13, 6), "지각"),
                Arguments.of(LocalDateTime.of(2024, 12, 2, 13, 31), "결석")
        );
    }

    @ParameterizedTest
    @MethodSource("determineAttendanceStatusTestCases")
    @DisplayName("등교시간에 따라 출석상태가 출석,지각,등교로 등록되는지 테스트")
    void determineAttendanceStatus(LocalDateTime localDateTime, String status) {
        //when
        Attendance attendance = new Attendance(localDateTime);
        //then
        Assertions.assertThat(attendance.getAttendanceStatus()).isEqualTo(status);
    }

    @Test
    @DisplayName("지정된 시간 이외에 등교하면 예외를 발생한다.")
    void unspecifiedAttendanceThrowException() {
        //given
        LocalDateTime attendanceDateTime = LocalDateTime.of(2024, 12, 4, 7, 30);

        //expected
        Assertions.assertThatThrownBy(() -> new Attendance(attendanceDateTime))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 지정된 시간이 아니면 등교가 불가능합니다.");
    }

    @Test
    @DisplayName("주말 및 공휴일에는 출석을 받지 않는다.")
    void weekendAndHolidayAttendanceThrowException() {
        //given
        LocalDateTime attendanceDateTime = LocalDateTime.of(2024, 12, 7, 10, 0);

        //expected
        Assertions.assertThatIllegalArgumentException()
                .isThrownBy(() -> new Attendance(attendanceDateTime))
                .withMessage("주말 및 공휴일은 출석을 받지않습니다");
    }

    @Test
    @DisplayName("출석 후 출석 기록을 확인할 수 있다")
    void afterAttendanceCheckAttendanceRecord() {
        //given
        Attendance attendance = new Attendance(LocalDateTime.of(2024, 12, 4, 9, 50));

        //when
        AttendanceResult attendanceResult = AttendanceResult.from(attendance);

        //then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(attendanceResult.attendanceMonth()).isEqualTo(12);
            softly.assertThat(attendanceResult.attendanceDay()).isEqualTo(4);
            softly.assertThat(attendanceResult.attendanceHour()).isEqualTo(9);
            softly.assertThat(attendanceResult.attendanceMinute()).isEqualTo(50);
            softly.assertThat(attendanceResult.attendanceStatus()).isEqualTo("출석");
        });

    }
}
