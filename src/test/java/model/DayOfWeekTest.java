package model;

import attendance.model.WoowaDurationTime;
import java.time.LocalTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class DayOfWeekTest {

    @Test
    void test1() {
        WoowaDurationTime dayOfWeek = WoowaDurationTime.월요일;
        Assertions.assertThat(dayOfWeek).isNotNull();
    }

    @Test
    void test2() {
        WoowaDurationTime dayOfWeek = WoowaDurationTime.월요일;
        Assertions.assertThat(dayOfWeek.getStartTime()).isEqualTo(LocalTime.of(13, 0));
    }


    @Test
    void test3() {
        WoowaDurationTime dayOfWeek = WoowaDurationTime.화요일;
        Assertions.assertThat(dayOfWeek.getStartTime()).isEqualTo(LocalTime.of(10, 0));
    }

    @Test
    void test4() {
        WoowaDurationTime dayOfWeek = WoowaDurationTime.월요일;
        Assertions.assertThat(dayOfWeek.getEndTime()).isEqualTo(LocalTime.of(18, 0));
    }

    @Test
    void test5() {
        WoowaDurationTime dayOfWeek = WoowaDurationTime.화요일;
        Assertions.assertThat(dayOfWeek.getEndTime()).isEqualTo(LocalTime.of(18, 0));
    }

}
