package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.DayOfWeek;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ClassTimeTest {

    @Test
    @DisplayName("요일에 맞는 강의 시작 시간을 찾는다.")
    void test1() {
        //given
        final LocalTime localTime1 = LocalTime.of(13, 0);
        final LocalTime localTime2 = LocalTime.of(10, 0);
        final DayOfWeek monday = DayOfWeek.MONDAY;
        final DayOfWeek tuesday = DayOfWeek.TUESDAY;

        //when
        final LocalTime byDayOfWeek1 = ClassTime.findByDayOfWeek(monday);
        final LocalTime byDayOfWeek2 = ClassTime.findByDayOfWeek(tuesday);

        //then
        assertThat(byDayOfWeek1).isEqualTo(localTime1);
        assertThat(byDayOfWeek2).isEqualTo(localTime2);

    }

    enum ClassTime {
        ;

        public static LocalTime findByDayOfWeek(DayOfWeek dayOfWeek) {
            return LocalTime.of( 1, 0);
        }
    }
}
