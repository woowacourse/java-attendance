package attendance.domain;

import java.time.LocalDateTime;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class AttendanceTest {


    private static Stream<Arguments> checkAttendanceStatusTestCases() {
        return Stream.of(
                Arguments.of(LocalDateTime.of(2024, 12, 3, 9, 50),"출석"),
                Arguments.of(LocalDateTime.of(2024, 12, 3, 10, 6),"지각"),
                Arguments.of(LocalDateTime.of(2024, 12, 3, 10, 50),"결석")
        );
    }

    @DisplayName("등교시간에 따라 출석상태가 출석,지각,등교로 등록되는지 테스트")
    @ParameterizedTest
    @MethodSource("checkAttendanceStatusTestCases")
    void checkAttendance(LocalDateTime localDateTime, String status) {
        Attendance attendance = new Attendance(localDateTime);
        Assertions.assertThat(attendance.getAttendanceStatus()).isEqualTo(status);
    }

}
