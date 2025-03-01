package util;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Stream;

class CsvReaderTest {

    private static final String FILE_PATH = "src/main/resources/attendances1.csv";

    @Test
    @DisplayName("csv 파일을 읽어오는 기능이 잘 작동하는지")
    void readFileSuccess() {

        // given
        final List<String[]> expected = List.of(
                new String[]{"쿠키", "2024-12-13 10:08"},
                new String[]{"빙봉", "2024-12-13 10:07"},
                new String[]{"빙티", "2024-12-13 10:07"},
                new String[]{"이든", "2024-12-13 10:07"},
                new String[]{"빙봉", "2024-12-12 11:11"}
        );

        // when
        final Stream<String[]> parsedFile = CsvReader.readFile(FILE_PATH).stream()
                .limit(5);

        // then
        Assertions.assertThat(parsedFile).containsAll(expected);
    }

    @Test
    @DisplayName("csv 파일을 읽어오는 기능이 잘 작동하는지")
    void readFileFailureByInvalidPath() {

        // given
        Assertions.assertThatThrownBy(
                () -> CsvReader.readFile(FILE_PATH)
        ).isInstanceOf(IllegalStateException.class);
    }
}
