package domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceManagerTest {
    @Test
    @DisplayName("닉네임과 등교시간을 가지고 출석을 기록한다")
    void should_attend_by_nickname_and_attending_time() {
        // given
        NickName nickName = new NickName("후우");
        String time = "10:00";
        AttendanceManager attendanceManager = new AttendanceManager();

        // when
        attendanceManager.attend(nickName, time);

        // then
        assertThat(attendanceManager).isNotEqualTo(new AttendanceManager());
    }
}
