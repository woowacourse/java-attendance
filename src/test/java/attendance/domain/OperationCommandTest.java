package attendance.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.*;

class OperationCommandTest {

    @CsvSource(value = {
            "1,ATTENDANCE_CONFIRMATION",
            "2,ATTENDANCE_MODIFICATION",
            "3,CREW_ATTENDANCES_CHECK",
            "4,EXPULSION_CHECK",
            "Q,QUIT"
    })
    @ParameterizedTest
    void 입력_값을_알려주면_어떤_기능인지_알려준다(String commandText, OperationCommand expected) {
        // When
        OperationCommand operationCommand = OperationCommand.from(commandText);

        // Then
        assertThat(operationCommand).isEqualTo(expected);
    }

    @Test
    void 등록되지_않은_기능을_입력하면_기능을_실행되지_않는다() {
        // Given
        String commandText = "";

        // When & Then
        assertThatThrownBy(() -> OperationCommand.from(commandText))
                .isInstanceOf(IllegalArgumentException.class);
    }
}