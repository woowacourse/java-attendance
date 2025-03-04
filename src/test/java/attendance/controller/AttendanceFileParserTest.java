package attendance.controller;

import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class AttendanceFileParserTest {

    @Test
    @DisplayName("파일이 빈줄이 있으면 예외를 던진다")
    void parseTest1() {
        String fileContents = "";
        assertThatThrownBy(() -> AttendanceFileParser.parse(fileContents)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("파싱이 끝나면 예외를 던지지 않는다")
    void parseTest2() {
        String fileContents = "쿠키,2024-12-13 10:08\n"
                + "빙봉,2024-12-13 10:07\n"
                + "빙티,2024-12-13 10:07\n"
                + "이든,2024-12-13 10:07";
        assertThatCode(() -> AttendanceFileParser.parse(fileContents)).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("파싱이 끝나면 Map<String, List<LocalDateTime>>으로 리턴한다")
    void parseTest3() {
        String fileContents = "쿠키,2024-12-13 10:08\n"
                + "빙봉,2024-12-13 10:07\n"
                + "빙티,2024-12-13 10:07\n"
                + "이든,2024-12-13 10:07\n"
                + "쿠키,2024-12-14 10:08";
        assertThat(AttendanceFileParser.parse(fileContents)).asInstanceOf(InstanceOfAssertFactories.MAP)
                .containsEntry("쿠키", List.of(LocalDateTime.of(2024, 12, 13, 10, 8), LocalDateTime.of(2024, 12, 14, 10, 8)));
    }
}
