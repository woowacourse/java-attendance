package domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.time.LocalDate;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
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

    @ParameterizedTest
    @DisplayName("날짜에 따른 교육 날짜들 탐색 테스트")
    @MethodSource("provideTodayDate")
    void findEducationDatesTest(LocalDate date, int expected) {
        Assertions.assertThat(AttendanceDate.getPastEducationDates(date).size())
                .isEqualTo(expected);
    }

    private static Stream<Arguments> provideTodayDate() {
        return Stream.of(
                Arguments.arguments(LocalDate.of(2025, 2, 27), 21),
                Arguments.arguments(LocalDate.of(2024, 12, 26), 17)
        );
    }

    @ParameterizedTest
    @DisplayName("월요일 여부 확인 테스트")
    @MethodSource("provideDate")
    void findEducationDatesTest(AttendanceDate attendanceDate, boolean expected) {
        Assertions.assertThat(attendanceDate.isMonday())
                .isEqualTo(expected);
    }

    private static Stream<Arguments> provideDate() {
        return Stream.of(
                Arguments.arguments(new AttendanceDate(LocalDate.of(2024, 12, 2)), true),
                Arguments.arguments(new AttendanceDate(LocalDate.of(2024, 12, 3)), false)
        );
    }
}
