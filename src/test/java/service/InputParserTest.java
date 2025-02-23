package service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class InputParserTest {
    @DisplayName("csv 파일 크루 정보에서 이름과 날짜시간을 분리한다.")
    @Test
    public void csv_파일_크루_정보에서_이름과_날짜시간을_분리한다() {
        String existedCrewRecord = "빙봉,2024-12-13 10:08";
        List<String> expectedResult = List.of("빙봉", "2024-12-13 10:08");

        assertThat(InputParser.parseRecordToNameAndDate(existedCrewRecord)).isEqualTo(expectedResult);
    }

    @DisplayName("날짜시간을 날짜와 시간으로 분리한다")
    @Test
    public void 날짜시간을_날짜와_시간으로_분리한다() {
        String date = "2024-12-13 10:08";
        Map<LocalDate, LocalTime> expectedResult =
                Map.of(LocalDate.parse("2024-12-13"), LocalTime.parse("10:08"));

        assertThat(InputParser.parseDateToDayAndTime(date)).isEqualTo(expectedResult);
    }
}