package attendance.util;

import static attendance.fixture.TestFixture.makeDateTime;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
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
        assertThatThrownBy(() -> StringParser.parseLocalTime(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] HH:mm 형식이 아닙니다.");
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

    @Test
    void 파일_데이터_형식이_맞지_않을_경우_예외가_발생한다() {
        // Given
        List<String> lines = List.of(
                "짱수,2024-12-0213:00"
        );
        // When & Then
        assertThatThrownBy(() -> StringParser.parseFile(lines))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("yyyy-MM-dd HH:mm 형식에 맞춰 작성해주세요.");
    }

    @Test
    void 문자열을_숫자로_파싱한다() {
        // Given
        String input = "31";

        // When & Then
        assertThat(StringParser.parseInt(input)).isEqualTo(31);
    }

    @Test
    void 숫자_문자열이_아닌_경우_예외가_발생한다() {
        // Given
        String input = "31.3";

        // When & Then
        assertThatThrownBy(() -> StringParser.parseInt(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 숫자 형식의 문자열이 아닙니다.");
    }

    @Test
    void 현재_날짜와_일자를_받아_LocalDate를_생성한다() {
        // Given
        LocalDate now = LocalDate.of(2024, 12, 13);
        String day = "3";
        LocalDate expected = now.withDayOfMonth(3);

        // When & Then
        assertThat(StringParser.parseLocalDate(day, now)).isEqualTo(expected);
    }

    @Test
    void 존재하지_않은_일자일_경우_예외가_발생한다() {
        // Given
        LocalDate now = LocalDate.of(2024, 12, 13);
        String day = "33";

        // When & Then
        Assertions.assertThatThrownBy(() -> StringParser.parseLocalDate(day, now))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 존재하지 않은 날짜(일)입니다.");
    }
}
