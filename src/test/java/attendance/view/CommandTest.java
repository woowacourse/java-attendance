package attendance.view;

import attendance.io.view.Command;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class CommandTest {

    @ParameterizedTest
    @CsvSource({
            "1, ATTENDANCE",
            "2, ATTENDANCE_UPDATE",
            "3, ATTENDANCE_CHECK",
            "4, ATTENDANCE_WARNING_CHECK",
            "Q, QUIT",
    })
    void 입력값에_따라_커멘드로_변환할_수_있다(String value, Command expected) {
        //when
        Command actual = Command.from(value);

        //then
        Assertions.assertThat(actual).isEqualTo(expected);
    }
}
