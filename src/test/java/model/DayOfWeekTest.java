package model;

import attendance.model.WoowaDayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.assertj.core.api.Assertions;
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


    @Test
    void test3() {
        WoowaDayOfWeek dayOfWeek = WoowaDayOfWeek.화요일;
        Assertions.assertThat(dayOfWeek.getStartTime()).isEqualTo(LocalTime.of(10, 0));
    }

    @Test
    void test4() {
        WoowaDayOfWeek dayOfWeek = WoowaDayOfWeek.월요일;
        Assertions.assertThat(dayOfWeek.getEndTime()).isEqualTo(LocalTime.of(18, 0));
    }

    @Test
    void test5() {
        WoowaDayOfWeek dayOfWeek = WoowaDayOfWeek.화요일;
        Assertions.assertThat(dayOfWeek.getEndTime()).isEqualTo(LocalTime.of(18, 0));
    }

    @Test
    void test6() {
        LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 10, 10, 0);
        WoowaDayOfWeek dayOfWeek = WoowaDayOfWeek.from(localDateTime);

        Assertions.assertThat(dayOfWeek).isEqualTo(WoowaDayOfWeek.화요일);
    }
}
