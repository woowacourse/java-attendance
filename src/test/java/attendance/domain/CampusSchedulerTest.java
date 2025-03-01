package attendance.domain;

import static attendance.fixture.TestFixture.makeDecemberDate;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import attendance.view.TimeFormatter;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
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
}
