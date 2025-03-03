package util;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Stream;

class CsvReaderTest {

    @Test
    @DisplayName("csv 파일을 읽어오는 기능이 잘 작동하는지")
    void readFileSuccess() {

        // given
        final String FILE_PATH = "src/main/resources/attendances.csv";
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
    @DisplayName("유효하지 않는 파일 경로일 때 예외 처리")
    void readFileFailureByInvalidPath() {

        // given
        final String FILE_PATH = "src/main/resources/invalidPath";

        // when
        // then
        Assertions.assertThatThrownBy(
                () -> CsvReader.readFile(FILE_PATH)
        ).isInstanceOf(IllegalStateException.class);
    }
}
