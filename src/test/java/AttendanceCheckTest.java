import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalTime;
import org.junit.jupiter.api.Test;

public class AttendanceCheckTest {

    @Test
    void 닉네임과_등교_시간을_입력하면_출석할_수_있다() {
        final var nickname = "에드";
        final var attendanceTime = LocalTime.of(9, 59);

        AttendanceCheck.attend(nickname, attendanceTime);
        final var actual = AttendanceCheck.getAttendanceStatus(nickname);
        final var expected = "출석";
        assertEquals(expected, actual);
    }

    @Test
    void 출석_후_출석_기록을_확인한다() {
        final var nickname = "에드";
        final var attendanceTime = LocalTime.of(9, 59);

        AttendanceCheck.attend(nickname, attendanceTime);

        final var attendanceTimeRecord = AttendanceCheck.getAttendanceTime(nickname);
        final var attendanceStatus = AttendanceCheck.getAttendanceStatus(nickname);

        assertEquals(attendanceTimeRecord, attendanceTime);
        assertEquals(attendanceStatus, "출석");
    }

    @Test
    void 이미_출석한_상태에서_출석을_시도하면_예외처리() {
        final var nickname = "에드";
        final var attendanceTime = LocalTime.of(9, 59);

        AttendanceCheck.attend(nickname, attendanceTime);

        assertThatThrownBy(() -> AttendanceCheck.attend(nickname, attendanceTime))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("[ERROR] 이미 출석이 완료되었습니다. 수정 기능을 이용하세요.");
    }

    //교육 시간은 월요일은 13:00~18:00, 화요일~금요일은 10:00~18:00이다.
    //해당 요일의 시작 시각으로부터 5분 초과는 지각으로 간주한다.
    //해당 요일의 시작 시각으로부터 30분 초과는 결석으로 간주한다.
    @Test
    void 시작_시간으로부터_5분_초과는_지각으로_처리한다() {
        final var nickname = "에드";
        final var attendanceTime = LocalTime.of(10, 6);

        AttendanceCheck.attend(nickname, attendanceTime);
        final var expected = "지각";
        assertEquals(expected, AttendanceCheck.getAttendanceStatus(nickname));
    }
}
