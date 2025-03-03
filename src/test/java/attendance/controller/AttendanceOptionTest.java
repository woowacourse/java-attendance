package attendance.controller;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class AttendanceOptionTest {

    @Test
    void 잘못된_기능_입력을_하는_경우_예외가_발생한다() {
        // when & then
        assertThatThrownBy(() -> AttendanceOption.find("출석"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("[ERROR] 잘못된 기능을 입력하셨습니다.");
    }
}
