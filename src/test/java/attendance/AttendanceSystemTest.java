package attendance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import attendance.domain.AttendanceSystem;
import attendance.domain.checker.AttendanceChecker;
import attendance.domain.checker.AttendanceType;
import attendance.domain.checker.HolidayChecker;
import attendance.domain.crew.CrewStorage;
import attendance.domain.record.AttendanceRecord;
import attendance.exception.ExceptionMessage;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

class AttendanceSystemTest {

    static final String VALID_CREW_NICKNAME = "쿠키";
    static final String INVALID_CREW_NICKNAME = "빙봉";
    static final LocalDateTime SATURDAY = LocalDateTime.of(2025, 2, 8, 8, 50);
    static final LocalDateTime SUNDAY = LocalDateTime.of(2025, 2, 9, 8, 50);
    static final LocalDateTime PUBLIC_HOLIDAY = LocalDateTime.of(2025, 2, 24, 8, 50);

    AttendanceSystem attendanceSystem;
    CrewStorage crewStorage;
    AttendanceChecker attendanceChecker;
    HolidayChecker holidayChecker;

    @BeforeEach
    void beforeEach() {
        crewStorage = new CrewStorage();
        crewStorage.add(VALID_CREW_NICKNAME);
        holidayChecker = new HolidayChecker();
        holidayChecker.addPublicHoliday(PUBLIC_HOLIDAY.toLocalDate());
        attendanceChecker = new AttendanceChecker(holidayChecker);
        attendanceSystem = new AttendanceSystem(crewStorage, attendanceChecker);
    }

    @DisplayName("닉네임과 출석 시간으로 출석 기록을 추가할 수 있다")
    @Test
    void 닉네임과_출석_시간으로_출석_기록을_추가할_수_있다() {
        String crewNickname = VALID_CREW_NICKNAME;
        LocalDateTime arrivalDateTime = LocalDateTime.of(2025, 2, 4, 8, 50, 0);

        attendanceSystem.addAttendanceRecord(crewNickname, arrivalDateTime);

        AttendanceRecord expectedRecord = new AttendanceRecord(crewNickname, arrivalDateTime,
                AttendanceType.ATTENDANCE);
        AttendanceRecord actualRecord = attendanceSystem.findAttendanceRecord(crewNickname,
                arrivalDateTime.toLocalDate()).get();
        assertThat(actualRecord).isEqualTo(expectedRecord);
    }

    @DisplayName("교육시간과 출석정책을 기준으로 월요일의 출석 상태를 결정한다")
    @ParameterizedTest
    @MethodSource()
    void 교육시간과_출석정책을_기준으로_월요일의_출석_상태를_결정한다(LocalDateTime arrivalDateTime, AttendanceType attendanceType) {
        String crewNickname = VALID_CREW_NICKNAME;
        attendanceSystem.addAttendanceRecord(crewNickname, arrivalDateTime);

        AttendanceRecord actualRecord = attendanceSystem.findAttendanceRecord(
                crewNickname, arrivalDateTime.toLocalDate()).get();
        assertThat(actualRecord.getAttendanceType()).isEqualTo(attendanceType);
    }

    static Stream<Arguments> 교육시간과_출석정책을_기준으로_월요일의_출석_상태를_결정한다() {
        return Stream.of(
                Arguments.of(LocalDateTime.of(2025, 2, 3, 12, 0, 0), AttendanceType.ATTENDANCE),
                Arguments.of(LocalDateTime.of(2025, 2, 3, 13, 4, 59), AttendanceType.ATTENDANCE),
                Arguments.of(LocalDateTime.of(2025, 2, 3, 13, 5, 0), AttendanceType.LATE),
                Arguments.of(LocalDateTime.of(2025, 2, 3, 13, 5, 1), AttendanceType.LATE),
                Arguments.of(LocalDateTime.of(2025, 2, 3, 13, 29, 59), AttendanceType.LATE),
                Arguments.of(LocalDateTime.of(2025, 2, 3, 13, 30, 0), AttendanceType.ABSENCE),
                Arguments.of(LocalDateTime.of(2025, 2, 3, 13, 30, 1), AttendanceType.ABSENCE)
        );
    }

    @DisplayName("교육시간과 출석정책을 기준으로 화요일에서 금요일의 출석 상태를 결정한다")
    @ParameterizedTest
    @MethodSource()
    void 교육시간과_출석정책을_기준으로_화요일에서_금요일의_출석_상태를_결정한다(LocalDateTime arrivalDateTime, AttendanceType attendanceType) {
        String crewNickname = VALID_CREW_NICKNAME;
        attendanceSystem.addAttendanceRecord(crewNickname, arrivalDateTime);

        AttendanceRecord actualRecord = attendanceSystem.findAttendanceRecord(
                crewNickname, arrivalDateTime.toLocalDate()).get();
        assertThat(actualRecord.getAttendanceType()).isEqualTo(attendanceType);
    }

    static Stream<Arguments> 교육시간과_출석정책을_기준으로_화요일에서_금요일의_출석_상태를_결정한다() {
        return Stream.of(
                Arguments.of(LocalDateTime.of(2025, 2, 7, 9, 0, 0), AttendanceType.ATTENDANCE),
                Arguments.of(LocalDateTime.of(2025, 2, 7, 10, 4, 59), AttendanceType.ATTENDANCE),
                Arguments.of(LocalDateTime.of(2025, 2, 7, 10, 5, 0), AttendanceType.LATE),
                Arguments.of(LocalDateTime.of(2025, 2, 7, 10, 5, 1), AttendanceType.LATE),
                Arguments.of(LocalDateTime.of(2025, 2, 7, 10, 29, 59), AttendanceType.LATE),
                Arguments.of(LocalDateTime.of(2025, 2, 7, 10, 30, 0), AttendanceType.ABSENCE),
                Arguments.of(LocalDateTime.of(2025, 2, 7, 10, 30, 1), AttendanceType.ABSENCE)
        );
    }

    @DisplayName("이미 출석한 경우, 다시 출석할 수 없으며 수정 기능을 이용하도록 안내한다")
    @Test
    void 이미_출석한_경우_다시_출석할_수_없으며_수정_기능을_이용하도록_안내한다() {
        String crewNickname = VALID_CREW_NICKNAME;
        LocalDate arrivalDate = LocalDate.of(2025, 2, 4);
        LocalDateTime arrivalDateTime = LocalDateTime.of(arrivalDate, LocalTime.of(8, 50, 0));
        attendanceSystem.addAttendanceRecord(crewNickname, arrivalDateTime);

        assertThatIllegalArgumentException()
                .isThrownBy(() -> attendanceSystem.addAttendanceRecord(crewNickname, arrivalDateTime))
                .withMessage(ExceptionMessage.ALREADY_ATTENDANCE.getMessage());
    }

    @DisplayName("네임이 등록되지 않은 경우 예외 메세지를 출력한다")
    @Test
    void 네임이_등록되지_않은_경우_예외_메세지를_출력한다() {
        String crewNickname = INVALID_CREW_NICKNAME;
        LocalDateTime arrivalDateTime = LocalDateTime.of(2025, 2, 4, 8, 50, 0);

        assertThatIllegalArgumentException()
                .isThrownBy(() -> attendanceSystem.addAttendanceRecord(crewNickname, arrivalDateTime))
                .withMessage(ExceptionMessage.INVALID_CREW.getMessage());
    }

    @DisplayName("등교일이 아닌 경우 예외 메세지를 출력한다")
    @ParameterizedTest
    @MethodSource()
    void 등교일이_아닌_경우_예외_메세지를_출력한다(LocalDateTime arrivalDateTime) {
        String crewNickname = VALID_CREW_NICKNAME;

        String exceptionMessage = String.format(ExceptionMessage.HOLIDAY_ATTENDANCE.getMessage(),
                arrivalDateTime.getMonth().getValue(), arrivalDateTime.getDayOfMonth(),
                arrivalDateTime.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.KOREA));

        assertThatIllegalArgumentException()
                .isThrownBy(() -> attendanceSystem.addAttendanceRecord(crewNickname, arrivalDateTime))
                .withMessage(exceptionMessage);
    }

    static Stream<Arguments> 등교일이_아닌_경우_예외_메세지를_출력한다() {
        return Stream.of(
                Arguments.of(SATURDAY),
                Arguments.of(SUNDAY),
                Arguments.of(PUBLIC_HOLIDAY)
        );
    }

    @DisplayName("캠퍼스 운영 시간이 아닌 경우 예외 메세지를 출력한다")
    @ParameterizedTest
    @ValueSource(strings = {"07:59:59", "23:00:00"})
    void 캠퍼스_운영_시간이_아닌_경우_예외_메세지를_출력한다(LocalTime arrivalTime) {
        LocalDateTime arrivalDateTime = LocalDateTime.of(LocalDate.of(2025, 2, 10), arrivalTime);

        assertThatIllegalArgumentException()
                .isThrownBy(() -> attendanceSystem.addAttendanceRecord(VALID_CREW_NICKNAME, arrivalDateTime))
                .withMessage(ExceptionMessage.OUT_OF_CAMPUS_TIME.getMessage());
    }

    @DisplayName("캠퍼스 운영 시간인 경우 예외 메세지를 발생시키지 않는다")
    @ParameterizedTest
    @ValueSource(strings = {"08:00", "22:59"})
    void 캠퍼스_운영_시간인_경우_예외_메세지를_발생시키지_않는다(LocalTime arrivalTime) {
        LocalDateTime arrivalDateTime = LocalDateTime.of(LocalDate.of(2025, 2, 10), arrivalTime);

        assertThatCode(() -> attendanceSystem.addAttendanceRecord(VALID_CREW_NICKNAME, arrivalDateTime))
                .doesNotThrowAnyException();
    }

}