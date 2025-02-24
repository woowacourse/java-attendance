import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileReader;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class FileReaderPolicyTest {
    @Test
    @DisplayName("파일의 위치가 올바르지 않으면 예외가 발생한다")
    public void testFileReaderPolicy() {
        //given
        String file = "src/main/resources/attendances.csv";

        //when-then
        assertDoesNotThrow(() -> new FileReader(file));
    }
}
