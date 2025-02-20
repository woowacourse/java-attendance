import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDate;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.DateUtil;

public class DateUtilTest {

    @Test
    void should_return_localDateTime_by_time() throws Exception {
        //given
        String time = "09:59";

        //when
        var result = DateUtil.parsetime(time);

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
        var result = DateUtil.parsetime(day, time);

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
        final LocalDate localDate = LocalDate.of(2024, 12, 1);

        // when
        final boolean result = DateUtil.isDayEqual(day, localDate);

        // then
        assertThat(result).isEqualTo(false);
    }

    @Test
    void should_return_is_day_equals_정확하게하는지_true() {
        // given
        final int day = 1;
        final LocalDate localDate = LocalDate.of(2024, 12, 1);

        // when
        final boolean result = DateUtil.isDayEqual(day, localDate);

        // then
        assertThat(result).isEqualTo(true);
    }

    @Test
    void 출석_대상_날짜_확인_주말() throws Exception {
        //given
        LocalDate holiday = LocalDate.of(2024, 12, 1);

        //when
        var result = DateUtil.isDayOff(holiday);

        //then
        Assertions.assertThat(result).isEqualTo(true);
    }

    @Test
    @DisplayName("출석_대상_날짜_확인_평일")
    void test2() throws Exception {
        //given
        LocalDate holiday = LocalDate.of(2024, 12, 2);

        //when
        var result = DateUtil.isDayOff(holiday);

        //then
        Assertions.assertThat(result).isEqualTo(false);
    }

    @Test
    @DisplayName("출석_대상_날짜_확인_공휴일")
    void test3() throws Exception {
        //given
        LocalDate holiday = LocalDate.of(2024, 12, 25);

        //when
        var result = DateUtil.isDayOff(holiday);

        //then
        Assertions.assertThat(result).isEqualTo(true);
    }

    @Test
    @DisplayName("출석하는 날짜 리스트 추춣 테스트")
    void test4() throws Exception {
        //given
        int day = 13;

        //when
        List<Integer> result = DateUtil.getAttendUntilDay(day);

        //then
        Assertions.assertThat(result).containsExactly(
                2, 3, 4, 5, 6, 9, 10, 11, 12, 13
        );
    }

    @Test
    @DisplayName("숫자가 아닌 입력 시 예외")
    void test5() throws Exception {
        //given
        String day = "asd";
        String time = "10:11";

        //when & then
        Assertions.assertThatThrownBy(
                () -> DateUtil.parsetime(day, time)
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
                () -> DateUtil.parsetime(day, time)
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
                () -> DateUtil.parsetime(day, time)
        ).isInstanceOf(IllegalArgumentException.class);
    }
}
