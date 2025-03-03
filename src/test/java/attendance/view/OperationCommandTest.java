package attendance.view;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class OperationCommandTest {

    @CsvSource(value = {
            "1,ATTENDANCE_CONFIRMATION",
            "2,ATTENDANCE_MODIFICATION",
            "3,CREW_ATTENDANCES_INQUIRY",
            "4,PENALTY_CREWS_INQUIRY",
            "Q,QUIT"
    })
    @ParameterizedTest
    void 실행_입력을_받으면_알맞은_기능_실행을_반환한다(String commandInput, OperationCommand expected) {
        assertThat(OperationCommand.from(commandInput)).isEqualByComparingTo(expected);
    }

    @Test
    void 존재하지_입력은_기능_실행을_생성할_수_없다() {
        assertThatThrownBy(() -> OperationCommand.from("5"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 기능 입니다.");
    }

}
