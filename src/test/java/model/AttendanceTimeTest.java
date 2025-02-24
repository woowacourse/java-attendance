package model;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class AttendanceTimeTest {

    @ParameterizedTest
    @CsvSource({
            "2024, 12, 2, 7, 59, false",
            "2024, 12, 2, 8, 0, true",
            "2024, 12, 2, 23, 0, true",
            "2024, 12, 2, 23, 1, false",
    })
    void 특정_시간이_운영시간인지_아닌지_알_수_있다(int year, int month, int date, int hour, int minute, boolean expected) {
        //given
        LocalDateTime time = LocalDateTime.of(year, month, date, hour, minute);
        //when
        boolean actual = AttendanceTime.isInOperation(time);
        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void 출석_인정_시간과의_nanos_차이를_알_수_있다() {
        //given
        LocalDateTime dateTime = LocalDateTime.of(2024, 12, 3, 10, 0, 0, 1);
        //when
        long nanos = AttendanceTime.calculateNanosDifferenceFromStartTime(dateTime);
        //then
        assertThat(nanos).isEqualTo(1);
    }
}
