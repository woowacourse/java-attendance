package attendance.view;

import org.junit.jupiter.api.Test;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class FileLineReaderTest {

    @Test
    void 경로와_파일_이름을_입력받으면_그_파일의_모든_line을_읽어서_반환한다() {
        // When
        List<String> allLines = FileLineReader.readAllLines("src/test/resources/", "testAttendances.csv");

        // Then
        assertThat(allLines).isEqualTo(
                List.of("nickname,datetime", "테스트닉네임,2025-02-24 13:05", "테스트닉네임,2025-02-25 10:40")
        );
    }

    @Test
    void 해당_경로에_파일이_존재하지_않으면_line을_읽지_않는다() {
        // When & Then
        assertThatThrownBy(() -> FileLineReader.readAllLines("src/test/resources/", "invalidFileName.csv"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("올바른 파일 경로를 입력해 주세요.");
    }
}
