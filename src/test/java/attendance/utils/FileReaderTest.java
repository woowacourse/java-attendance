package attendance.utils;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FileReaderTest {

    @DisplayName("파일을 읽어 리스트로 반환한다.")
    @Test
    void 파일을_읽어_리스트로_반환한다() {

        // given
        String filePath = "src/main/resources/attendances.csv";

        // when
        List<String> file = FileReader.parseToFile(filePath);

        // then
        assertThat(file.size()).isEqualTo(43);
    }
}
