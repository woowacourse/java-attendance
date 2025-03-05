package attendance.model;

import static attendance.model.TestFixtures.BELLO_NICKNAME;
import static attendance.model.TestFixtures.LOCAL_DATE_2024_12_02;
import static attendance.model.TestFixtures.LOCAL_DATE_TIME_2024_12_02_10_00;
import static attendance.model.TestFixtures.LOCAL_TIME_10_00;
import static attendance.model.TestFixtures.NEO_NICKNAME;
import static attendance.model.TestFixtures.createAttendanceLog;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.EnumMap;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("출석부 테스트")
class AttendanceBookTest {

    @DisplayName("등록된 닉네임인 경우 예외가 발생하지 않는다.")
    @Test
    void shouldNotThrowException_WhenUseRegisterNickname() {
        // given
        AttendanceLogs attendanceLogs = new AttendanceLogs();
        NicknameRegistry nicknameRegistry = new NicknameRegistry(Set.of(BELLO_NICKNAME));
        AttendanceBook attendanceBook = new AttendanceBook(attendanceLogs, nicknameRegistry);

        // when & then
        assertThatCode(() -> attendanceBook.validateNicknameExists(BELLO_NICKNAME))
                .doesNotThrowAnyException();
    }

    @DisplayName("등록되지 않은 닉네임인 경우 예외가 발생한다.")
    @Test
    void shouldThrowException_WhenUseNoRegisterNickname() {
        // given
        AttendanceLogs attendanceLogs = new AttendanceLogs();
        NicknameRegistry nicknameRegistry = new NicknameRegistry(Set.of());
        AttendanceBook attendanceBook = new AttendanceBook(attendanceLogs, nicknameRegistry);

        // when & then
        assertThatCode(() -> attendanceBook.validateNicknameExists(BELLO_NICKNAME))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("등록되지 않은 닉네임입니다.");
    }

    @DisplayName("출석에 성공한 경우 출석 기록 내용을 확인할 수 있다.")
    @Test
    void attendTest() {
        // given
        AttendanceLogs attendanceLogs = new AttendanceLogs();
        NicknameRegistry nicknameRegistry = new NicknameRegistry(Set.of(BELLO_NICKNAME));
        AttendanceBook attendanceBook = new AttendanceBook(attendanceLogs, nicknameRegistry);

        // when
        attendanceBook.attend(BELLO_NICKNAME, LOCAL_DATE_TIME_2024_12_02_10_00);

        // then
        LocalTime attendanceTime = attendanceBook.findAttendanceTimeByNicknameAndDate(
                BELLO_NICKNAME, LOCAL_DATE_2024_12_02);
        assertThat(attendanceTime)
                .isEqualTo(LOCAL_TIME_10_00);
    }

    @DisplayName("특정 닉네임과 날짜로 출석 시간을 조회할 수 있다.")
    @Test
    void findAttendanceTimeByNicknameAndDateTest() {
        // given
        AttendanceLogs attendanceLogs = new AttendanceLogs();
        attendanceLogs.add(createAttendanceLog(BELLO_NICKNAME, LOCAL_DATE_2024_12_02, LOCAL_TIME_10_00));
        NicknameRegistry nicknameRegistry = new NicknameRegistry(Set.of(BELLO_NICKNAME));
        AttendanceBook attendanceBook = new AttendanceBook(attendanceLogs, nicknameRegistry);

        // when
        LocalTime attendanceTime = attendanceBook.findAttendanceTimeByNicknameAndDate(
                BELLO_NICKNAME, LOCAL_DATE_2024_12_02);

        // then
        assertThat(attendanceTime)
                .isEqualTo(LOCAL_TIME_10_00);
    }

    @DisplayName("특정 닉네임과 날짜로 출석 시간을 조회할 때, 기록이 없는 경우 null을 반환한다.")
    @Test
    void findAttendanceTimeByNicknameAndDateTest_WhenNoAttendanceLog() {
        // given
        AttendanceLogs attendanceLogs = new AttendanceLogs();
        NicknameRegistry nicknameRegistry = new NicknameRegistry(Set.of(BELLO_NICKNAME));
        AttendanceBook attendanceBook = new AttendanceBook(attendanceLogs, nicknameRegistry);

        // when
        LocalTime attendanceTime = attendanceBook.findAttendanceTimeByNicknameAndDate(
                BELLO_NICKNAME, LOCAL_DATE_2024_12_02);

        // then
        assertThat(attendanceTime)
                .isNull();
    }

    @DisplayName("존재하는 출석 기록을 수정할 수 있다.")
    @Test
    void editTest_WhenExistAttendanceLog() {
        // given
        AttendanceLogs attendanceLogs = new AttendanceLogs();
        attendanceLogs.add(createAttendanceLog(BELLO_NICKNAME, LOCAL_DATE_2024_12_02, LocalTime.of(10, 31)));
        NicknameRegistry nicknameRegistry = new NicknameRegistry(Set.of(BELLO_NICKNAME));
        AttendanceBook attendanceBook = new AttendanceBook(attendanceLogs, nicknameRegistry);

        // when
        attendanceBook.edit(BELLO_NICKNAME, LOCAL_DATE_TIME_2024_12_02_10_00);

        // then
        LocalTime attendanceTime = attendanceBook.findAttendanceTimeByNicknameAndDate(
                BELLO_NICKNAME, LOCAL_DATE_2024_12_02);
        AttendanceType attendanceType = attendanceBook.determineAttendanceType(LOCAL_DATE_2024_12_02, attendanceTime);
        assertThat(attendanceTime)
                .isEqualTo(LOCAL_TIME_10_00);
        assertThat(attendanceType)
                .isSameAs(AttendanceType.PRESENT);
    }

    @DisplayName("출석 기록이 없던 날짜의 출석 기록을 수정할 수 있다.")
    @Test
    void editTest_WhenNoAttendanceLog() {
        // given
        AttendanceLogs attendanceLogs = new AttendanceLogs();
        NicknameRegistry nicknameRegistry = new NicknameRegistry(Set.of(BELLO_NICKNAME));
        AttendanceBook attendanceBook = new AttendanceBook(attendanceLogs, nicknameRegistry);

        // when
        attendanceBook.edit(BELLO_NICKNAME, LOCAL_DATE_TIME_2024_12_02_10_00);

        // then
        LocalTime attendanceTime = attendanceBook.findAttendanceTimeByNicknameAndDate(
                BELLO_NICKNAME, LOCAL_DATE_2024_12_02);
        AttendanceType attendanceType = attendanceBook.determineAttendanceType(LOCAL_DATE_2024_12_02, attendanceTime);
        assertThat(attendanceTime)
                .isEqualTo(LOCAL_TIME_10_00);
        assertThat(attendanceType)
                .isSameAs(AttendanceType.PRESENT);
    }

    @DisplayName("특정 닉네임의 특정 월의 전날까지의 출석 기록을 조회할 수 있다.")
    @Test
    void findAttendanceLogsByNicknameAndInMonthTest() {
        // given
        AttendanceLogs attendanceLogs = new AttendanceLogs();
        attendanceLogs.add(createAttendanceLog(BELLO_NICKNAME, LOCAL_DATE_2024_12_02, LOCAL_TIME_10_00));
        NicknameRegistry nicknameRegistry = new NicknameRegistry(Set.of(BELLO_NICKNAME));
        AttendanceBook attendanceBook = new AttendanceBook(attendanceLogs, nicknameRegistry);

        // when & then
        assertThat(attendanceBook.findAttendanceLogsByNicknameAndInMonth(BELLO_NICKNAME, LocalDate.of(2024, 12, 5)))
                .hasSize(3);
    }

    @DisplayName("닉네임과 기준 날짜로 이번 달 각 출석 유형 횟수를 조회할 수 있다.")
    @Test
    void countAttendanceTypesTest() {
        // given
        LocalDate today = LocalDate.of(2024, 12, 5);
        LocalDate yesterday = today.minusDays(1);
        AttendanceLogs attendanceLogs = new AttendanceLogs();
        attendanceLogs.add(createAttendanceLog(BELLO_NICKNAME, yesterday, LOCAL_TIME_10_00));
        attendanceLogs.add(createAttendanceLog(BELLO_NICKNAME, today, LOCAL_TIME_10_00));
        NicknameRegistry nicknameRegistry = new NicknameRegistry(attendanceLogs.getAllNicknames());
        AttendanceBook attendanceBook = new AttendanceBook(attendanceLogs, nicknameRegistry);

        // when
        EnumMap<AttendanceType, Integer> map = attendanceBook.countAttendanceTypes(BELLO_NICKNAME, today);

        // then
        assertThat(map.get(AttendanceType.PRESENT))
                .isEqualTo(1);
        assertThat(map.get(AttendanceType.LATE))
                .isEqualTo(0);
        assertThat(map.get(AttendanceType.ABSENT))
                .isEqualTo(2);
    }

    @DisplayName("출석 유형 횟수로 출석 경고 수준을 판단할 수 있다.")
    @Test
    void determineWarningLevelTest() {
        // given
        EnumMap<AttendanceType, Integer> counted = new EnumMap<>(AttendanceType.class);
        counted.put(AttendanceType.PRESENT, 1);
        counted.put(AttendanceType.LATE, 0);
        counted.put(AttendanceType.ABSENT, 2);
        AttendanceBook attendanceBook = new AttendanceBook(new AttendanceLogs(), new NicknameRegistry(Set.of()));

        // when
        AttendanceWarningLevel warningLevel = attendanceBook.determineWarningLevel(counted);

        // then
        assertThat(warningLevel)
                .isSameAs(AttendanceWarningLevel.WARNING);
    }

    @DisplayName("닉네임 등록 명단에서 닉네임 목록을 조회할 수 있다.")
    @Test
    void getNicknamesTest() {
        // given
        Set<Nickname> nicknames = Set.of(BELLO_NICKNAME, NEO_NICKNAME);
        AttendanceBook attendanceBook = new AttendanceBook(new AttendanceLogs(), new NicknameRegistry(nicknames));

        // when
        Set<Nickname> result = attendanceBook.getNicknames();

        // then
        assertThat(result)
                .containsExactlyInAnyOrder(BELLO_NICKNAME, NEO_NICKNAME);
    }
}
