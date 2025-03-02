package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendancesTest {
    @Test
    @DisplayName("출석 시간을 가지고 출석을 기록한다")
    void should_attend_by_attending_time() {
        // given
        LocalTime attendingTime = LocalTime.parse("10:00", DateTimeFormatter.ofPattern("HH:mm"));
        Attendances attendances = new Attendances();

        // when
        attendances.attend(attendingTime);

        // then
        assertThat(attendances).isNotEqualTo(new Attendances());
    }
}
