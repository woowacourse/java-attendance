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
import java.util.Comparator;
import java.util.List;
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
    static final LocalDateTime COMMON_ATTENDANCE_DATE_TIME = LocalDateTime.of(2025, 2, 4, 8, 50);

    CrewStorage crewStorage = new CrewStorage();
    HolidayChecker holidayChecker = new HolidayChecker();
    AttendanceChecker attendanceChecker = new AttendanceChecker(holidayChecker);
    AttendanceSystem attendanceSystem = new AttendanceSystem(crewStorage, attendanceChecker);

    @BeforeEach
    void beforeEach() {
        crewStorage.add(VALID_CREW_NICKNAME);
        holidayChecker.addPublicHoliday(PUBLIC_HOLIDAY.toLocalDate());
        attendanceSystem = new AttendanceSystem(crewStorage, attendanceChecker);
    }

    @DisplayName("출석 확인 - 닉네임과 출석 시간으로 출석 기록을 추가할 수 있다")
    @Test
    void 출석_확인_닉네임과_출석_시간으로_출석_기록을_추가할_수_있다() {
        String crewNickname = VALID_CREW_NICKNAME;
        LocalDateTime arrivalDateTime = LocalDateTime.of(2025, 2, 4, 8, 50, 0);

        attendanceSystem.addAttendanceRecord(crewNickname, arrivalDateTime);

        AttendanceRecord expectedRecord = new AttendanceRecord(crewNickname, arrivalDateTime,
                AttendanceType.ATTENDANCE);
        AttendanceRecord actualRecord = attendanceSystem.findAttendanceRecord(crewNickname,
                arrivalDateTime.toLocalDate()).get();
        assertThat(actualRecord).isEqualTo(expectedRecord);
    }

    @DisplayName("출석 확인 - 교육시간과 출석정책을 기준으로 월요일의 출석 상태를 결정한다")
    @ParameterizedTest
    @MethodSource()
    void 출석_확인_교육시간과_출석정책을_기준으로_월요일의_출석_상태를_결정한다(LocalDateTime arrivalDateTime, AttendanceType attendanceType) {
        String crewNickname = VALID_CREW_NICKNAME;
        attendanceSystem.addAttendanceRecord(crewNickname, arrivalDateTime);

        AttendanceRecord actualRecord = attendanceSystem.findAttendanceRecord(
                crewNickname, arrivalDateTime.toLocalDate()).get();
        assertThat(actualRecord.getAttendanceType()).isEqualTo(attendanceType);
    }

    static Stream<Arguments> 출석_확인_교육시간과_출석정책을_기준으로_월요일의_출석_상태를_결정한다() {
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

    @DisplayName("출석 확인 - 교육시간과 출석정책을 기준으로 화요일에서 금요일의 출석 상태를 결정한다")
    @ParameterizedTest
    @MethodSource()
    void 출석_확인_교육시간과_출석정책을_기준으로_화요일에서_금요일의_출석_상태를_결정한다(LocalDateTime arrivalDateTime, AttendanceType attendanceType) {
        String crewNickname = VALID_CREW_NICKNAME;
        attendanceSystem.addAttendanceRecord(crewNickname, arrivalDateTime);

        AttendanceRecord actualRecord = attendanceSystem.findAttendanceRecord(
                crewNickname, arrivalDateTime.toLocalDate()).get();
        assertThat(actualRecord.getAttendanceType()).isEqualTo(attendanceType);
    }

    static Stream<Arguments> 출석_확인_교육시간과_출석정책을_기준으로_화요일에서_금요일의_출석_상태를_결정한다() {
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

    @DisplayName("출석 확인 - 이미 출석한 경우, 다시 출석할 수 없으며 수정 기능을 이용하도록 안내한다")
    @Test
    void 출석_확인_이미_출석한_경우_다시_출석할_수_없으며_수정_기능을_이용하도록_안내한다() {
        String crewNickname = VALID_CREW_NICKNAME;
        LocalDate arrivalDate = LocalDate.of(2025, 2, 4);
        LocalDateTime arrivalDateTime = LocalDateTime.of(arrivalDate, LocalTime.of(8, 50, 0));
        attendanceSystem.addAttendanceRecord(crewNickname, arrivalDateTime);

        assertThatIllegalArgumentException()
                .isThrownBy(() -> attendanceSystem.addAttendanceRecord(crewNickname, arrivalDateTime))
                .withMessage(ExceptionMessage.ALREADY_ATTENDANCE.getMessage());
    }

    @DisplayName("출석 확인 - 네임이 등록되지 않은 경우 예외 메세지를 출력한다")
    @Test
    void 출석_확인_네임이_등록되지_않은_경우_예외_메세지를_출력한다() {
        String crewNickname = INVALID_CREW_NICKNAME;
        LocalDateTime arrivalDateTime = LocalDateTime.of(2025, 2, 4, 8, 50, 0);

        assertThatIllegalArgumentException()
                .isThrownBy(() -> attendanceSystem.addAttendanceRecord(crewNickname, arrivalDateTime))
                .withMessage(ExceptionMessage.INVALID_CREW.getMessage());
    }

    @DisplayName("출석 확인 - 등교일이 아닌 경우 예외 메세지를 출력한다")
    @ParameterizedTest
    @MethodSource()
    void 출석_확인_등교일이_아닌_경우_예외_메세지를_출력한다(LocalDateTime arrivalDateTime) {
        String crewNickname = VALID_CREW_NICKNAME;

        assertThatIllegalArgumentException()
                .isThrownBy(() -> attendanceSystem.addAttendanceRecord(crewNickname, arrivalDateTime))
                .withMessage(makeHolidayAttendanceExceptionMessage(arrivalDateTime));
    }

    static Stream<Arguments> 출석_확인_등교일이_아닌_경우_예외_메세지를_출력한다() {
        return Stream.of(
                Arguments.of(SATURDAY),
                Arguments.of(SUNDAY),
                Arguments.of(PUBLIC_HOLIDAY)
        );
    }

    @DisplayName("출석 확인 - 캠퍼스 운영 시간이 아닌 경우 예외 메세지를 출력한다")
    @ParameterizedTest
    @ValueSource(strings = {"07:59:59", "23:00:00"})
    void 출석_확인_캠퍼스_운영_시간이_아닌_경우_예외_메세지를_출력한다(LocalTime arrivalTime) {
        LocalDateTime arrivalDateTime = LocalDateTime.of(LocalDate.of(2025, 2, 10), arrivalTime);

        assertThatIllegalArgumentException()
                .isThrownBy(() -> attendanceSystem.addAttendanceRecord(VALID_CREW_NICKNAME, arrivalDateTime))
                .withMessage(ExceptionMessage.OUT_OF_CAMPUS_TIME.getMessage());
    }

    @DisplayName("출석 확인 - 캠퍼스 운영 시간인 경우 예외 메세지를 발생시키지 않는다")
    @ParameterizedTest
    @ValueSource(strings = {"08:00", "22:59"})
    void 출석_확인_캠퍼스_운영_시간인_경우_예외_메세지를_발생시키지_않는다(LocalTime arrivalTime) {
        LocalDateTime arrivalDateTime = LocalDateTime.of(LocalDate.of(2025, 2, 10), arrivalTime);

        assertThatCode(() -> attendanceSystem.addAttendanceRecord(VALID_CREW_NICKNAME, arrivalDateTime))
                .doesNotThrowAnyException();
    }

    @DisplayName("출석 기록 수정 - 닉네임, 수정 목표 날짜, 새로운 출석 시간으로 기존 출석 기록을 수정할 수 있다")
    @Test
    void 출석_기록_수정_닉네임_수정_목표_날짜_새로운_출석_시간으로_기존_출석_기록을_수정할_수_있다() {
        attendanceSystem.addAttendanceRecord(VALID_CREW_NICKNAME, COMMON_ATTENDANCE_DATE_TIME);
        LocalDateTime newDateTime = LocalDateTime.of(
                COMMON_ATTENDANCE_DATE_TIME.toLocalDate(), LocalTime.of(10, 5));

        attendanceSystem.updateAttendance(
                VALID_CREW_NICKNAME, newDateTime.toLocalDate(), newDateTime.toLocalTime());

        AttendanceRecord actualRecord = attendanceSystem.findAttendanceRecord(
                VALID_CREW_NICKNAME, COMMON_ATTENDANCE_DATE_TIME.toLocalDate()).get();
        checkSameRecord(actualRecord, VALID_CREW_NICKNAME, newDateTime);
    }

    @DisplayName("출석 기록 수정 - 교육시간과 출석정책을 기준으로 출석 상태를 결정한다")
    @ParameterizedTest
    @MethodSource()
    void 출석_기록_수정_교육시간과_출석정책을_기준으로_출석_상태를_결정한다(LocalTime time, AttendanceType expectedType) {
        attendanceSystem.addAttendanceRecord(VALID_CREW_NICKNAME, COMMON_ATTENDANCE_DATE_TIME);
        LocalDateTime newDateTime = LocalDateTime.of(
                COMMON_ATTENDANCE_DATE_TIME.toLocalDate(), time);

        attendanceSystem.updateAttendance(
                VALID_CREW_NICKNAME, newDateTime.toLocalDate(), newDateTime.toLocalTime());

        AttendanceRecord actualRecord = attendanceSystem.findAttendanceRecord(
                VALID_CREW_NICKNAME, COMMON_ATTENDANCE_DATE_TIME.toLocalDate()).get();
        assertThat(actualRecord.getAttendanceType()).isEqualTo(expectedType);
    }

    static Stream<Arguments> 출석_기록_수정_교육시간과_출석정책을_기준으로_출석_상태를_결정한다() {
        return Stream.of(
                Arguments.of(LocalTime.of(9, 59, 59), AttendanceType.ATTENDANCE),
                Arguments.of(LocalTime.of(10, 4, 59), AttendanceType.ATTENDANCE),
                Arguments.of(LocalTime.of(10, 5, 0), AttendanceType.LATE),
                Arguments.of(LocalTime.of(10, 29, 59), AttendanceType.LATE),
                Arguments.of(LocalTime.of(10, 30, 0), AttendanceType.ABSENCE),
                Arguments.of(LocalTime.of(10, 30, 1), AttendanceType.ABSENCE)
        );
    }

    @DisplayName("출석 기록 수정 - 닉네임이 등록되지 않은 경우 예외 메세지를 출력한다")
    @Test
    void 출석_기록_수정_닉네임이_등록되지_않은_경우_예외_메세지를_출력한다() {
        LocalDateTime newDateTime = LocalDateTime.of(
                COMMON_ATTENDANCE_DATE_TIME.toLocalDate(), LocalTime.of(8, 50));

        assertThatIllegalArgumentException()
                .isThrownBy(() -> attendanceSystem.updateAttendance(
                        INVALID_CREW_NICKNAME,
                        newDateTime.toLocalDate(),
                        newDateTime.toLocalTime()))
                .withMessage(ExceptionMessage.INVALID_CREW.getMessage());
    }

    @DisplayName("출석 기록 수정 - 등교일이 아닌 경우 예외 메세지를 출력한다")
    @ParameterizedTest
    @MethodSource()
    void 출석_기록_수정_등교일이_아닌_경우_예외_메세지를_출력한다(LocalDateTime holiday) {
        LocalDateTime newDateTime = LocalDateTime.of(
                holiday.toLocalDate(), LocalTime.of(8, 50));

        assertThatIllegalArgumentException()
                .isThrownBy(() -> attendanceSystem.updateAttendance(
                        VALID_CREW_NICKNAME,
                        newDateTime.toLocalDate(),
                        newDateTime.toLocalTime()))
                .withMessage(makeHolidayAttendanceExceptionMessage(newDateTime));
    }

    static Stream<Arguments> 출석_기록_수정_등교일이_아닌_경우_예외_메세지를_출력한다() {
        return Stream.of(
                Arguments.of(SATURDAY),
                Arguments.of(SUNDAY),
                Arguments.of(PUBLIC_HOLIDAY)
        );
    }

    @DisplayName("출석 기록 수정 - 캠퍼스 운영 시간이 아닌 경우 예외 메세지를 출력한다")
    @ParameterizedTest
    @ValueSource(strings = {"07:59:59", "23:00:00"})
    void 출석_기록_수정_캠퍼스_운영_시간이_아닌_경우_예외_메세지를_출력한다(LocalTime time) {
        LocalDateTime newDateTime = LocalDateTime.of(
                COMMON_ATTENDANCE_DATE_TIME.toLocalDate(), time);

        assertThatIllegalArgumentException()
                .isThrownBy(() -> attendanceSystem.updateAttendance(
                        VALID_CREW_NICKNAME,
                        newDateTime.toLocalDate(),
                        newDateTime.toLocalTime()))
                .withMessage(ExceptionMessage.OUT_OF_CAMPUS_TIME.getMessage());
    }

    @DisplayName("출석 조회 - 닉네임을 통해 해당 크루의 출석 기록 일자순으로 조회할 수 있다")
    @Test
    void 출석_조회_닉네임을_통해_해당_크루의_출석_기록_일자순으로_조회할_수_있다() {
        attendanceSystem.addAttendanceRecord(VALID_CREW_NICKNAME, LocalDateTime.of(2025, 2, 19, 8, 50));
        attendanceSystem.addAttendanceRecord(VALID_CREW_NICKNAME, LocalDateTime.of(2025, 2, 18, 8, 50));
        attendanceSystem.addAttendanceRecord(VALID_CREW_NICKNAME, LocalDateTime.of(2025, 2, 17, 8, 50));
        LocalDate today = LocalDate.of(2025, 2, 21);
        List<AttendanceRecord> records = attendanceSystem.findRecordsInMonth(VALID_CREW_NICKNAME, today);

        assertThat(records).hasSize(15);
        assertThat(records).isSortedAccordingTo(Comparator.comparing(AttendanceRecord::getArrivalDateTime));
        checkAttendanceTypeCount(records, AttendanceType.ATTENDANCE, 3);
        checkAttendanceTypeCount(records, AttendanceType.ABSENCE, 12);
    }

    String makeHolidayAttendanceExceptionMessage(LocalDateTime dateTime) {
        return String.format(ExceptionMessage.HOLIDAY_ATTENDANCE.getMessage(),
                dateTime.getMonth().getValue(), dateTime.getDayOfMonth(),
                dateTime.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.KOREA));
    }

    void checkSameRecord(
            AttendanceRecord target,
            String expectedNickname,
            LocalDateTime expectedDateTime
    ) {
        assertThat(target.getNickname()).isEqualTo(expectedNickname);
        assertThat(target.getArrivalDateTime()).isEqualTo(expectedDateTime);
    }

    void checkAttendanceTypeCount(List<AttendanceRecord> targetRecords, AttendanceType targetType, int expectedCount) {
        assertThat(targetRecords).extracting(AttendanceRecord::getAttendanceType)
                .filteredOn(type -> type == targetType).size()
                .isEqualTo(expectedCount);
    }
}