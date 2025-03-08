package util;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CsvReaderTest {
    @DisplayName("파일을 정상적으로 읽어온다")
    @Test
    void getValidFile() {
        // given
        final List<String[]> testData = List.of(
                new String[]{"쿠키", "2024-12-13 10:08"},
                new String[]{"빙봉", "2024-12-13 10:07"},
                new String[]{"빙티", "2024-12-13 10:07"}
        );
        final List<String[]> data = CsvReader.readFile("src/test/resources/attendances-test.csv");

        // when
        // then
        for (int i = 0; i < data.size(); i++) {
            final String[] items = data.get(i);

            assertThat(items[0]).isEqualTo(testData.get(i)[0]);
            assertThat(items[1]).isEqualTo(testData.get(i)[1]);
        }
    }
}
