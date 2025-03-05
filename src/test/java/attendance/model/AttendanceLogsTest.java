package attendance.model;

import static attendance.model.TestFixtures.BELLO_NICKNAME;
import static attendance.model.TestFixtures.LOCAL_DATE_2024_12_02;
import static attendance.model.TestFixtures.LOCAL_TIME_10_00;
import static attendance.model.TestFixtures.NEO_NICKNAME;
import static attendance.model.TestFixtures.createAttendanceLog;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.EnumMap;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("출석 기록 목록 테스트")
class AttendanceLogsTest {

    @DisplayName("출석 로그를 저장할 수 있다.")
    @Test
    void attendanceLogAddTest() {
        // given
        AttendanceLogs attendanceLogs = new AttendanceLogs();
        AttendanceLog attendanceLog = createAttendanceLog(BELLO_NICKNAME, LOCAL_DATE_2024_12_02, LOCAL_TIME_10_00);

        // when
        attendanceLogs.add(attendanceLog);

        // then
        assertThat(attendanceLogs.contains(attendanceLog))
                .isTrue();
    }

    @DisplayName("출석 로그 목록에서 존재하는 닉네임 목록을 조회할 수 있다.")
    @Test
    void getAllNicknamesTest() {
        // given
        AttendanceLogs attendanceLogs = new AttendanceLogs();
        AttendanceLog belloAttendanceLog = createAttendanceLog(BELLO_NICKNAME, LOCAL_DATE_2024_12_02, LOCAL_TIME_10_00);
        AttendanceLog neoAttendanceLog = createAttendanceLog(NEO_NICKNAME, LOCAL_DATE_2024_12_02, LOCAL_TIME_10_00);
        attendanceLogs.add(belloAttendanceLog);
        attendanceLogs.add(neoAttendanceLog);

        // when
        Set<Nickname> allNicknames = attendanceLogs.getAllNicknames();

        // then
        assertThat(allNicknames)
                .isEqualTo(Set.of(BELLO_NICKNAME, NEO_NICKNAME));
    }

    @DisplayName("닉네임과 출석 날짜가 같은 출석 로그를 저장할 경우 예외가 발생한다.")
    @Test
    void shouldThrowException_WhenAddEqualAttendanceLog() {
        // given
        AttendanceLogs attendanceLogs = new AttendanceLogs();
        AttendanceLog beforeAttendanceLog = createAttendanceLog(BELLO_NICKNAME, LOCAL_DATE_2024_12_02,
                LOCAL_TIME_10_00);
        AttendanceLog afterAttendanceLog = new AttendanceLog(BELLO_NICKNAME, LOCAL_DATE_2024_12_02, LOCAL_TIME_10_00);
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

        LocalDate today = LocalDate.of(2024, 12, 3);
        LocalDate yesterday = today.minusDays(1);

        AttendanceLogs attendanceLogs = new AttendanceLogs();
        attendanceLogs.add(createAttendanceLog(BELLO_NICKNAME, yesterday, LOCAL_TIME_10_00));
        attendanceLogs.add(createAttendanceLog(NEO_NICKNAME, yesterday, LOCAL_TIME_10_00));

        // when
        List<AttendanceLog> findLogs = attendanceLogs.findAllByNicknameInMonth(BELLO_NICKNAME, today);

        // then
        assertThat(findLogs)
                .hasSize(1)
                .containsExactly(new AttendanceLog(BELLO_NICKNAME, yesterday, LOCAL_TIME_10_00));
    }

    @DisplayName("닉네임과 특정 날짜로 출석 로그를 조회할 수 있다.")
    @Test
    void findByNicknameAndAttendanceDateTest() {
        // given
        AttendanceLogs attendanceLogs = new AttendanceLogs();
        AttendanceLog attendanceLog = createAttendanceLog(BELLO_NICKNAME, LOCAL_DATE_2024_12_02, LOCAL_TIME_10_00);
        attendanceLogs.add(attendanceLog);

        // when
        AttendanceLog findLog = attendanceLogs.findByNicknameAndAttendanceDate(BELLO_NICKNAME, LOCAL_DATE_2024_12_02);

        // then
        assertThat(findLog)
                .isEqualTo(attendanceLog);
    }

    @DisplayName("닉네임과 특정 날짜에 해당하는 출석 로그가 없는 경우 시간이 없는 로그 객체를 반환한다.")
    @Test
    void findByNicknameAndAttendanceDateNoExistTest() {
        // given
        AttendanceLogs attendanceLogs = new AttendanceLogs();

        // when
        AttendanceLog findLog = attendanceLogs.findByNicknameAndAttendanceDate(BELLO_NICKNAME, LOCAL_DATE_2024_12_02);

        // then
        assertThat(findLog.isNotRecorded())
                .isTrue();
    }

    @DisplayName("출석 로그를 수정할 수 있다.")
    @Test
    void editAttendanceLogTest() {
        // given
        AttendanceLogs attendanceLogs = new AttendanceLogs();
        AttendanceLog attendanceLog = createAttendanceLog(BELLO_NICKNAME, LOCAL_DATE_2024_12_02, LOCAL_TIME_10_00);
        attendanceLogs.add(attendanceLog);

        // when
        LocalDateTime updateDateTime = LocalDateTime.of(LOCAL_DATE_2024_12_02, LocalTime.of(13, 0));
        attendanceLogs.edit(createAttendanceLog(BELLO_NICKNAME, LOCAL_DATE_2024_12_02, updateDateTime.toLocalTime()));
        AttendanceLog edited = attendanceLogs.findByNicknameAndAttendanceDate(BELLO_NICKNAME,
                updateDateTime.toLocalDate());

        // then
        assertThat(edited.getAttendanceTime())
                .isEqualTo(LocalTime.of(13, 0));
    }

    @DisplayName("닉네임과 기준 날짜로 이번 달 각 출석 유형 횟수를 조회할 수 있다.")
    @Test
    void countAttendanceTypesTest() {
        // given
        Nickname nickname = new Nickname("벨로");
        LocalDate today = LocalDate.of(2024, 12, 5);
        LocalDate yesterday = today.minusDays(1);

        AttendanceLogs attendanceLogs = new AttendanceLogs();
        attendanceLogs.add(createAttendanceLog(BELLO_NICKNAME, yesterday, LOCAL_TIME_10_00));
        attendanceLogs.add(createAttendanceLog(BELLO_NICKNAME, today, LOCAL_TIME_10_00));

        // when
        EnumMap<AttendanceType, Integer> map = attendanceLogs.countAttendanceTypes(nickname, today);

        // then
        assertThat(map.get(AttendanceType.PRESENT))
                .isEqualTo(1);
        assertThat(map.get(AttendanceType.LATE))
                .isEqualTo(0);
        assertThat(map.get(AttendanceType.ABSENT))
                .isEqualTo(2);
    }
}
