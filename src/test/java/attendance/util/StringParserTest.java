package attendance.util;

import static attendance.fixture.TestFixture.makeDateTime;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class StringParserTest {

    @Test
    void 시간_문자열을_LocalTime으로_파싱한다() {
        // Given
        String input = "10:01";
        LocalTime expected = LocalTime.of(10, 1);

        // When & Then
        assertThat(StringParser.parseLocalTime(input)).isEqualTo(expected);
    }

    @Test
    void 시간_문자열_형식에_맞지_않은_경우_예외가_발생한다() {
        // Given
        String input = "10:1";

        // When & Then
        Assertions.assertThatThrownBy(() -> StringParser.parseLocalTime(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("문자열 형식에 맞지 않습니다.");
    }

    @Test
    void 파일_데이터를_객체로_파싱한다() {
        // Given
        List<String> lines = List.of(
                "짱수,2024-12-02 13:00",
                "빙티,2024-12-02 13:00",
                "쿠키,2024-12-02 13:01",
                "이든,2024-12-02 13:02",
                "빙봉,2024-12-02 13:06",
                "짱수,2024-12-03 10:00",
                "빙봉,2024-12-03 10:03",
                "쿠키,2024-12-03 10:06",
                "이든,2024-12-03 10:06",
                "빙티,2024-12-03 10:07"
        );
        Map<String, List<LocalDateTime>> expected = Map.of(
                "짱수", List.of(makeDateTime(2, 13, 0), makeDateTime(3, 10, 0)),
                "빙티", List.of(makeDateTime(2, 13, 0), makeDateTime(3, 10, 7)),
                "쿠키", List.of(makeDateTime(2, 13, 1), makeDateTime(3, 10, 6)),
                "이든", List.of(makeDateTime(2, 13, 2), makeDateTime(3, 10, 6)),
                "빙봉", List.of(makeDateTime(2, 13, 6), makeDateTime(3, 10, 3))
        );

        // When
        Map<String, List<LocalDateTime>> result = StringParser.parseFile(lines);

        // Then
        assertThat(result).isEqualTo(expected);
    }
}
