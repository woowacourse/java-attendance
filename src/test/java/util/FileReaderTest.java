package util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FileReaderTest {
    @DisplayName("파일의 내용을 읽어 컬렉션에 저장할 수 있다.")
    @Test
    void readTest() {
        // given
        String filePath = "src/main/resources/attendances.csv";

        // when
        List<String> linesRead = FileReader.read(filePath);

        // then
        assertThat(linesRead).containsSequence("nickname,datetime", "쿠키,2024-12-13 10:08", "빙봉,2024-12-13 10:07");
    }

    @DisplayName("파일의 경로가 잘못된 경우 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"attendances.csv", "./attendances.csv", "src/main/resources/attendan.csv"})
    void readExceptionTest(String filePath) {
        assertThatThrownBy(() -> FileReader.read(filePath)).isInstanceOf(IllegalStateException.class);
    }
}
