package attendance.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.EnumMap;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("출석 기록 목록 테스트")
class AttendanceLogsTest {

    @DisplayName("출석 로그를 저장할 수 있다.")
    @Test
    void attendanceLogAddTest() {
        // given
        LocalDate attendanceDate = LocalDate.of(2024, 12, 2);
        LocalTime attendanceTime = LocalTime.of(10, 0);
        AttendanceLogs attendanceLogs = new AttendanceLogs();

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
        AttendanceLogs attendanceLogs = new AttendanceLogs();
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
    void findAttendanceLogsByNicknameUpToPreviousDayTest() {
        // given
        Nickname nickname = new Nickname("벨로");
        LocalDate today = LocalDate.of(2024, 12, 3);
        LocalDate yesterday = today.minusDays(1);
        LocalTime attendanceTime = LocalTime.of(10, 0);

        AttendanceLogs attendanceLogs = new AttendanceLogs();
        attendanceLogs.add(new AttendanceLog(nickname, yesterday, attendanceTime));
        attendanceLogs.add(new AttendanceLog(nickname, today, attendanceTime));

        // when
        List<AttendanceLog> findLogs = attendanceLogs.findAllByNicknameInMonth(nickname, today);

        // then
        assertThat(findLogs)
                .hasSize(1)
                .containsExactly(new AttendanceLog(nickname, yesterday, attendanceTime));
    }

    @DisplayName("닉네임과 특정 날짜로 출석 로그를 조회할 수 있다.")
    @Test
    void findByNicknameAndAttendanceDateTest() {
        // given
        Nickname nickname = new Nickname("벨로");
        LocalDate today = LocalDate.of(2024, 12, 3);
        LocalTime attendanceTime = LocalTime.of(10, 0);

        AttendanceLogs attendanceLogs = new AttendanceLogs();
        AttendanceLog attendanceLog = new AttendanceLog(nickname, today, attendanceTime);
        attendanceLogs.add(attendanceLog);

        // when
        AttendanceLog findLog = attendanceLogs.findByNicknameAndAttendanceDate(nickname, today);

        // then
        assertThat(findLog)
                .isEqualTo(attendanceLog);
    }

    @DisplayName("닉네임과 특정 날짜에 해당하는 출석 로그가 없는 경우 시간이 없는 로그 객체를 반환한다.")
    @Test
    void findByNicknameAndAttendanceDateNoExistTest() {
        // given
        Nickname nickname = new Nickname("벨로");
        LocalDate today = LocalDate.of(2024, 12, 3);
        AttendanceLogs attendanceLogs = new AttendanceLogs();

        // when
        AttendanceLog findLog = attendanceLogs.findByNicknameAndAttendanceDate(nickname, today);

        // then
        assertThat(findLog.isNotRecorded())
                .isTrue();
    }

    @DisplayName("출석 로그를 수정할 수 있다.")
    @Test
    void editAttendanceLogTest() {
        // given
        Nickname nickname = new Nickname("벨로");
        LocalDate today = LocalDate.of(2024, 12, 3);
        LocalTime attendanceTime = LocalTime.of(10, 0);

        AttendanceLogs attendanceLogs = new AttendanceLogs();
        AttendanceLog attendanceLog = new AttendanceLog(nickname, today, attendanceTime);
        attendanceLogs.add(attendanceLog);

        // when
        LocalDateTime updateDateTime = LocalDateTime.of(today, LocalTime.of(13, 0));
        attendanceLogs.edit(nickname, updateDateTime);
        AttendanceLog edited = attendanceLogs.findByNicknameAndAttendanceDate(nickname, updateDateTime.toLocalDate());

        // then
        assertThat(edited.getAttendanceTime())
                .isEqualTo(LocalTime.of(13, 0));
    }

    @DisplayName("닉네임과 기준 날짜로 이번 달 각 출석 유형 횟수를 조회할 수 있다.")
    @Test
    void countAllAttendanceTypeTest() {
        // given
        Nickname nickname = new Nickname("벨로");
        LocalDate today = LocalDate.of(2024, 12, 5);
        LocalDate yesterday = today.minusDays(1);
        LocalTime attendanceTime = LocalTime.of(10, 0);

        AttendanceLogs attendanceLogs = new AttendanceLogs();
        attendanceLogs.add(new AttendanceLog(nickname, yesterday, attendanceTime));
        attendanceLogs.add(new AttendanceLog(nickname, today, attendanceTime));

        // when
        EnumMap<AttendanceType, Integer> map = attendanceLogs.countAllAttendanceType(nickname, today);

        // then
        assertThat(map.get(AttendanceType.PRESENT))
                .isEqualTo(1);
        assertThat(map.get(AttendanceType.LATE))
                .isEqualTo(0);
        assertThat(map.get(AttendanceType.ABSENT))
                .isEqualTo(2);
    }
}
