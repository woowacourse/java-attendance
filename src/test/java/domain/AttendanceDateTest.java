package domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
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
    @DisplayName("날짜에 따른 교육 시작 시간 탐색 테스트")
    @MethodSource("provideDateAndEducationStartTime")
    void findEducationStartTimeTest(LocalDate date, LocalTime expected) {
        AttendanceDate attendanceDate = new AttendanceDate(date);
        Assertions.assertThat(attendanceDate.getEducationStartTime()).isEqualTo(expected);
    }

    private static Stream<Arguments> provideDateAndEducationStartTime() {
        return Stream.of(
                Arguments.arguments(LocalDate.of(2024, 12, 2), LocalTime.of(13, 0)),
                Arguments.arguments(LocalDate.of(2024, 12, 3), LocalTime.of(10, 0))
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
}
