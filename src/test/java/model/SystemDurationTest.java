package model;

import static org.assertj.core.api.Assertions.assertThat;

import attendance.model.SystemDuration;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

public class SystemDurationTest {

    @Test
    void 시스템을_사용할_수_있는_날짜인지_확인한다_사용하지_못하는_경우() {
        // given
        LocalDate now = LocalDate.of(2025, 1, 1);

        // when
        boolean isDuration = SystemDuration.isSystemDuration(now);

        // then
        assertThat(isDuration).isFalse();
    }

    @Test
    void 시스템을_사용할_수_있는_날짜인지_확인한다_사용할_수_있는_경우() {
        // given
        LocalDate now = LocalDate.of(2024, 12, 31);

        // when
        boolean isDuration = SystemDuration.isSystemDuration(now);

        // then
        assertThat(isDuration).isFalse();
    }
}
