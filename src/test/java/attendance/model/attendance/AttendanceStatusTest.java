package attendance.model.attendance;

import static org.assertj.core.api.Assertions.assertThat;

import attendance.model.campus.CampusOperationPolicy;
import java.time.LocalDateTime;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class AttendanceStatusTest {

    private final CampusOperationPolicy campusOperationPolicy = new CampusOperationPolicy();

    private static Stream<Arguments> fromTestCases() {
        return Stream.of(
                Arguments.of(LocalDateTime.of(2024, 12, 2, 13, 5), AttendanceStatus.ATTENDANCE),
                Arguments.of(LocalDateTime.of(2024, 12, 2, 13, 6), AttendanceStatus.LATE),
                Arguments.of(LocalDateTime.of(2024, 12, 2, 13, 30), AttendanceStatus.LATE),
                Arguments.of(LocalDateTime.of(2024, 12, 2, 13, 31), AttendanceStatus.ABSENCE),

                Arguments.of(LocalDateTime.of(2024, 12, 3, 10, 5), AttendanceStatus.ATTENDANCE),
                Arguments.of(LocalDateTime.of(2024, 12, 3, 10, 6), AttendanceStatus.LATE),
                Arguments.of(LocalDateTime.of(2024, 12, 3, 10, 30), AttendanceStatus.LATE),
                Arguments.of(LocalDateTime.of(2024, 12, 3, 10, 31), AttendanceStatus.ABSENCE)
        );
    }

    @DisplayName("일시에 대한 출석 상태를 반환한다.")
    @ParameterizedTest
    @MethodSource("fromTestCases")
    void from(final LocalDateTime dateTime, final AttendanceStatus expected) {

        // When
        final AttendanceStatus actual = AttendanceStatus.from(dateTime, campusOperationPolicy);

        // Then
        assertThat(actual).isEqualTo(expected);
    }
}
