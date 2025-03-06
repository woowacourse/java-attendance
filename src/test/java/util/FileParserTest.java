package util;

import static constant.PathConstant.ATTENDANCE_FILE_PATH;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FileParserTest {

    @Test
    @DisplayName("파일을 불러온다.")
    void test() {
        // given

        // when
        List<String> lines = FileParser.readLines(ATTENDANCE_FILE_PATH.getPath());

        // then
        assertThat(lines).hasSize(16);
    }
}
