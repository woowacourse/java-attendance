package attendance;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import attendance.common.exception.AttendanceArgumentException;
import attendance.common.exception.AttendanceFileException;
import attendance.domain.AttendanceFileReader;
import attendance.domain.attendanceBook.Attendance;
import attendance.domain.attendanceBook.AttendanceBook;
import attendance.domain.attendanceManager.AttendanceManager;
import attendance.domain.attendanceManager.AttendanceRegister;

public class AttendanceRegisterTest {
    private static final String TEST_FILE = "/attendances.csv";

    private AttendanceManager attendanceManager;
    private AttendanceBook attendanceBook;

    @BeforeEach
    void setUp() throws AttendanceFileException {
        var repository = new AttendanceFileReader(TEST_FILE);
        var lines = repository.getLines();
        attendanceBook = AttendanceBook.from(lines);
        attendanceManager = new AttendanceRegister(attendanceBook);
    }

    @Test
    @DisplayName("닉네임과 출석 정보을 입력하면, 출석 정보를 저장한다.")
    void test_attendance() {
        var nickname = "이든";
        var date = LocalDate.of(2024, 12, 20);
        var time = LocalTime.of(10, 1);

        var attendance = new Attendance(LocalDateTime.of(date, time));

        attendanceManager.manage(nickname, date, time);

        assertThat(attendanceBook.findAttendance(nickname, attendance)).isEqualTo(attendance);
    }

    @Test
    @DisplayName("등록되지 않은 닉네임을 입력하면, 예외가 발생된다.")
    void error_notRegisteredNickname() {
        var nickname = "고든";
        var date = LocalDate.of(2024, 12, 20);
        var time = LocalTime.of(10, 1);

        assertThatThrownBy(() -> attendanceManager.manage(nickname, date, time))
            .isInstanceOf(AttendanceArgumentException.class)
            .hasMessageContaining("등록되지 않은 닉네임");
    }

    @Test
    @DisplayName("다시 출석할 경우, 예외가 발생한다.")
    void error_retireAttendance() {
        var nickname = "이든";
        var date = LocalDate.of(2024, 12, 13);
        var time = LocalTime.of(10, 1);

        assertThatThrownBy(() -> attendanceManager.manage(nickname, date, time))
            .isInstanceOf(AttendanceArgumentException.class)
            .hasMessageContaining("이미 출석되었습니다. 수정 기능을 이용해주세요.");
    }

}
