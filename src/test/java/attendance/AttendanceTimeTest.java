package attendance;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import java.time.LocalTime;
import org.junit.jupiter.api.Test;

public class AttendanceTimeTest {

    @Test
    void _08시_이전에는_출석할_수_없다() {
        LocalTime time = LocalTime.of(7, 0);
        assertThatThrownBy(() -> new AttendanceTime(time))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void _23시_이후에는_출석할_수_없다() {
        LocalTime time = LocalTime.of(23, 30);
        assertThatThrownBy(() -> new AttendanceTime(time))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void _캠퍼스_운영시간_내에는_출석_가능하다() {
        LocalTime time = LocalTime.of(9, 0);
        assertThatCode(() -> new AttendanceTime(time))
                .doesNotThrowAnyException();
    }
}
