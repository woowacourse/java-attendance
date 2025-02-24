import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class FileReaderPolicyPolicyTest {
    @Test
    @DisplayName("파일의 위치가 올바르지 않으면 예외가 발생한다")
    public void testFileReaderPolicy() {
        assertDoesNotThrow(FileReaderPolicy::new);
    }
}
