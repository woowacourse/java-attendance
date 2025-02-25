import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import policy.FileReaderPolicy;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class FileReaderPolicyTest {
    private static final String FILE_PATH = "src/main/resources/attendances.csv";

    @Test
    @DisplayName("지정된 위치의 파일이 아니면 예외가 발생한다")
    public void validateFileReaderPolicyTest() {
        assertThatThrownBy(() -> new FileReaderPolicy("attendances.csv"))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("파일의 형식이 올바른지 검증할 수 있다")
    public void validateFileFormatTest() {
        //given
        FileReaderPolicy fileReaderPolicy = new FileReaderPolicy(FILE_PATH);

        //when-then
        assertDoesNotThrow(fileReaderPolicy::parseLines);
    }
}
