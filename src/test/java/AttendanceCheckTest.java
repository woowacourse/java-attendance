import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalTime;
import org.junit.jupiter.api.Test;

public class AttendanceCheckTest {

    //닉네임과 등교 시간을 입력하면 출석할 수 있다.
    //출석 후 출석 기록을 확인할 수 있다.
    //이미 출석한 경우, 다시 출석할 수 없으며 수정 기능을 이용하도록 안내한다.

    //닉네임을 입력해 주세요.
    //이든
    //등교 시간을 입력해 주세요.
    //09:59
    //
    //12월 05일 화요일 09:59 (출석)
    @Test
    void 닉네임과_등교_시간을_입력하면_출석할_수_있다() {
        final var nickname = "에드";
        final var attendanceTime = LocalTime.of(9, 59);

        final var actual = AttendanceCheck.attend(nickname, attendanceTime);
        final var expected = "출석";
        assertEquals(expected, actual);
    }
}
