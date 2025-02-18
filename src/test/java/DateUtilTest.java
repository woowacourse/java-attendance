import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDateTime;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DateUtilTest {

    @Test
    void should_return_localDateTime_by_time() throws Exception {
        //given
        String time = "09:59";

        //when
        var result = DateUtil.parseDatetime(time);

        //then
        assertAll(
                () -> assertThat(result.getHour()).isEqualTo(9),
                () -> assertThat(result.getMinute()).isEqualTo(59)
        );
    }

    @Test
    void should_return_localDateTime_by_day_and_time() throws Exception {
        //given
        String day = "3";
        String time = "09:59";

        //when
        var result = DateUtil.parseDatetime(day, time);

        //then
        assertAll(
                () -> assertThat(result.getDayOfMonth()).isEqualTo(3),
                () -> assertThat(result.getHour()).isEqualTo(9),
                () -> assertThat(result.getMinute()).isEqualTo(59)
        );
    }

    @Test
    void should_return_is_day_equals_정확하게하는지_false() {
        // given
        final int day = 3;
        final LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 1, 9, 59);

        // when
        final boolean result = DateUtil.isDayEqual(day, localDateTime);

        // then
        assertThat(result).isEqualTo(false);
    }

    @Test
    void should_return_is_day_equals_정확하게하는지_true() {
        // given
        final int day = 1;
        final LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 1, 9, 59);

        // when
        final boolean result = DateUtil.isDayEqual(day, localDateTime);

        // then
        assertThat(result).isEqualTo(true);
    }
    @Test
    @DisplayName("숫자가 아닌 입력 시 예외")
    void test5() throws Exception {
        //given
        String day = "asd";
        String time = "10:11";

        //when & then
        Assertions.assertThatThrownBy(
                () -> DateUtil.parseDatetime(day, time)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("날짜 범위를 벗어나면 예외")
    void test6() throws Exception {
        //given
        String day = "0";
        String time = "10:11";

        //when & then
        Assertions.assertThatThrownBy(
                () -> DateUtil.parseDatetime(day, time)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("날짜 범위를 벗어나면 예외")
    void test7() throws Exception {
        //given
        String day = "32";
        String time = "10:11";

        //when & then
        Assertions.assertThatThrownBy(
                () -> DateUtil.parseDatetime(day, time)
        ).isInstanceOf(IllegalArgumentException.class);
    }
}
