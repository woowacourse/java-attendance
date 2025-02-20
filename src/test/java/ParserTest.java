import static org.assertj.core.api.Assertions.assertThat;

import domain.Parser;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ParserTest {
    List<String> loadedData;

    @BeforeEach
    public void setup() {
        loadedData = new ArrayList<>(List.of("nickname,datetime", "쿠키,2024-12-13 10:08", "빙봉,2024-12-13 10:07"));
    }

    @DisplayName("csv파일파서_첫줄제거_테스트")
    @Test
    public void csv파일파서_첫줄제거_테스트() {
        List<String> parsedData = Parser.parse(loadedData);

        List<String> expectedData = List.of("쿠키,2024-12-13 10:08", "빙봉,2024-12-13 10:07");

        assertThat(parsedData).isEqualTo(expectedData);
    }

    @DisplayName("csv파일파서_이름분리_테스트")
    @Test
    public void csv파일파서_이름분리_테스트() {
        List<String> removedData = List.of("쿠키,2024-12-13 10:08", "빙봉,2024-12-13 10:07");
        List<List<String>> expectedNameSeperatedData = List.of(
                List.of("쿠키", "2024-12-13 10:08"),
                List.of("빙봉", "2024-12-13 10:07"));

        assertThat(Parser.parseName(removedData)).isEqualTo(expectedNameSeperatedData);
    }

    @DisplayName("csv파일파서_크루정보분리_테스트")
    @Test
    public void csv파일파서_크루정보분리_테스트() {
        String rawDate = "2024-12-13 10:08";
        Map<LocalDate, LocalTime> expectedSeperatedData =
                Map.of(LocalDate.parse("2024-12-13"), LocalTime.parse("10:08")); // 중간에 map으로 변경한 것 반영되지 않아 추가

        assertThat(Parser.parseDate(rawDate)).isEqualTo(expectedSeperatedData);
    }
}