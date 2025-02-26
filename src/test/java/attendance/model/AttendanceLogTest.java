package attendance.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.JavaTimeConversionPattern;
import org.junit.jupiter.params.provider.CsvSource;

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

    @DisplayName("닉네임과 등교 날짜가 같은 경우 같은 출석 로그로 간주한다.")
    @Test
    void shouldEquals_WhenNicknameAndAttendanceDateSame() {
        // given
        Nickname nickname = new Nickname("벨로");
        LocalDate attendanceDate = LocalDate.of(2024, 12, 2);
        LocalTime attendanceTime = LocalTime.of(10, 0);
        AttendanceLog beforeAttendanceLog = new AttendanceLog(nickname, attendanceDate, attendanceTime);
        AttendanceLog afterAttendanceLog = new AttendanceLog(nickname, attendanceDate, attendanceTime);

        // when & then
        assertThat(beforeAttendanceLog.equals(afterAttendanceLog))
                .isTrue();
    }

    @DisplayName("출석 로그에서 닉네임을 확인할 수 있다.")
    @Test
    void getNicknameTest() {
        // given
        Nickname belloNickname = new Nickname("벨로");
        LocalDate attendanceDate = LocalDate.of(2024, 12, 2);
        LocalTime attendanceTime = LocalTime.of(10, 0);
        AttendanceLog attendanceLog = new AttendanceLog(belloNickname, attendanceDate, attendanceTime);

        // when
        Nickname nickname = attendanceLog.getNickname();

        // then
        assertThat(nickname)
                .isEqualTo(belloNickname);
    }

    @DisplayName("닉네임이 null인 경우 예외가 발생한다.")
    @Test
    void shouldThrowException_WhenNicknameIsNull() {
        // given
        LocalDate attendanceDate = LocalDate.of(2024, 12, 2);
        LocalTime attendanceTime = LocalTime.of(10, 0);

        // when & then
        assertThatCode(() -> new AttendanceLog(null, attendanceDate, attendanceTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("닉네임은 null일 수 없습니다.");
    }

    @DisplayName("등교 날짜가 법정 공휴일인 경우 예외가 발생한다.")
    @Test
    void shouldThrowException_WhenAttendanceDateIsPublicHoliday() {
        // given
        Nickname nickname = new Nickname("벨로");
        LocalDate christmas = LocalDate.of(2024, 12, 25);

        // when & then
        assertThatCode(() -> new AttendanceLog(nickname, christmas))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("공휴일에는 출석할 수 없습니다.");
    }

    @DisplayName("등교 날짜가 주말인 경우 예외가 발생한다.")
    @ParameterizedTest
    @CsvSource({
            "2024-12-07", // 토요일
            "2024-12-08"  // 일요일
    })
    void shouldThrowException_WhenAttendanceDateIsWeekend(@JavaTimeConversionPattern("yyyy-MM-dd") LocalDate weekend) {
        // given
        Nickname nickname = new Nickname("벨로");

        // when & then
        assertThatCode(() -> new AttendanceLog(nickname, weekend))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주말에는 출석할 수 없습니다.");
    }

    @DisplayName("등교 날짜가 null인 경우 예외가 발생한다.")
    @Test
    void shouldThrowException_WhenAttendanceDateIsNull() {
        // given
        Nickname nickname = new Nickname("벨로");
        LocalTime attendanceTime = LocalTime.of(10, 0);

        // when & then
        assertThatCode(() -> new AttendanceLog(nickname, null, attendanceTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("출석 날짜는 null일 수 없습니다.");
    }

    @DisplayName("등교 날짜를 조회할 수 있다.")
    @Test
    void getAttendanceDateTest() {
        // given
        Nickname nickname = new Nickname("벨로");
        LocalDate attendanceDate = LocalDate.of(2024, 12, 2);
        LocalTime attendanceTime = LocalTime.of(10, 0);
        AttendanceLog attendanceLog = new AttendanceLog(nickname, attendanceDate, attendanceTime);

        // when
        LocalDate logAttendanceDate = attendanceLog.getAttendanceDate();

        // then
        assertThat(logAttendanceDate)
                .isEqualTo(attendanceDate);
    }

    @DisplayName("등교 시간이 없는 경우 결석으로 간주한다.")
    @Test
    void shouldAbsent_WhenAttendanceTimeIsNull() {
        // given
        Nickname nickname = new Nickname("벨로");
        LocalDate attendanceDate = LocalDate.of(2024, 12, 2);

        // when
        AttendanceLog attendanceLog = new AttendanceLog(nickname, attendanceDate);
        boolean isAbsent = attendanceLog.isNotRecorded();

        // then
        assertThat(isAbsent)
                .isTrue();
    }

    @DisplayName("등교 시간이 있는 경우 결석으로 간주하지 않는다.")
    @Test
    void shouldAbsent_WhenAttendanceTimeExist() {
        // given
        Nickname nickname = new Nickname("벨로");
        LocalDate attendanceDate = LocalDate.of(2024, 12, 2);
        LocalTime attendanceTime = LocalTime.of(10, 0);

        // when
        AttendanceLog attendanceLog = new AttendanceLog(nickname, attendanceDate, attendanceTime);
        boolean isAbsent = attendanceLog.isNotRecorded();

        // then
        assertThat(isAbsent)
                .isFalse();
    }

    @DisplayName("등교 시간이 있는 경우 조회할 수 있다.")
    @Test
    void getAttendanceTimeTest() {
        // given
        Nickname nickname = new Nickname("벨로");
        LocalDate attendanceDate = LocalDate.of(2024, 12, 2);
        LocalTime attendanceTime = LocalTime.of(10, 0);

        // when
        AttendanceLog attendanceLog = new AttendanceLog(nickname, attendanceDate, attendanceTime);

        // then
        assertThat(attendanceLog.getAttendanceTime())
                .isEqualTo(attendanceTime);
    }

    @DisplayName("등교 시간이 있는 경우 날짜+시간을 조회할 수 있다.")
    @Test
    void getAttendanceDateTimeTest() {
        // given
        Nickname nickname = new Nickname("벨로");
        LocalDate attendanceDate = LocalDate.of(2024, 12, 2);
        LocalTime attendanceTime = LocalTime.of(10, 0);

        // when
        AttendanceLog attendanceLog = new AttendanceLog(nickname, attendanceDate, attendanceTime);

        // then
        assertThat(attendanceLog.getAttendanceDateTime())
                .isEqualTo(LocalDateTime.of(attendanceDate, attendanceTime));
    }

    @DisplayName("캠퍼스 운영시간이 아닐 때 출석하는 경우 예외가 발생한다.")
    @ParameterizedTest
    @CsvSource({
            "07:59",
            "23:01",
    })
    void shouldThrowException_WhenAttendanceTimeNotInOpen(@JavaTimeConversionPattern("HH:mm") LocalTime outTime) {
        // given
        Nickname nickname = new Nickname("벨로");
        LocalDate attendanceDate = LocalDate.of(2024, 12, 2);

        // when & then
        assertThatCode(() -> new AttendanceLog(nickname, attendanceDate, outTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("캠퍼스 운영시간(08:00~23:00) 외에는 출석할 수 없습니다.");
    }

    @DisplayName("출석 로그를 닉네임과 입력한 달이 일치하는지 확인할 수 있다.")
    @ParameterizedTest
    @CsvSource({
            "벨로, 2024-12-02, true",
            "벨로, 2024-11-31, false",
            "네오, 2024-12-02, false",
            "네오, 2024-11-31, false"
    })
    void attendanceLogSameNicknameAndMonthTest(String comparedNickname,
                                               @JavaTimeConversionPattern("yyyy-MM-dd") LocalDate comparedDate,
                                               boolean expected) {
        // given
        Nickname belloNickname = new Nickname("벨로");
        LocalDate attendanceDate = LocalDate.of(2024, 12, 2);
        AttendanceLog attendanceLog = new AttendanceLog(belloNickname, attendanceDate);

        // when
        boolean isSame = attendanceLog.isSameNicknameAndMonth(new Nickname(comparedNickname), comparedDate);

        // then
        assertThat(isSame)
                .isEqualTo(expected);
    }

    @DisplayName("출석 로그를 닉네임과 입력한 날짜가 일치하는지 확인할 수 있다.")
    @ParameterizedTest
    @CsvSource({
            "벨로, 2024-12-02, true",
            "벨로, 2024-12-31, false",
            "네오, 2024-12-02, false",
            "네오, 2024-12-31, false"
    })
    void attendanceLogSameNicknameAndDateTest(String comparedNickname,
                                              @JavaTimeConversionPattern("yyyy-MM-dd") LocalDate comparedDate,
                                              boolean expected) {
        // given
        Nickname belloNickname = new Nickname("벨로");
        LocalDate attendanceDate = LocalDate.of(2024, 12, 2);
        AttendanceLog attendanceLog = new AttendanceLog(belloNickname, attendanceDate);

        // when
        boolean isSame = attendanceLog.isSameNicknameAndDate(new Nickname(comparedNickname), comparedDate);

        // then
        assertThat(isSame)
                .isEqualTo(expected);
    }
}
