package domain;


import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class AttendanceStateTest {
    @ParameterizedTest
    @CsvSource(value = {
            "13:05,2,출석",
            "13:06,2,지각",
            "13:30,2,지각",
            "13:31,2,결석",
            "10:05,4,출석",
            "10:06,4,지각",
            "10:30,4,지각",
            "10:31,4,결석"
    }, delimiter = ',')
    void statusReturn(LocalTime localTime, int dayOfMonth, String expected) {
        // given
        String actual = AttendanceState.findStateBy(localTime,
                LocalDate.of(2024, 12, dayOfMonth)).getDescription();

        // when & then
        assertThat(actual).isEqualTo(expected);
    }
}
