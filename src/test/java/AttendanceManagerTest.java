import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

public class AttendanceManagerTest {

    @Test
    void 등록되지_않은_닉네임의_출석을_등록하면_예외가_발생한다() {
        assertThatThrownBy(() -> new AttendanceManager().attend("이든", "09:59"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 등록되지 않은 닉네임입니다.");
    }
    
}
