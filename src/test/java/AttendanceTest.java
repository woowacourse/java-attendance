import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import domain.Attendance;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

public class AttendanceTest {

    @DisplayName("캠퍼스 운영 시간이 아닌 경우 예외를 발생한다.")
    @ParameterizedTest
    @CsvSource({"7,59", "23,1"})
    void should_ThrowException_When_NotInCampusOperatingHours(int hour, int minute) {
        LocalTime time = LocalTime.of(hour, minute);
        LocalDate date = LocalDate.of(2024, 12, 16);

        assertThatThrownBy(() -> new Attendance(date, time))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContainingAll("[ERROR] 캠퍼스 운영 시간이 아닙니다.");
    }

    @DisplayName("휴일인 경우 예외를 발생한다.")
    @ParameterizedTest
    @CsvSource({"12,7", "12,15", "12,25"})
    void should_ThrowException_When_Holiday(int month, int monthOfDay) {
        LocalTime time = LocalTime.of(12, 0);
        LocalDate date = LocalDate.of(2024, month, monthOfDay);

        assertThatThrownBy(() -> new Attendance(date, time))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("등교일이 아닙니다.");
    }

    @DisplayName("출석 날짜와 시간을 기반으로 상태를 출석으로 판단한다.")
    @ParameterizedTest
    @MethodSource("attendanceDateAndTimeArguments")
    void should_DetermineAttendanceStatus_When_GivenDateAndTime(LocalDate date, LocalTime time) {
        Attendance attendance = new Attendance(date, time);

        assertThat(attendance.determineStatus()).isEqualTo("출석");
    }

    @DisplayName("출석 날짜와 시간을 기반으로 상태를 지각으로 판단한다.")
    @ParameterizedTest
    @MethodSource("latenessDateAndTimeArguments")
    void should_DetermineLatenessStatus_When_GivenDateAndTime(LocalDate date, LocalTime time) {
        Attendance attendance = new Attendance(date, time);

        assertThat(attendance.determineStatus()).isEqualTo("지각");
    }

    @DisplayName("출석 날짜와 시간을 기반으로 상태를 결석으로 판단한다.")
    @ParameterizedTest
    @MethodSource("absenceDateAndTimeArguments")
    void should_DetermineAbsenceStatus_When_GivenDateAndTime(LocalDate date, LocalTime time) {
        Attendance attendance = new Attendance(date, time);

        assertThat(attendance.determineStatus()).isEqualTo("결석");
    }

    private static Stream<Arguments> attendanceDateAndTimeArguments() {
        return Stream.of(
                Arguments.arguments(LocalDate.of(2024, 12, 2), LocalTime.of(13, 0)),
                Arguments.arguments(LocalDate.of(2024, 12, 2), LocalTime.of(13, 5)),
                Arguments.arguments(LocalDate.of(2024, 12, 3), LocalTime.of(10, 0)),
                Arguments.arguments(LocalDate.of(2024, 12, 3), LocalTime.of(10, 5))
        );
    }

    private static Stream<Arguments> latenessDateAndTimeArguments() {
        return Stream.of(
                Arguments.arguments(LocalDate.of(2024, 12, 2), LocalTime.of(13, 6)),
                Arguments.arguments(LocalDate.of(2024, 12, 2), LocalTime.of(13, 30)),
                Arguments.arguments(LocalDate.of(2024, 12, 3), LocalTime.of(10, 6)),
                Arguments.arguments(LocalDate.of(2024, 12, 3), LocalTime.of(10, 30))
        );
    }

    private static Stream<Arguments> absenceDateAndTimeArguments() {
        return Stream.of(
                Arguments.arguments(LocalDate.of(2024, 12, 2), LocalTime.of(13, 31)),
                Arguments.arguments(LocalDate.of(2024, 12, 3), LocalTime.of(10, 31))
        );
    }
}
