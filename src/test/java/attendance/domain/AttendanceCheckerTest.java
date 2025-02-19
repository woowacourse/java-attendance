package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import attendance.utils.AttendanceChecker;
import java.time.LocalDateTime;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class AttendanceCheckerTest {

    @ParameterizedTest
    @CsvSource(value = {"2024,12,13,10,0,출석", "2024,12,13,10,6,지각", "2024,12,13,10,31,결석",
            "2024,12,9,12,30,출석", "2024,12,9,13,6,지각", "2024,12,9,13,31,결석"})
    void 시간에_맞는_출결_상태를_갖는다(int year, int month, int day, int hour, int minute, String result) {

        // given
        LocalDateTime dateTime = LocalDateTime.of(year, month, day, hour, minute);

        // when && then
        assertThat(AttendanceChecker.check(dateTime)).isEqualTo(result);
    }
}