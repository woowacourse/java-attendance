package util;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.DayOfWeek;
import org.junit.jupiter.api.Test;

public class ConvertorTest {

    @Test
    void 요일_변환_테스트() {

        assertThat(Convertor.convertDayOfWeekToKorean(DayOfWeek.MONDAY))
                .isEqualTo("월");
    }
}
