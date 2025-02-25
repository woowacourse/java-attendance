package attendance.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("출석 기록 목록 테스트")
class AttendanceLogsTest {

    @DisplayName("출석 로그들을 전달하여 출석 로그 목록을 생성할 수 있다.")
    @Test
    void createTestWithAttendanceLogList() {
        // given
        LocalDate attendanceDate = LocalDate.of(2024, 12, 2);
        LocalTime attendanceTime = LocalTime.of(10, 0);
        Set<AttendanceLog> logs = new HashSet<>();
        logs.add(new AttendanceLog(new Nickname("네오"), attendanceDate, attendanceTime));
        logs.add(new AttendanceLog(new Nickname("벨로"), attendanceDate, attendanceTime));

        // when & then
        assertThatCode(() -> new AttendanceLogs(logs))
                .doesNotThrowAnyException();
    }

    @DisplayName("출석 로그를 저장할 수 있다.")
    @Test
    void attendanceLogAddTest() {
        // given
        LocalDate attendanceDate = LocalDate.of(2024, 12, 2);
        LocalTime attendanceTime = LocalTime.of(10, 0);
        AttendanceLogs attendanceLogs = new AttendanceLogs(new HashSet<>());

        // when
        AttendanceLog belloAttendanceLog = new AttendanceLog(new Nickname("벨로"), attendanceDate, attendanceTime);
        attendanceLogs.add(belloAttendanceLog);

        // then
        assertThat(attendanceLogs.contains(belloAttendanceLog))
                .isTrue();
    }

    @DisplayName("닉네임과 출석 날짜가 같은 출석 로그를 저장할 경우 예외가 발생한다.")
    @Test
    void shouldThrowException_WhenAddEqualAttendanceLog() {
        // given
        Nickname nickname = new Nickname("벨로");
        LocalDate attendanceDate = LocalDate.of(2024, 12, 2);
        LocalTime attendanceTime = LocalTime.of(10, 0);
        AttendanceLogs attendanceLogs = new AttendanceLogs(new HashSet<>());
        AttendanceLog beforeAttendanceLog = new AttendanceLog(nickname, attendanceDate, attendanceTime);
        AttendanceLog afterAttendanceLog = new AttendanceLog(nickname, attendanceDate, attendanceTime);
        attendanceLogs.add(beforeAttendanceLog);

        // when & then
        assertThatCode(() -> attendanceLogs.add(afterAttendanceLog))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("금일 출석 기록이 존재하여 추가되지 않았습니다. 수정이 필요한 경우 출석 수정 기능을 사용해주세요.");
    }

    @DisplayName("기준 날짜 전날까지의 출석 기록을 닉네임과 기준 날짜로 조회할 수 있다.")
    @Test
    void findAttendanceLogsByNicknameUpToPreviousDay() {
        // given
        Nickname nickname = new Nickname("벨로");
        LocalDate today = LocalDate.of(2024, 12, 3);
        LocalDate yesterday = today.minusDays(1);
        LocalTime attendanceTime = LocalTime.of(10, 0);

        AttendanceLogs attendanceLogs = new AttendanceLogs(Set.of(
                new AttendanceLog(nickname, yesterday, attendanceTime),
                new AttendanceLog(nickname, today, attendanceTime)
        ));

        // when
        List<AttendanceLog> findLogs = attendanceLogs.findByNicknameInMonth(nickname, today);

        // then
        assertThat(findLogs)
                .hasSize(1)
                .containsExactly(new AttendanceLog(nickname, yesterday, attendanceTime));
    }
}
