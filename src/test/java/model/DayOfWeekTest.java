package model;

import attendance.model.WoowaDayOfWeek;
import java.time.DayOfWeek;
import java.time.LocalTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DayOfWeekTest {

    @Test
    void test1() {
        WoowaDayOfWeek dayOfWeek = WoowaDayOfWeek.월요일;
        Assertions.assertThat(dayOfWeek).isNotNull();
    }

    @Test
    void test2() {
        WoowaDayOfWeek dayOfWeek = WoowaDayOfWeek.월요일;
        Assertions.assertThat(dayOfWeek.getStartTime()).isEqualTo(LocalTime.of(13, 0));
    }
}
