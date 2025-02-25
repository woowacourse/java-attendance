package domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.time.LocalDate;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class AttendanceDateTest {

    @DisplayName("LocalDate 기반 AttendanceDate 생성 테스트")
    @Test
    void generateAttendanceDateTest() {
        assertDoesNotThrow(() -> new AttendanceDate(LocalDate.of(2024, 12, 2)));
    }

    @ParameterizedTest
    @DisplayName("캠퍼스 운영 날짜가 아닌 경우 예외 발생 테스트")
    @MethodSource("provideCampusDate")
    void campusNotRunningTest(LocalDate attendanceDate) {
        assertThatThrownBy(() -> new AttendanceDate(attendanceDate))
                .isInstanceOf(IllegalArgumentException.class);
    }

    private static Stream<Arguments> provideCampusDate() {
        return Stream.of(
                Arguments.arguments(LocalDate.of(2024, 12, 1)),
                Arguments.arguments(LocalDate.of(2024, 12, 7)),
                Arguments.arguments(LocalDate.of(2024, 12, 25))
        );
    }
}
