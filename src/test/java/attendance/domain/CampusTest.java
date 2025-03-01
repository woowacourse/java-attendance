package attendance.domain;

import static attendance.fixture.TestFixture.makeAttendanceTime;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

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
}
