import static org.assertj.core.api.Assertions.assertThat;

import domain.CsvReader;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CsvReaderTest {

    @Test
    @DisplayName("Csv 파일을 각 행을 String으로 읽어온다")
    void readCsvFile() {
        //given
        String path = "attendances.csv";

        //when
        List<String> actual = CsvReader.readFile(path);

        //then
        List<String> expected = List.of(
                "빙티,2024-12-02 10:00",
                "빙티,2024-12-03 10:00",
                "빙티,2024-12-05 10:00"
        );
        assertThat(actual).containsExactlyElementsOf(expected);
    }

    @Test
    @DisplayName("존재하지 않는 Csv 파일을 읽을 경우, 예외를 던진다")
    void throwExceptionWhenNotExistCsvFile() {
        //given
        String path = "notExist.csv";

        //when & then
        Assertions.assertThatThrownBy(() -> CsvReader.readFile(path));
    }
}
