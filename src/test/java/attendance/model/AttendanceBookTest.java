package attendance.model;

import static attendance.model.TestFixtures.BELLO_NICKNAME;
import static attendance.model.TestFixtures.NEO_NICKNAME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import attendance.dto.AttendanceWarning;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
        LocalDateTime attendanceDateTime = LocalDateTime.of(2024, 12, 2, 10, 0);

        // when
        attendanceBook.attend(BELLO_NICKNAME, attendanceDateTime);

        // then
        LocalTime attendanceTime = attendanceBook.findAttendanceTimeByNicknameAndDate(BELLO_NICKNAME,
                LocalDate.of(2024, 12, 2));
        assertThat(attendanceTime)
                .isEqualTo(LocalTime.of(10, 0));
    }

    @DisplayName("특정 닉네임과 날짜로 출석 시간을 조회할 수 있다.")
    @Test
    void findAttendanceTimeByNicknameAndDateTest() {
        // given
        AttendanceLogs attendanceLogs = new AttendanceLogs();
        attendanceLogs.add(new AttendanceLog(BELLO_NICKNAME, LocalDate.of(2024, 12, 2), LocalTime.of(10, 0)));
        NicknameRegistry nicknameRegistry = new NicknameRegistry(Set.of(BELLO_NICKNAME));
        AttendanceBook attendanceBook = new AttendanceBook(attendanceLogs, nicknameRegistry);

        // when
        LocalTime attendanceTime = attendanceBook.findAttendanceTimeByNicknameAndDate(BELLO_NICKNAME,
                LocalDate.of(2024, 12, 2));

        // then
        assertThat(attendanceTime)
                .isEqualTo(LocalTime.of(10, 0));
    }

    @DisplayName("특정 닉네임과 날짜로 출석 시간을 조회할 때, 기록이 없는 경우 null을 반환한다.")
    @Test
    void findAttendanceTimeByNicknameAndDateTest_WhenNoAttendanceLog() {
        // given
        AttendanceLogs attendanceLogs = new AttendanceLogs();
        NicknameRegistry nicknameRegistry = new NicknameRegistry(Set.of(BELLO_NICKNAME));
        AttendanceBook attendanceBook = new AttendanceBook(attendanceLogs, nicknameRegistry);

        // when
        LocalTime attendanceTime = attendanceBook.findAttendanceTimeByNicknameAndDate(BELLO_NICKNAME,
                LocalDate.of(2024, 12, 2));

        // then
        assertThat(attendanceTime)
                .isNull();
    }

    @DisplayName("존재하는 출석 기록을 수정할 수 있다.")
    @Test
    void editTest_WhenExistAttendanceLog() {
        // given
        AttendanceLogs attendanceLogs = new AttendanceLogs();
        attendanceLogs.add(new AttendanceLog(BELLO_NICKNAME, LocalDate.of(2024, 12, 3), LocalTime.of(10, 31)));
        NicknameRegistry nicknameRegistry = new NicknameRegistry(Set.of(BELLO_NICKNAME));
        AttendanceBook attendanceBook = new AttendanceBook(attendanceLogs, nicknameRegistry);

        // when
        LocalDateTime updateAttendanceDateTime = LocalDateTime.of(2024, 12, 3, 10, 5);
        attendanceBook.edit(BELLO_NICKNAME, updateAttendanceDateTime);

        // then
        LocalTime attendanceTime = attendanceBook.findAttendanceTimeByNicknameAndDate(new Nickname("벨로"),
                LocalDate.of(2024, 12, 3));
        AttendanceType attendanceType = attendanceBook.determineAttendanceType(LocalDate.of(2024, 12, 3),
                attendanceTime);
        assertThat(attendanceTime)
                .isEqualTo(LocalTime.of(10, 5));
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
        LocalDateTime updateAttendanceDateTime = LocalDateTime.of(2024, 12, 2, 10, 0);
        attendanceBook.edit(BELLO_NICKNAME, updateAttendanceDateTime);

        // then
        LocalTime attendanceTime = attendanceBook.findAttendanceTimeByNicknameAndDate(BELLO_NICKNAME,
                LocalDate.of(2024, 12, 2));
        AttendanceType attendanceType = attendanceBook.determineAttendanceType(LocalDate.of(2024, 12, 2),
                attendanceTime);
        assertThat(attendanceTime)
                .isEqualTo(LocalTime.of(10, 0));
        assertThat(attendanceType)
                .isSameAs(AttendanceType.PRESENT);
    }

    @DisplayName("특정 닉네임의 특정 월의 전날까지의 출석 기록을 조회할 수 있다.")
    @Test
    void findAttendanceLogsByNicknameAndInMonthTest() {
        // given
        AttendanceLogs attendanceLogs = new AttendanceLogs();
        attendanceLogs.add(new AttendanceLog(BELLO_NICKNAME, LocalDate.of(2024, 12, 2), LocalTime.of(10, 0)));
        NicknameRegistry nicknameRegistry = new NicknameRegistry(Set.of(BELLO_NICKNAME));
        AttendanceBook attendanceBook = new AttendanceBook(attendanceLogs, nicknameRegistry);

        // when & then
        assertThat(attendanceBook.findAttendanceLogsByNicknameAndInMonth(BELLO_NICKNAME, LocalDate.of(2024, 12, 5)))
                .hasSize(3);
    }

    @DisplayName("닉네임과 기준 날짜로 이번 달 각 출석 유형 횟수를 조회할 수 있다.")
    @Test
    void countAllAttendanceTypeTest() {
        // given
        LocalDate today = LocalDate.of(2024, 12, 5);
        LocalDate yesterday = today.minusDays(1);
        LocalTime attendanceTime = LocalTime.of(10, 0);
        AttendanceLogs attendanceLogs = new AttendanceLogs();
        attendanceLogs.add(new AttendanceLog(BELLO_NICKNAME, yesterday, attendanceTime));
        attendanceLogs.add(new AttendanceLog(BELLO_NICKNAME, today, attendanceTime));
        NicknameRegistry nicknameRegistry = new NicknameRegistry(attendanceLogs.getAllNicknames());
        AttendanceBook attendanceBook = new AttendanceBook(attendanceLogs, nicknameRegistry);

        // when
        EnumMap<AttendanceType, Integer> map = attendanceBook.countAllAttendanceType(BELLO_NICKNAME, today);

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

    @DisplayName("출석 경고를 조회할 수 있다.")
    @Test
    void getAttendanceWarningsTest() {
        // given
        NicknameRegistry nicknameRegistry = new NicknameRegistry(Set.of(BELLO_NICKNAME, NEO_NICKNAME));
        AttendanceLogs attendanceLogs = new AttendanceLogs();
        // 벨로 결석 4회-미팅, 네오 결석 0회-클린
        attendanceLogs.add(new AttendanceLog(BELLO_NICKNAME, LocalDate.of(2024, 12, 2), LocalTime.of(15, 0)));
        attendanceLogs.add(new AttendanceLog(BELLO_NICKNAME, LocalDate.of(2024, 12, 3), LocalTime.of(11, 0)));
        attendanceLogs.add(new AttendanceLog(BELLO_NICKNAME, LocalDate.of(2024, 12, 4), LocalTime.of(11, 0)));
        attendanceLogs.add(new AttendanceLog(BELLO_NICKNAME, LocalDate.of(2024, 12, 5), LocalTime.of(11, 0)));
        attendanceLogs.add(new AttendanceLog(NEO_NICKNAME, LocalDate.of(2024, 12, 2), LocalTime.of(13, 0)));
        attendanceLogs.add(new AttendanceLog(NEO_NICKNAME, LocalDate.of(2024, 12, 3), LocalTime.of(10, 0)));
        attendanceLogs.add(new AttendanceLog(NEO_NICKNAME, LocalDate.of(2024, 12, 4), LocalTime.of(10, 0)));
        attendanceLogs.add(new AttendanceLog(NEO_NICKNAME, LocalDate.of(2024, 12, 5), LocalTime.of(10, 0)));
        AttendanceBook attendanceBook = new AttendanceBook(attendanceLogs, nicknameRegistry);

        // when & then
        assertThat(attendanceBook.getAttendanceWarnings(LocalDate.of(2024, 12, 6)))
                .containsExactlyInAnyOrder(
                        new AttendanceWarning(BELLO_NICKNAME, 0, 4)
                );
    }

    @DisplayName("출석 경고를 조회할 때 경고 레벨이 높은 순으로 정렬된다.")
    @Test
    void getAttendanceWarningsSortTest_WhenMultipleWarnings() {
        // given
        NicknameRegistry nicknameRegistry = new NicknameRegistry(Set.of(BELLO_NICKNAME, NEO_NICKNAME));
        AttendanceLogs attendanceLogs = new AttendanceLogs();
        // 네오 결석 2회-경고, 벨로 결석 4회-미팅
        attendanceLogs.add(new AttendanceLog(NEO_NICKNAME, LocalDate.of(2024, 12, 2), LocalTime.of(15, 0)));
        attendanceLogs.add(new AttendanceLog(NEO_NICKNAME, LocalDate.of(2024, 12, 3), LocalTime.of(11, 0)));
        attendanceLogs.add(new AttendanceLog(NEO_NICKNAME, LocalDate.of(2024, 12, 4), LocalTime.of(10, 0)));
        attendanceLogs.add(new AttendanceLog(NEO_NICKNAME, LocalDate.of(2024, 12, 5), LocalTime.of(10, 0)));
        attendanceLogs.add(new AttendanceLog(BELLO_NICKNAME, LocalDate.of(2024, 12, 2), LocalTime.of(15, 0)));
        attendanceLogs.add(new AttendanceLog(BELLO_NICKNAME, LocalDate.of(2024, 12, 3), LocalTime.of(11, 0)));
        attendanceLogs.add(new AttendanceLog(BELLO_NICKNAME, LocalDate.of(2024, 12, 4), LocalTime.of(11, 0)));
        attendanceLogs.add(new AttendanceLog(BELLO_NICKNAME, LocalDate.of(2024, 12, 5), LocalTime.of(11, 0)));
        AttendanceBook attendanceBook = new AttendanceBook(attendanceLogs, nicknameRegistry);

        // when & then
        assertThat(attendanceBook.getAttendanceWarnings(LocalDate.of(2024, 12, 6)))
                .containsExactlyInAnyOrder(
                        new AttendanceWarning(BELLO_NICKNAME, 0, 4),
                        new AttendanceWarning(NEO_NICKNAME, 0, 2)
                );
    }

    @DisplayName("출석 경고를 조회할 때 출석 상태가 같다면, 닉네임 순으로 정렬된다.")
    @Test
    void getAttendanceWarningsSortTest_WhenSameAttendanceStatus() {
        // given
        NicknameRegistry nicknameRegistry = new NicknameRegistry(Set.of(BELLO_NICKNAME, NEO_NICKNAME));
        AttendanceLogs attendanceLogs = new AttendanceLogs();
        // 네오 결석 2회-미팅, 벨로 결석 2회-미팅
        attendanceLogs.add(new AttendanceLog(NEO_NICKNAME, LocalDate.of(2024, 12, 2), LocalTime.of(15, 0)));
        attendanceLogs.add(new AttendanceLog(NEO_NICKNAME, LocalDate.of(2024, 12, 3), LocalTime.of(11, 0)));
        attendanceLogs.add(new AttendanceLog(BELLO_NICKNAME, LocalDate.of(2024, 12, 2), LocalTime.of(15, 0)));
        attendanceLogs.add(new AttendanceLog(BELLO_NICKNAME, LocalDate.of(2024, 12, 3), LocalTime.of(11, 0)));
        AttendanceBook attendanceBook = new AttendanceBook(attendanceLogs, nicknameRegistry);

        // when & then
        assertThat(attendanceBook.getAttendanceWarnings(LocalDate.of(2024, 12, 4)))
                .containsExactlyInAnyOrder(
                        new AttendanceWarning(BELLO_NICKNAME, 0, 2),
                        new AttendanceWarning(NEO_NICKNAME, 0, 2)
                );
    }
}
