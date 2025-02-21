package util;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.DayOfWeek;
import org.junit.jupiter.api.Test;

public class ConvertorTest {

    @Test
    void 요일_변환_테스트() {
        assertThat(Convertor.convertDayOfWeekToKorean(DayOfWeek.MONDAY)).isEqualTo("월");
        assertThat(Convertor.convertDayOfWeekToKorean(DayOfWeek.TUESDAY)).isEqualTo("화");
        assertThat(Convertor.convertDayOfWeekToKorean(DayOfWeek.WEDNESDAY)).isEqualTo("수");
        assertThat(Convertor.convertDayOfWeekToKorean(DayOfWeek.THURSDAY)).isEqualTo("목");
        assertThat(Convertor.convertDayOfWeekToKorean(DayOfWeek.FRIDAY)).isEqualTo("금");
        assertThat(Convertor.convertDayOfWeekToKorean(DayOfWeek.SATURDAY)).isEqualTo("토");
        assertThat(Convertor.convertDayOfWeekToKorean(DayOfWeek.SUNDAY)).isEqualTo("일");
    }
}
