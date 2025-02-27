package attendance;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import attendance.utils.Parser;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CampusTimeTest {

    @DisplayName("입력한 시간에 콜론이 있는지 확인")
    @Test
    void checkTimeFormat() {
        //given
        String attendanceTime = "09:59";

        //when
        List<String> dividedTime = Parser.divideByColon(attendanceTime);

        //then
        assertThat(dividedTime.size()).isEqualTo(2);

    }
}
