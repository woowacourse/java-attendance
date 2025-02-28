package util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class FileReaderTest {
    @DisplayName("파일의 내용을 읽어 컬렉션에 저장할 수 있다.")
    @Test
    void readTest() {
        // given
        String filePath = "src/main/resources/attendances.csv";

        // when
        List<String> linesRead = FileReader.read(filePath);

        // that
        assertThat(linesRead).containsSequence("nickname,datetime", "쿠키,2024-12-13 10:08", "빙봉,2024-12-13 10:07");
    }
}
