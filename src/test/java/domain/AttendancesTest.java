package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendancesTest {
    @Test
    @DisplayName("출석 시간을 가지고 출석을 기록한다")
    void should_attend_by_attending_time() {
        // given
        LocalTime attendingTime = LocalTime.parse("10:00");
        Attendances attendances = new Attendances();

        // when
        attendances.attend(attendingTime);

        // then
        assertThat(attendances).isNotEqualTo(new Attendances());
    }

    @Test
    @DisplayName("이미 출석한 경우를 알 수 있다")
    void should_return_true_when_already_attended() {
        // given
        LocalTime attendingTime = LocalTime.parse("10:00");
        Attendances attendances = new Attendances();
        attendances.attend(attendingTime);

        // when
        boolean result = attendances.isAttended();

        // then
        assertThat(result).isEqualTo(true);
    }
}
