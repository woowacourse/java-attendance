package presentation.view;

import java.util.List;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FileInputViewTest {
    @DisplayName("fileInputView에서 파일을 읽어서 Map으로 변환한다")
    @Test
    void test() {
        FileInputView inputView = new FileInputView();
        Map<String, List<String>> fileInput = inputView.getFileInput();

        Assertions.assertThat(fileInput.get("쿠키")).hasSize(8);
        Assertions.assertThat(fileInput.get("빙봉")).hasSize(11);
    }
}
