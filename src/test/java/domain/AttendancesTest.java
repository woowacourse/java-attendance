package domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendancesTest {
    @Test
    @DisplayName("출석 시간을 가지고 출석을 기록한다")
    void should_attend_by_attending_time() {
        // given
        String time = "10:00";
        Attendances attendances = new Attendances();

        // when
        attendances.attend(time);

        // then
        assertThat(attendances).isNotEqualTo(new Attendances());
    }
}
