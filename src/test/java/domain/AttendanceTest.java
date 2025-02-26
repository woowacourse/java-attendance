package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("1. 출석 확인 기능")
public class AttendanceTest {
    @Test
    @DisplayName("1.1 닉네임과 등교 시간을 받으면 오늘 날짜로 출석 기록을 생성할 수 있다.")
    void test() {
        // given
        Attendance attendance = new Attendance();
        String nickname = "노랑";
        LocalTime time = LocalTime.of(10, 0);
        // when
        String attendanceRecord = attendance.attend(nickname, time);
        // then
        assertThat(attendanceRecord).isEqualTo("출석");
    }
}
