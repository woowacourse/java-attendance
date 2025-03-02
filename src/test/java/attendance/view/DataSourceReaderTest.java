package attendance.view;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DataSourceReaderTest {
    @DisplayName("파일에서 전체 출석 기록 읽어오기 성공")
    @Test
    void test() {
        List<String> lines = DataSourceReader.readFile();

        assertThat(lines)
                .isNotNull()
                .hasSize(41);
    }
}
