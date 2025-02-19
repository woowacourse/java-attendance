package attendance;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import attendance.domain.AttendanceManager;
import attendance.dto.AttendanceDateDto;
import attendance.exception.AttendanceArgumentException;

public class AttendanceManagerTest {

    @BeforeAll
    void setup() {
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    @NullSource
    @DisplayName("닉네임이 유효하지 않으면 예외가 발생한다")
    void testNicknameInvalid(String nickname) {
        var time = LocalDateTime.parse("2007-12-03T10:15:30");
        AttendanceManager attendanceManager = new AttendanceManager();

        assertThatThrownBy(() -> attendanceManager.addAttendance(nickname, time))
            .isInstanceOf(AttendanceArgumentException.class);
    }

//    @Test
//    @DisplayName("출석 후 출석 기록을 확인할 수 있다.")
//    void testAttendanceResult() {
//        var nickname = "몽이2";
//        var time = LocalDateTime.parse("2024-12-03T10:15:30");
//        AttendanceManager attendanceManager = new AttendanceManager();
//        attendanceManager.addAttendance(nickname, time);
//
//        AttendanceDateDto attendanceResult = attendanceManager.getAttendanceResult(nickname, time.toLocalDate());
//
//        String result = dateTimeFormatter.formattedTime(attendanceResult);
//        assertThat(result)
//            .isEqualTo("12월 3일 화요일 10:15 (출석)");
//    }
//
//    @Test
//    @DisplayName("이미 출석한 경우, 예외를 반환한다.")
//    void testDuplicateAttendance() {
//        var nickname = "몽이2";
//        var time = LocalDateTime.parse("2024-12-03T10:15:30");
//        var DUPLICATE_ATTENDANCE_DATE = "이미 출석되었습니다. 수정 기능을 이용해주세요.";
//        AttendanceManager attendanceManager = new AttendanceManager();
//        attendanceManager.addAttendance(nickname, time);
//
//        assertThatThrownBy(() -> attendanceManager.addAttendance(nickname, time))
//            .isInstanceOf(AttendanceArgumentException.class)
//            .hasMessage(DUPLICATE_ATTENDANCE_DATE);
//    }
}
