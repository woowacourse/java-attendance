import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import domain.CsvReader;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CsvReaderTest {

    @Test
    @DisplayName("CSV 형식의 파일 정상 읽기 테스트")
    void csvFileReadTest() {
        //given
        String filePath = "attendances.csv";

        //when & then
        assertDoesNotThrow(() -> CsvReader.readCsv(filePath));
    }

    @Test
    @DisplayName("CSV 형식의 파일을 읽고 1번째 행을 없애는 기능 테스트")
    void removeFirstRowOfCsvFileTest() {
        //given
        String filePath = "attendances.csv";

        //when
        List<String> result = CsvReader.readCsv(filePath);

        // then
        assertThat(result).doesNotContain("nickname,datetime");
    }

    @Test
    @DisplayName("CSV 형식이 맞지 않은 파일을 읽을 시 예외 처리")
    void throwExceptionWhenNotMatchFileFormat() {
        //given
        String filePath = "attendances_format_fail.csv";
        List<String> rows = CsvReader.readCsv(filePath);

        //when & then
        assertThatThrownBy(() -> CsvReader.parseName(rows.getFirst())).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("파일 행 구조가 잘못되었습니다.");
    }

    @Test
    @DisplayName("날짜 형식이 맞지 않은 파일을 읽을 시 예외 처리")
    void throwExceptionWhenNotMatchDateFormat() {
        //given
        String row = "플린트,2024:12:03 08:00";

        //when & then
        assertThatThrownBy(() -> CsvReader.parseAttend(row)).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("날짜 형식이 잘못되었습니다.");
    }

    @Test
    @DisplayName("시간 형식이 맞지 않은 파일을 읽을 시 예외 처리")
    void throwExceptionWhenNotMatchTimeFormat() {
        //given
        String row = "플린트,2024-12-03 08-00";

        //when & then
        assertThatThrownBy(() -> CsvReader.parseAttend(row)).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("시간 형식이 잘못되었습니다.");
    }

    @Test
    @DisplayName("datetime 형식이 맞지 않은 파일을 읽을 시 예외 처리")
    void throwExceptionWhenNotMatchDateTimeFormat() {
        //given
        String row = "플린트,2024-12-03/08:00";

        //when & then
        assertThatThrownBy(() -> CsvReader.parseAttend(row)).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("datetime 형식이 잘못되었습니다.");
    }
}
