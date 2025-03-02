package attendance.domain;

import static attendance.fixture.TestFixture.makeAbsenceExceptMonday;
import static attendance.fixture.TestFixture.makeAttendanceExceptMonday;
import static attendance.fixture.TestFixture.makeDateTime;
import static attendance.fixture.TestFixture.makeDecemberDate;
import static attendance.fixture.TestFixture.makeTardinessExceptMonday;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import attendance.view.TimeFormatter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

class CampusSchedulerTest {

    private CampusScheduler campusScheduler;

    @BeforeEach
    void setUp() {
        campusScheduler = new CampusScheduler();
    }

    @Test
    void 운영일인지_확인한다() {
        // Given
        LocalDate attendanceDate = makeDecemberDate(2);

        // When & Then
        assertThatCode(() -> campusScheduler.validateOperationDate(attendanceDate))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 7, 25})
    void 운영일이_아닌_경우_예외가_발생한다(final int day) {
        // Given
        LocalDate attendanceDate = makeDecemberDate(day);

        // When & Then
        assertThatThrownBy(() -> campusScheduler.validateOperationDate(attendanceDate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]",
                        TimeFormatter.makeDateMessage(attendanceDate) + "은 등교일이 아닙니다.");
    }

    @ParameterizedTest
    @CsvSource({
            "1, true",
            "2, false",
            "7, true",
            "25, true",
    })
    void 운영일이_아닌_경우_true를_반환한다(int day, boolean expected) {
        // Given
        LocalDate attendanceDate = makeDecemberDate(day);

        // When & Then
        assertThat(campusScheduler.isNotOperationDate(attendanceDate)).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource
    void 캠퍼스_운영_시간에_따라_출석_상태를_파악한다(final LocalDateTime attendanceTime, final AttendanceState expected) {
        // Given

        // When
        AttendanceState attendanceState = campusScheduler.calculateAttendanceState(attendanceTime);

        // Then
        assertThat(attendanceState).isEqualTo(expected);
    }

    private static Stream<Arguments> 캠퍼스_운영_시간에_따라_출석_상태를_파악한다() {
        return Stream.of(
                Arguments.of(makeDateTime(3, 8, 0), AttendanceState.ATTENDANCE),
                Arguments.of(makeAttendanceExceptMonday(3), AttendanceState.ATTENDANCE),
                Arguments.of(makeTardinessExceptMonday(4), AttendanceState.TARDINESS),
                Arguments.of(makeAbsenceExceptMonday(5), AttendanceState.ABSENCE)
        );
    }

    @ParameterizedTest
    @MethodSource
    void 캠퍼스_운영_시간이_아니면_예외가_발생한다(final LocalDateTime attendanceTime) {
        // Given

        // When & Then
        assertThatThrownBy(() -> campusScheduler.validateOperationTime(attendanceTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("캠퍼스 운영 시간이 아닙니다.");
    }

    private static Stream<Arguments> 캠퍼스_운영_시간이_아니면_예외가_발생한다() {
        return Stream.of(
                Arguments.of(makeDateTime(3, 7, 59)),
                Arguments.of(makeDateTime(3, 23, 1))
        );
    }
}
