import dto.AttendanceRecordDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import view.FileInput;
import view.Parser;

import java.util.List;

public class FileInputTest {
    @DisplayName("파일에서 쿠키의 이름을 가져온다")
    @Test
    void file_input_test() {
        FileInput fileInput = new FileInput();
        List<AttendanceRecordDto> fileInit = fileInput.getFileInit();
        Assertions.assertThat(fileInit.get(2)).isEqualTo(new AttendanceRecordDto("빙티", Parser.stringToLocalDateTime("2024-12-13 10:07")));
    }
}
