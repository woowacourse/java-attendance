package attendance.domain;

import static attendance.fixture.TestFixture.makeAttendanceTime;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

class CampusTest {

    @ParameterizedTest
    @CsvSource({
            "7,59,false",
            "8,0,true",
            "23,0,true",
            "23,1,false"
    })
    void 운영_시간인지_확인한다(final int hour, final int minute, final boolean expected) {
        // Given
        LocalTime attendanceTime = makeAttendanceTime(hour, minute);

        // When & Then
        assertThat(Campus.isOperationTime(attendanceTime)).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource
    void 교육_시작시간을_조회한다(final DayOfWeek dayOfWeek, final LocalTime expected) {
        // Given

        // When & Then
        assertThat(Campus.getEducationStartTime(dayOfWeek)).isEqualTo(expected);
    }

    private static Stream<Arguments> 교육_시작시간을_조회한다() {
        return Stream.of(
                Arguments.of(DayOfWeek.MONDAY, LocalTime.of(13, 0)),
                Arguments.of(DayOfWeek.TUESDAY, LocalTime.of(10, 0))
        );
    }
}
