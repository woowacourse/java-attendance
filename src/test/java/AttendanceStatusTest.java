import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import domain.AttendanceStatus;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class AttendanceStatusTest {

    @DisplayName("월요일은 13시 5분 이전, 다른 요일은 10시 5분 이전이면 출석으로 판단한다.")
    @ParameterizedTest
    @MethodSource("attendanceDateAndTimeArguments")
    void should_DetermineAttendanceStatus_When_WithinAttendanceTime(LocalDate date, LocalTime time) {
        AttendanceStatus attendanceStatus = AttendanceStatus.from(date, time);

        assertThat(attendanceStatus).isSameAs(AttendanceStatus.ATTENDANCE);
    }

    @DisplayName("월요일은 13시 30분 이전, 다른 요일은 10시 30분 이전이면 지각으로 판단한다.")
    @ParameterizedTest
    @MethodSource("latenessDateAndTimeArguments")
    void should_decideByTime_When_WithinLatenessTime(LocalDate date, LocalTime time) {
        AttendanceStatus attendanceStatus = AttendanceStatus.from(date, time);

        assertThat(attendanceStatus).isSameAs(AttendanceStatus.LATENESS);
    }

    @DisplayName("월요일은 13시 31분 이후, 다른 요일은 10시 31분 이후이면 결석으로 판단한다.")
    @ParameterizedTest
    @MethodSource("absenceDateAndTimeArguments")
    void should_decideByTime_When_WithinAbsenceTime(LocalDate date, LocalTime time) {
        AttendanceStatus attendanceStatus = AttendanceStatus.from(date, time);

        assertThat(attendanceStatus).isSameAs(AttendanceStatus.ABSENCE);
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
