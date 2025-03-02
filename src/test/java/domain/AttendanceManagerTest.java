package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceManagerTest {
    @Test
    @DisplayName("닉네임과 등교시간을 가지고 출석을 기록한다")
    void should_attend_by_nickname_and_attending_time() {
        // given
        NickName nickName = new NickName("후우");
        LocalTime attendingTime = LocalTime.parse("10:00", DateTimeFormatter.ofPattern("HH:mm"));
        AttendanceManager attendanceManager = new AttendanceManager();

        // when
        attendanceManager.attend(nickName, attendingTime);

        // then
        assertThat(attendanceManager).isNotEqualTo(new AttendanceManager());
    }
}
