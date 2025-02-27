package attendance.view.processor;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class InputPreprocessorTest {

    @DisplayName("입력값의 양옆 공백을 제거할 수 있다")
    @Test
    void 입력값의_양옆_공백을_제거할_수_있다() {
        String result = InputPreprocessor.removeSideSpace(" 내용  ");
        Assertions.assertThat(result).isEqualTo("내용");
    }
}