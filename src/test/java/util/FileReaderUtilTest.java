package util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FileReaderUtilTest {

    @Test
    @DisplayName("출석 CSV 파일을 정상적으로 읽을 수 있다")
    void readAttendanceData() throws IOException {
        // given
        // when
        List<String> lines = FileReaderUtil.read(FileReaderUtil.DEFAULT_ATTENDANCE_DATA_PATH);

        // then
        assertThat(lines)
                .isNotNull()
                .isNotEmpty();
    }

    @Test
    @DisplayName("출석 CSV 파일이 존재하지 않으면 예외를 발생시킨다")
    void whenFileNotFound() {
        // given
        String invalidPath = "iWannabePATH";

        // when
        // then
        assertThatThrownBy(()->FileReaderUtil.read(invalidPath))
                .isInstanceOf(IOException.class)
                .hasMessage("출석 데이터를 읽어오는데 실패했습니다: " + invalidPath);
    }
}
