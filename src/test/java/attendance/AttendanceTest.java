package attendance;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import attendance.domain.AttendanceFormatter;
import attendance.domain.AttendanceManager;
import attendance.domain.AttendanceStatus;
import attendance.exception.AttendanceException;

public class AttendanceTest {
    private AttendanceFormatter attendanceFormatter;

    @BeforeEach
    void setup() {
        attendanceFormatter = new AttendanceFormatter();
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    @NullSource
    @DisplayName("닉네임이 유효하지 않으면 예외가 발생한다")
    void testNickname(String nickname) {
        var time = LocalDateTime.parse("2007-12-03T10:15:30");
        AttendanceManager attendanceManager = new AttendanceManager();

        assertThatThrownBy(() -> attendanceManager.addAttendance(nickname, time))
            .isInstanceOf(AttendanceException.class);
    }

    @Test
    @DisplayName("출석 후 출석 기록을 확인할 수 있다.")
    void test() {
        var nickname = "몽이2";
        var time = LocalDateTime.parse("2024-12-03T10:15:30");
        AttendanceManager attendanceManager = new AttendanceManager();
        attendanceManager.addAttendance(nickname, time);
        LocalDateTime attendanceTime = attendanceManager.getAttendanceTime(nickname);
        AttendanceStatus attendanceStatus = attendanceManager.getAttendanceStatus(nickname);

        String result = attendanceFormatter.formattedTime(attendanceTime, attendanceStatus);
        assertThat(result)
            .isEqualTo("12월 3일 화요일 10:15 (출석)");
    }
}
