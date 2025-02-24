package attendance.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("출석 기록 테스트")
class AttendanceLogTest {

    @DisplayName("닉네임과 등교 시간으로 출석 로그를 생성할 수 있다.")
    @Test
    void createTestWithNicknameAndAttendanceDateTime() {
        // given
        Nickname nickname = new Nickname("벨로");
        LocalDate attendanceDate = LocalDate.of(2024, 12, 2);
        LocalTime attendanceTime = LocalTime.of(10, 0);

        // when & then
        assertThatCode(() -> new AttendanceLog(nickname, attendanceDate, attendanceTime))
                .doesNotThrowAnyException();
    }

    @DisplayName("닉네임이 null인 경우 예외가 발생한다.")
    @Test
    void shouldThrowException_WhenNicknameIsNull() {
        // given
        LocalDate attendanceDate = LocalDate.of(2024, 12, 2);
        LocalTime attendanceTime = LocalTime.of(10, 0);

        // when & then
        assertThatCode(() -> new AttendanceLog(null, attendanceDate, attendanceTime))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("닉네임은 null일 수 없습니다.");
    }

    @DisplayName("출석 시간이 없는 경우 결석으로 간주한다.")
    @Test
    void shouldAbsent_WhenAttendanceTimeIsNull() {
        // given
        Nickname nickname = new Nickname("벨로");
        LocalDate attendanceDate = LocalDate.of(2024, 12, 2);

        // when
        AttendanceLog attendanceLog = new AttendanceLog(nickname, attendanceDate);
        boolean isAbsent = attendanceLog.isAbsent();

        // then
        assertThat(isAbsent)
                .isTrue();
    }

    @DisplayName("출석 날짜가 null인 경우 예외가 발생한다.")
    @Test
    void shouldThrowException_WhenAttendanceDateIsNull() {
        // given
        Nickname nickname = new Nickname("벨로");
        LocalTime attendanceTime = LocalTime.of(10, 0);

        // when & then
        assertThatCode(() -> new AttendanceLog(nickname, null, attendanceTime))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("출석 날짜는 null일 수 없습니다.");
    }
}
