package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import attendance.domain.fixture.LocalDateTestFixture;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayName("출석 상태")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class AttendanceStatusTest {

    @ParameterizedTest
    @CsvSource({
            "10, 5, PRESENT",
            "9, 59, PRESENT",
            "10, 6, LATENESS",
            "10, 30, LATENESS",
            "10, 31, ABSENCE",
            "23, 59, ABSENCE"
    })
    void 평일_출석_상태를_반환한다(int hour, int minute, AttendanceStatus expected) {
        LocalDate date = LocalDateTestFixture.createRegularDate();
        LocalTime time = LocalTime.of(hour, minute);

        AttendanceStatus status = AttendanceStatus.from(date, time);

        assertThat(status).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
            "13, 5, PRESENT",
            "9, 59, PRESENT",
            "13, 6, LATENESS",
            "13, 30, LATENESS",
            "13, 31, ABSENCE",
            "23, 59, ABSENCE"
    })
    void 월요일_출석_상태를_반환한다(int hour, int minute, AttendanceStatus expected) {
        LocalDate date = LocalDateTestFixture.createMondayDate();
        LocalTime time = LocalTime.of(hour, minute);

        AttendanceStatus status = AttendanceStatus.from(date, time);

        assertThat(status).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
            "PRESENT, '출석'",
            "LATENESS, '지각'",
            "ABSENCE, '결석'"
    })
    void 출석_상태의_문자를_반환한다(AttendanceStatus status, String expected) {
        assertThat(status.getStatus()).isEqualTo(expected);
    }
}
