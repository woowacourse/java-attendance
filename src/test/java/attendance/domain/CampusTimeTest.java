package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class CampusTimeTest {

    @CsvSource(value = {
            "7,59,true", "23,1,true",
            "8,0,false", "23,0,false"
    })
    @ParameterizedTest
    void 시간을_알려주면_캠퍼스_운영_시간을_벗어나는지_알려준다(int hour, int minute, boolean expected) {
        LocalTime localTime = LocalTime.of(hour, minute);

        assertThat(CampusTime.isOutOfCampusOperationTime(localTime)).isEqualTo(expected);
    }

}
