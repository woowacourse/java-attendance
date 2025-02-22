package attendance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import attendance.common.ErrorMessage;
import attendance.controller.AttendanceOption;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class AttendanceOptionTest {

    @Test
    void 기능을_입력한다() {
        String input = "1";

        assertThat(AttendanceOption.find(input)).isEqualTo(AttendanceOption.MARK);
    }

    @ParameterizedTest
    @ValueSource(strings = {"a", "#"})
    void 기능번호_이외에_입력을_하면_에러를_발생한다(String input) {
        assertThatThrownBy(() -> AttendanceOption.find(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.INVALID_OPTION_INPUT.getMessage());
    }
}
