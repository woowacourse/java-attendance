package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("1.1 닉네임과 등교 시간을 받으면 오늘 날짜로 출석 기록을 생성할 수 있다.")
public class AttendanceTest {
    @Test
    @DisplayName("10시 5분에 출석할 경우 출석으로 처리한다.")
    void attendTest1() {
        // given
        Attendance attendance = new Attendance();
        String nickname = "노랑";
        LocalTime time = LocalTime.of(10, 5);
        // when
        String attendanceRecord = attendance.attend(nickname, time);
        // then
        assertThat(attendanceRecord).isEqualTo("출석");
    }

    @Test
    @DisplayName("10시 30분에 출석할 경우 지각으로 처리한다.")
    void attendTest2() {
        // given
        Attendance attendance = new Attendance();
        String nickname = "노랑";
        LocalTime time = LocalTime.of(10, 30);
        // when
        String attendanceRecord = attendance.attend(nickname, time);
        // then
        assertThat(attendanceRecord).isEqualTo("지각");
    }

    @Test
    @DisplayName("10시 30분 1초에 출석할 경우 결석으로 처리한다.")
    void attendTest3() {
        // given
        Attendance attendance = new Attendance();
        String nickname = "노랑";
        LocalTime time = LocalTime.of(10, 30, 1);
        // when
        String attendanceRecord = attendance.attend(nickname, time);
        // then
        assertThat(attendanceRecord).isEqualTo("결석");
    }
}
