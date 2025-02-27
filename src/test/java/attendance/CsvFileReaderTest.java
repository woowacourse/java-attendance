package attendance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CsvFileReaderTest {

    @Test
    @DisplayName("파일을 읽을 수 있다")
    void readTest1() {
        assertThatCode(() -> CsvFileReader.read("src/test/resources/attendances.csv")).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("파일을 읽을 수 없으면 예외를 던진다")
    void readTest2() {
        assertThatThrownBy(() -> CsvFileReader.read("src/test/resources/attendances.txt")).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("파일을 읽은 결과에 파일 내용이 포함되어 있어야 한다")
    void readTest3() throws IOException {
        assertThat(CsvFileReader.read("src/test/resources/attendances.csv")).contains("쿠키,2024-12-13 10:08");
    }
}

