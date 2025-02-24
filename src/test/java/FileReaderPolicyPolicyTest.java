import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class FileReaderPolicyPolicyTest {
    @Test
    @DisplayName("지정된 위치의 파일을 읽어올 수 있다")
    public void validateFileReaderPolicyTest() {
        assertDoesNotThrow(FileReaderPolicy::new);
    }

    @Test
    @DisplayName("파일의 형식이 올바르지 않으면 예외가 발생한다")
    public void validateFileFormatTest() {
        //given
        FileReaderPolicy fileReaderPolicy = new FileReaderPolicy();

        //when-then
        assertThatThrownBy(fileReaderPolicy::validateFileFormat)
                .isInstanceOf(IllegalArgumentException.class);
    }
}
