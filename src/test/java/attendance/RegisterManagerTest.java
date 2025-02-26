package attendance;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import attendance.domain.AttendanceFileReader;
import attendance.domain.attendance.Attendance;
import attendance.domain.attendance.AttendanceBook;
import attendance.domain.attendanceManager.RegisterManager;
import attendance.exception.AttendanceArgumentException;
import attendance.exception.AttendanceFileException;

public class RegisterManagerTest {
    private final AttendanceFileReader attendanceFileReader = AttendanceFileReader.from("/attendances.csv");
    private final AttendanceBook attendanceBook = AttendanceBook.from(attendanceFileReader.getLines());
    private final RegisterManager attendanceRegister = new RegisterManager(attendanceBook);

    public RegisterManagerTest() throws AttendanceFileException {
    }

    @Test
    @DisplayName("닉네임과 출석 정보을 입력하면, 출석 정보를 저장한다.")
    void test_attendance() {
        var nickname = "이든";
        var dateTime = LocalDateTime.of(2024, 12, 11, 10, 1);

        var attendance = Attendance.from(dateTime);

        attendanceRegister.manage(nickname, dateTime);

        assertThat(attendanceBook.findAttendance(nickname, attendance)).isEqualTo(attendance);
    }

    @Test
    @DisplayName("등록되지 않은 닉네임을 입력하면, 예외가 발생된다.")
    void error_notRegisteredNickname() {
        var nickname = "고든";
        var dateTime = LocalDateTime.of(2024, 12, 20, 10, 1);

        assertThatThrownBy(() -> attendanceRegister.manage(nickname, dateTime))
            .isInstanceOf(AttendanceArgumentException.class)
            .hasMessageContaining("등록되지 않은 닉네임");
    }

    @Test
    @DisplayName("다시 출석할 경우, 예외가 발생한다.")
    void error_retireAttendance() {
        var nickname = "이든";
        var dateTime = LocalDateTime.of(2024, 12, 13, 10, 1);

        assertThatThrownBy(() -> attendanceRegister.manage(nickname, dateTime))
            .isInstanceOf(AttendanceArgumentException.class)
            .hasMessageContaining("이미 출석되었습니다. 수정 기능을 이용해주세요.");
    }

}
