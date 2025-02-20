import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import domain.Parser;
import java.util.ArrayList;
import java.util.List;
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
        List<List<String>> expectedNameSeperatedData = List.of(List.of("쿠키", "2024-12-13 10:08"), List.of("빙봉", "2024-12-13 10:07"));

        assertThat(Parser.parseName(removedData)).isEqualTo(expectedNameSeperatedData);
    }

    @DisplayName("csv파일파서_크루정보분리_테스트")
    @Test
    public void csv파일파서_크루정보분리_테스트() {
        String rawDate = "2024-12-13 10:08";
        List<String> expectedSeperatedData = List.of("2024-12-13", "10:08");

        assertThat(Parser.parseDate(rawDate)).isEqualTo(expectedSeperatedData);
    }
}