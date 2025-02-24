import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class FileReaderPolicyPolicyTest {
    @Test
    @DisplayName("지정된 위치의 파일을 읽어올 수 있다")
    public void validateFileReaderPolicyTest() {
        assertDoesNotThrow(FileReaderPolicy::new);
    }

    @Test
    @DisplayName("파일의 형식이 올바른지 검증할 수 있다")
    public void validateFileFormatTest() {
        //given
        FileReaderPolicy fileReaderPolicy = new FileReaderPolicy();

        //when-then
        assertDoesNotThrow(fileReaderPolicy::parseLines);
    }
}
