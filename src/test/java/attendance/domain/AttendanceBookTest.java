package attendance.domain;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import attendance.exception.AttendanceArgumentException;

class AttendanceBookTest {
    private final SystemDateTime systemDateTime = new AttendanceDateTime();
    private final AttendanceReader attendanceReader = new AttendanceReader("attendances.csv", systemDateTime);
    private final AttendanceBook attendanceBook = attendanceReader.load();

    AttendanceBookTest() throws FileNotFoundException {
    }

    @Test
    @DisplayName("닉네임을 입력하면, 현재 시간으로 출석한다.")
    void test_SaveAttendanceWhenEnterNickname() {
        var nickname = new Nickname("이든");
        var dateTime = LocalDateTime.of(2024, 12, 11, 10, 0);
        attendanceBook.attendance(nickname, dateTime);

        var attendance = new Attendance(dateTime);
        assertThat(attendanceBook.getAttendance(nickname, dateTime.toLocalDate())).isEqualTo(attendance);
    }

    @Test
    @DisplayName("등록되지 않은 닉네임으로 출석할 경우, 예외가 발생한다.")
    void error_attendanceNotRegisteredNickname() {
        var nickname = new Nickname("때지");
        var dateTime = systemDateTime.now();
        Assertions.assertThatThrownBy(() -> attendanceBook.attendance(nickname, dateTime))
            .isInstanceOf(AttendanceArgumentException.class)
            .hasMessageContaining("등록되지 않은");
    }
}
