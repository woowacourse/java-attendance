package attendance.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("출석 매니저 테스트")
class AttendanceManagerTest {

    private final AttendanceManager attendanceManager = new AttendanceManager();

    @Test
    @DisplayName("닉네임과 입력 시간으로 크루의 출석을 등록한다")
    void 닉네임과_입력_시간으로_크루의_출석을_등록한다() {
        // given
        Nickname nickname = new Nickname("비타");
        LocalTime attendanceTime = LocalTime.of(10, 0);

        // when
        Attendance attendance = attendanceManager.processCheck(nickname, attendanceTime);

        // then
        assertThat(attendance.getDateTime().toLocalTime())
                .isEqualTo(attendanceTime);
    }
}
