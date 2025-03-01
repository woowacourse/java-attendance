package attendance.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ReaderTest {

    @Test
    void 존재하는_파일_경로를_입력하면_파일_내용을_읽어온다() {

        // given

        // when & then
        Assertions.assertDoesNotThrow(() -> Reader.getContents("src/main/resources/attendances.csv"));
    }

    @Test
    void 존재하지_않는_파일_경로를_입력하면_예외가_발생한다() {

        // given

        // when & then
        org.assertj.core.api.Assertions.assertThatThrownBy(
                        () -> Reader.getContents("src/main/resources/not-exists.csv"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 파일을 읽어오는 중 오류가 발생했습니다.");
    }
}
