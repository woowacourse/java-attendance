package attendance.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class CommandTest {

    @DisplayName("커멘드를 찾을 수 있다.")
    @ParameterizedTest
    @CsvSource({
            "1, ATTENDANCE",
            "2, ATTENDANCE_UPDATE",
            "3, ATTENDANCE_RESULT",
            "4, EMERGENCY_SUBJECTS",
            "Q, QUIT"
    })
    void from(String value, Command expected) {
        //given //when
        Command actual = Command.from(value);

        //then
        assertThat(actual).isSameAs(expected);
    }
}
