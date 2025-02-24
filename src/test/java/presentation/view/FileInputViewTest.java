package presentation.view;

import java.util.List;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FileInputViewTest {
    @DisplayName("출석 기록 파일을 읽어서 출석 데이터를 반환한다")
    @Test
    void test() {
        AttendanceFileInputView inputView = new AttendanceFileInputView();
        Map<String, List<String>> fileInput = inputView.getAttendanceFileInput();

        Assertions.assertThat(fileInput.get("쿠키")).hasSize(8);
        Assertions.assertThat(fileInput.get("빙봉")).hasSize(11);
    }
}
