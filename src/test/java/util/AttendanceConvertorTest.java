package util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DateTimeConvertorTest {

    @DisplayName("날짜 변환 테스트")
    @Test
    void convertToDateTest() {
        String raw = "2024-12-13 10:08";
        Assertions.assertThat(DateTimeConvertor.convertToDate(raw))
                .isEqualTo(LocalDate.of(2024, 12, 13));
    }

    @DisplayName("시간 변환 테스트")
    @Test
    void convertToTimeTest() {
        String raw = "2024-12-13 10:08";
        Assertions.assertThat(DateTimeConvertor.convertToTime(raw))
                .isEqualTo(LocalTime.of(10, 8));
    }
}
