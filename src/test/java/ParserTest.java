import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.Parser;

public class ParserTest {
    List<String> loadedData;

    @BeforeEach
    public void setup() {
        loadedData = new ArrayList<>(List.of("nickname,datetime", "쿠키,2024-12-13 10:08", "빙봉,2024-12-13 10:07"));
    }

    @Test
    public void 카테고리를_나타내는_첫_줄을_제거한다() {
        List<String> parsedData = Parser.parse(loadedData);

        List<String> expectedData = List.of("쿠키,2024-12-13 10:08", "빙봉,2024-12-13 10:07");

        assertThat(parsedData).isEqualTo(expectedData);
    }

    @Test
    public void 이름_별로_분리한다() {
        List<String> removedData = List.of("쿠키,2024-12-13 10:08", "빙봉,2024-12-13 10:07");
        List<List<String>> expectedNameSeperatedData = List.of(
                List.of("쿠키", "2024-12-13 10:08"),
                List.of("빙봉", "2024-12-13 10:07"));

        assertThat(Parser.parseName(removedData)).isEqualTo(expectedNameSeperatedData);
    }

    @Test
    public void 날짜와_시간을_분리한다() {
        String rawDate = "2024-12-13 10:08";
        Map<LocalDate, LocalTime> expectedSeperatedData =
                Map.of(LocalDate.parse("2024-12-13"), LocalTime.parse("10:08"));

        assertThat(Parser.parseDate(rawDate)).isEqualTo(expectedSeperatedData);
    }
}