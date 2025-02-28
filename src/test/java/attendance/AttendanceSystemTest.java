package attendance;

import static attendance.fixture.CampusTimeFixture.CAMPUS_END_TIME;
import static attendance.fixture.CampusTimeFixture.CAMPUS_START_TIME;
import static attendance.fixture.CrewNicknameFixture.INVALID_CREW_NICKNAME;
import static attendance.fixture.CrewNicknameFixture.VALID_CREW_NICKNAME;
import static attendance.fixture.DateFixture.MONDAY;
import static attendance.fixture.DateFixture.NOT_MONDAY;
import static attendance.fixture.DateFixture.PUBLIC_HOLIDAY;
import static attendance.fixture.DateFixture.SATURDAY;
import static attendance.fixture.DateFixture.SUNDAY;
import static attendance.fixture.TimeFixture.MONDAY_ABSENCE_TIME;
import static attendance.fixture.TimeFixture.MONDAY_ATTENDANCE_TIME;
import static attendance.fixture.TimeFixture.MONDAY_LATE_TIME;
import static attendance.fixture.TimeFixture.NOT_MONDAY_ABSENCE_TIME;
import static attendance.fixture.TimeFixture.NOT_MONDAY_ATTENDANCE_TIME;
import static attendance.fixture.TimeFixture.NOT_MONDAY_LATE_TIME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import attendance.domain.AttendanceSystem;
import attendance.domain.checker.AttendanceType;
import attendance.domain.checker.AttendanceTypeChecker;
import attendance.domain.checker.HolidayChecker;
import attendance.domain.crew.CrewStorage;
import attendance.domain.record.AttendanceRecord;
import attendance.domain.record.AttendanceRecordStorage;
import attendance.domain.risk.RiskType;
import attendance.dto.AttendanceState;
import attendance.exception.AttendanceException;
import attendance.exception.ExceptionMessage;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class AttendanceSystemTest {

    CrewStorage crewStorage = new CrewStorage();
    HolidayChecker holidayChecker = new HolidayChecker();
    AttendanceTypeChecker attendanceTypeChecker = new AttendanceTypeChecker(holidayChecker);
    AttendanceRecordStorage recordStorage = new AttendanceRecordStorage();
    AttendanceSystem attendanceSystem = new AttendanceSystem(crewStorage, attendanceTypeChecker, recordStorage);

    @BeforeEach
    void beforeEach() {
        crewStorage.add(VALID_CREW_NICKNAME);
        holidayChecker.addPublicHoliday(PUBLIC_HOLIDAY);
    }

    @DisplayName("출석 확인 - 닉네임과 출석 시간으로 출석 기록을 추가할 수 있다")
    @Test
    void 출석_확인_닉네임과_출석_시간으로_출석_기록을_추가할_수_있다() {
        String nickname = VALID_CREW_NICKNAME;
        LocalDateTime arrivalDateTime = LocalDateTime.of(MONDAY, MONDAY_ATTENDANCE_TIME);

        attendanceSystem.addAttendanceRecord(nickname, arrivalDateTime);

        Optional<AttendanceRecord> record = recordStorage.find(nickname, arrivalDateTime.toLocalDate());
        assertThat(record).isPresent();
    }

    @DisplayName("출석 확인 - 교육시간과 출석정책을 기준으로 월요일의 출석 상태를 결정한다")
    @ParameterizedTest
    @MethodSource()
    void 출석_확인_교육시간과_출석정책을_기준으로_월요일의_출석_상태를_결정한다(LocalDateTime arrivalDateTime, AttendanceType attendanceType) {
        String nickname = VALID_CREW_NICKNAME;

        attendanceSystem.addAttendanceRecord(nickname, arrivalDateTime);

        AttendanceRecord record = recordStorage.findWithAbsenceRecord(nickname, arrivalDateTime.toLocalDate());
        assertThat(record.getAttendanceType()).isEqualTo(attendanceType);
    }

    static Stream<Arguments> 출석_확인_교육시간과_출석정책을_기준으로_월요일의_출석_상태를_결정한다() {
        return Stream.of(
                Arguments.of(LocalDateTime.of(MONDAY, MONDAY_ATTENDANCE_TIME), AttendanceType.ATTENDANCE),
                Arguments.of(LocalDateTime.of(MONDAY, MONDAY_LATE_TIME.minusSeconds(1)), AttendanceType.ATTENDANCE),
                Arguments.of(LocalDateTime.of(MONDAY, MONDAY_LATE_TIME), AttendanceType.LATE),
                Arguments.of(LocalDateTime.of(MONDAY, MONDAY_LATE_TIME.plusSeconds(1)), AttendanceType.LATE),
                Arguments.of(LocalDateTime.of(MONDAY, MONDAY_ABSENCE_TIME.minusSeconds(1)), AttendanceType.LATE),
                Arguments.of(LocalDateTime.of(MONDAY, MONDAY_ABSENCE_TIME), AttendanceType.ABSENCE),
                Arguments.of(LocalDateTime.of(MONDAY, MONDAY_ABSENCE_TIME.plusSeconds(1)), AttendanceType.ABSENCE)
        );
    }

    @DisplayName("출석 확인 - 교육시간과 출석정책을 기준으로 화요일에서 금요일의 출석 상태를 결정한다")
    @ParameterizedTest
    @MethodSource()
    void 출석_확인_교육시간과_출석정책을_기준으로_화요일에서_금요일의_출석_상태를_결정한다(
            LocalDateTime arrivalDateTime,
            AttendanceType attendanceType
    ) {
        String nickname = VALID_CREW_NICKNAME;
        attendanceSystem.addAttendanceRecord(nickname, arrivalDateTime);

        AttendanceRecord record = recordStorage.findWithAbsenceRecord(nickname, arrivalDateTime.toLocalDate());
        assertThat(record.getAttendanceType()).isEqualTo(attendanceType);
    }

    static Stream<Arguments> 출석_확인_교육시간과_출석정책을_기준으로_화요일에서_금요일의_출석_상태를_결정한다() {
        return Stream.of(
                Arguments.of(LocalDateTime.of(NOT_MONDAY, NOT_MONDAY_ATTENDANCE_TIME), AttendanceType.ATTENDANCE),
                Arguments.of(LocalDateTime.of(NOT_MONDAY, NOT_MONDAY_LATE_TIME.minusSeconds(1)),
                        AttendanceType.ATTENDANCE),
                Arguments.of(LocalDateTime.of(NOT_MONDAY, NOT_MONDAY_LATE_TIME), AttendanceType.LATE),
                Arguments.of(LocalDateTime.of(NOT_MONDAY, NOT_MONDAY_LATE_TIME.plusSeconds(1)), AttendanceType.LATE),
                Arguments.of(LocalDateTime.of(NOT_MONDAY, NOT_MONDAY_ABSENCE_TIME.minusSeconds(1)),
                        AttendanceType.LATE),
                Arguments.of(LocalDateTime.of(NOT_MONDAY, NOT_MONDAY_ABSENCE_TIME), AttendanceType.ABSENCE),
                Arguments.of(LocalDateTime.of(NOT_MONDAY, NOT_MONDAY_ABSENCE_TIME.plusSeconds(1)),
                        AttendanceType.ABSENCE)
        );
    }

    @DisplayName("출석 확인 - 이미 출석한 경우, 다시 출석할 수 없으며 수정 기능을 이용하도록 안내한다")
    @Test
    void 출석_확인_이미_출석한_경우_다시_출석할_수_없으며_수정_기능을_이용하도록_안내한다() {
        String nickname = VALID_CREW_NICKNAME;
        LocalDateTime arrivalDateTime = LocalDateTime.of(MONDAY, MONDAY_ATTENDANCE_TIME);
        attendanceSystem.addAttendanceRecord(nickname, arrivalDateTime);

        assertThatThrownBy(() -> attendanceSystem.addAttendanceRecord(nickname, arrivalDateTime))
                .isInstanceOf(AttendanceException.class)
                .hasMessage(ExceptionMessage.ALREADY_ATTENDANCE.getMessage());
    }

    @DisplayName("출석 확인 - 네임이 등록되지 않은 경우 예외 메세지를 출력한다")
    @Test
    void 출석_확인_네임이_등록되지_않은_경우_예외_메세지를_출력한다() {
        String nickname = INVALID_CREW_NICKNAME;
        LocalDateTime arrivalDateTime = LocalDateTime.of(MONDAY, MONDAY_ATTENDANCE_TIME);

        assertThatThrownBy(() -> attendanceSystem.addAttendanceRecord(nickname, arrivalDateTime))
                .isInstanceOf(AttendanceException.class)
                .hasMessage(ExceptionMessage.INVALID_CREW.getMessage());
    }

    @DisplayName("출석 확인 - 등교일이 아닌 경우 예외 메세지를 출력한다")
    @ParameterizedTest
    @MethodSource()
    void 출석_확인_등교일이_아닌_경우_예외_메세지를_출력한다(LocalDateTime arrivalDateTime) {
        String nickname = VALID_CREW_NICKNAME;
        assertThatThrownBy(() -> attendanceSystem.addAttendanceRecord(nickname, arrivalDateTime))
                .isInstanceOf(AttendanceException.class)
                .hasMessage(makeHolidayAttendanceExceptionMessage(arrivalDateTime.toLocalDate()));
    }

    static Stream<Arguments> 출석_확인_등교일이_아닌_경우_예외_메세지를_출력한다() {
        return Stream.of(
                Arguments.of(LocalDateTime.of(SATURDAY, MONDAY_ATTENDANCE_TIME)),
                Arguments.of(LocalDateTime.of(SATURDAY, MONDAY_ATTENDANCE_TIME)),
                Arguments.of(LocalDateTime.of(SATURDAY, MONDAY_ATTENDANCE_TIME))
        );
    }

    @DisplayName("출석 확인 - 캠퍼스 운영 시간이 아닌 경우 예외 메세지를 출력한다")
    @ParameterizedTest
    @MethodSource()
    void 출석_확인_캠퍼스_운영_시간이_아닌_경우_예외_메세지를_출력한다(LocalDateTime arrivalDateTime) {
        assertThatThrownBy(() -> attendanceSystem.addAttendanceRecord(VALID_CREW_NICKNAME, arrivalDateTime))
                .isInstanceOf(AttendanceException.class)
                .hasMessage(ExceptionMessage.OUT_OF_CAMPUS_TIME.getMessage());
    }

    static Stream<Arguments> 출석_확인_캠퍼스_운영_시간이_아닌_경우_예외_메세지를_출력한다() {
        return Stream.of(
                Arguments.of(LocalDateTime.of(MONDAY, CAMPUS_START_TIME.minusSeconds(1))),
                Arguments.of(LocalDateTime.of(MONDAY, CAMPUS_END_TIME.plusSeconds(1)))
        );
    }

    @DisplayName("출석 기록 수정 - 닉네임, 수정 목표 날짜, 새로운 출석 시간으로 기존 출석 기록을 수정할 수 있다")
    @Test
    void 출석_기록_수정_닉네임_수정_목표_날짜_새로운_출석_시간으로_기존_출석_기록을_수정할_수_있다() {
        LocalDateTime oldDateTime = LocalDateTime.of(MONDAY, MONDAY_ATTENDANCE_TIME);
        attendanceSystem.addAttendanceRecord(VALID_CREW_NICKNAME, oldDateTime);

        LocalDateTime newDateTime = LocalDateTime.of(MONDAY, MONDAY_LATE_TIME);
        attendanceSystem.updateAttendance(VALID_CREW_NICKNAME, newDateTime.toLocalDate(), newDateTime.toLocalTime());

        AttendanceRecord actualRecord = recordStorage.findWithAbsenceRecord(VALID_CREW_NICKNAME, MONDAY);
        assertThat(actualRecord.getAttendanceType()).isEqualTo(AttendanceType.LATE);
    }

    @DisplayName("출석 기록 수정 - 교육시간과 출석정책을 기준으로 출석 상태를 결정한다")
    @ParameterizedTest
    @MethodSource()
    void 출석_기록_수정_교육시간과_출석정책을_기준으로_출석_상태를_결정한다(LocalDateTime newDateTime, AttendanceType expectedType) {
        attendanceSystem.updateAttendance(
                VALID_CREW_NICKNAME, newDateTime.toLocalDate(), newDateTime.toLocalTime());

        AttendanceRecord actualRecord = recordStorage.findWithAbsenceRecord(VALID_CREW_NICKNAME, MONDAY);
        assertThat(actualRecord.getAttendanceType()).isEqualTo(expectedType);
    }

    static Stream<Arguments> 출석_기록_수정_교육시간과_출석정책을_기준으로_출석_상태를_결정한다() {
        return Stream.of(
                Arguments.of(LocalDateTime.of(MONDAY, MONDAY_ATTENDANCE_TIME), AttendanceType.ATTENDANCE),
                Arguments.of(LocalDateTime.of(MONDAY, MONDAY_LATE_TIME.minusSeconds(1)), AttendanceType.ATTENDANCE),
                Arguments.of(LocalDateTime.of(MONDAY, MONDAY_LATE_TIME), AttendanceType.LATE),
                Arguments.of(LocalDateTime.of(MONDAY, MONDAY_LATE_TIME.plusSeconds(1)), AttendanceType.LATE),
                Arguments.of(LocalDateTime.of(MONDAY, MONDAY_ABSENCE_TIME.minusSeconds(1)), AttendanceType.LATE),
                Arguments.of(LocalDateTime.of(MONDAY, MONDAY_ABSENCE_TIME), AttendanceType.ABSENCE),
                Arguments.of(LocalDateTime.of(MONDAY, MONDAY_ABSENCE_TIME.plusSeconds(1)), AttendanceType.ABSENCE)
        );
    }

    @DisplayName("출석 기록 수정 - 닉네임이 등록되지 않은 경우 예외 메세지를 출력한다")
    @Test
    void 출석_기록_수정_닉네임이_등록되지_않은_경우_예외_메세지를_출력한다() {
        assertThatThrownBy(
                () -> attendanceSystem.updateAttendance(INVALID_CREW_NICKNAME, MONDAY, MONDAY_ATTENDANCE_TIME))
                .isInstanceOf(AttendanceException.class)
                .hasMessage(ExceptionMessage.INVALID_CREW.getMessage());
    }

    @DisplayName("출석 기록 수정 - 등교일이 아닌 경우 예외 메세지를 출력한다")
    @ParameterizedTest
    @MethodSource()
    void 출석_기록_수정_등교일이_아닌_경우_예외_메세지를_출력한다(LocalDate holiday) {
        assertThatThrownBy(
                () -> attendanceSystem.updateAttendance(VALID_CREW_NICKNAME, holiday, MONDAY_ATTENDANCE_TIME))
                .isInstanceOf(AttendanceException.class)
                .hasMessage(makeHolidayAttendanceExceptionMessage(holiday));
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
    @MethodSource()
    void 출석_기록_수정_캠퍼스_운영_시간이_아닌_경우_예외_메세지를_출력한다(LocalDateTime arrivalDateTime) {
        assertThatThrownBy(() -> attendanceSystem.updateAttendance(
                VALID_CREW_NICKNAME, arrivalDateTime.toLocalDate(), arrivalDateTime.toLocalTime()))
                .isInstanceOf(AttendanceException.class)
                .hasMessage(ExceptionMessage.OUT_OF_CAMPUS_TIME.getMessage());
    }

    static Stream<Arguments> 출석_기록_수정_캠퍼스_운영_시간이_아닌_경우_예외_메세지를_출력한다() {
        return Stream.of(
                Arguments.of(LocalDateTime.of(MONDAY, CAMPUS_START_TIME.minusSeconds(1))),
                Arguments.of(LocalDateTime.of(MONDAY, CAMPUS_END_TIME.plusSeconds(1)))
        );
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

    @DisplayName("출석 조회 - 닉네임을 통해 해당 크루의 출석 현황을 조회할 수 있다")
    @Test
    void 출석_조회_닉네임을_통해_해당_크루의_출석_현황을_조회할_수_있다() {
        attendanceSystem.addAttendanceRecord(VALID_CREW_NICKNAME, LocalDateTime.of(2025, 2, 19, 10, 5));
        attendanceSystem.addAttendanceRecord(VALID_CREW_NICKNAME, LocalDateTime.of(2025, 2, 18, 8, 50));
        attendanceSystem.addAttendanceRecord(VALID_CREW_NICKNAME, LocalDateTime.of(2025, 2, 17, 8, 50));
        LocalDate today = LocalDate.of(2025, 2, 21);
        AttendanceState state = attendanceSystem.calculateAttendanceStateInMonth(VALID_CREW_NICKNAME, today);

        assertThat(state.getAttendanceCount()).isEqualTo(2);
        assertThat(state.getLateCount()).isEqualTo(1);
        assertThat(state.getAbsenceCount()).isEqualTo(12);
    }

    @DisplayName("출석 조회 - 닉네임을 통해 해당 크루의 제적 위험도를 계산할 수 있다")
    @Test
    void 출석_조회_닉네임을_통해_해당_크루의_제적_위험도를_계산할_수_있다() {
        attendanceSystem.addAttendanceRecord(VALID_CREW_NICKNAME, LocalDateTime.of(2025, 2, 3, 10, 5));
        attendanceSystem.addAttendanceRecord(VALID_CREW_NICKNAME, LocalDateTime.of(2025, 2, 4, 8, 50));
        LocalDate today = LocalDate.of(2025, 2, 7);
        AttendanceState state = attendanceSystem.calculateAttendanceStateInMonth(VALID_CREW_NICKNAME, today);

        assertThat(state.getRiskTyp()).isEqualTo(RiskType.COUNSELING);
    }

    @DisplayName("출석 조회 - 닉네임이 등록되지 않은 경우 예외 메세지를 출력한다")
    @Test
    void 출석_조회_닉네임이_등록되지_않은_경우_예외_메세지를_출력한다() {
        LocalDate today = LocalDate.of(2025, 2, 7);
        assertThatThrownBy(() -> attendanceSystem.calculateAttendanceStateInMonth(INVALID_CREW_NICKNAME, today))
                .isInstanceOf(AttendanceException.class)
                .hasMessage(ExceptionMessage.INVALID_CREW.getMessage());
    }

    @DisplayName("제적 위험자 조회 - 제적 위험이 있는 크루의 출석 상태와 제적 위험도를 구할 수 있다")
    @Test
    void 제적_위험이_있는_크루의_출석_상태와_제적_위험도를_구할_수_있다() {
        addNotRiskCrew(VALID_CREW_NICKNAME);
        addWarningTargetCrew("쿠키2");
        addCounselingTargetCrew("쿠키3");
        addExpulsionTargetCrew("쿠키4");

        List<AttendanceState> states = attendanceSystem.findRiskCrew(LocalDate.of(2025, 2, 8));
        assertThat(states)
                .extracting(AttendanceState::getRiskTyp)
                .containsExactlyInAnyOrder(RiskType.WARNING, RiskType.COUNSELING, RiskType.EXPULSION);
    }

    @DisplayName("제적 위험자 조회 - 제적 위험자 첫번째로 제적 위험도의 내림차순으로 정렬된다")
    @Test
    void 제적_위험자_첫번째로_제적_위험도로_정렬된다() {
        addNotRiskCrew(VALID_CREW_NICKNAME);
        addWarningTargetCrew("쿠키2");
        addCounselingTargetCrew("쿠키3");
        addExpulsionTargetCrew("쿠키4");

        List<AttendanceState> states = attendanceSystem.findRiskCrew(LocalDate.of(2025, 2, 8));
        assertThat(states)
                .extracting(AttendanceState::getRiskTyp)
                .containsExactly(RiskType.EXPULSION, RiskType.COUNSELING, RiskType.WARNING);
    }

    @DisplayName("제적 위험자 조회 - 제적 위험자 두번째로 결석 지각의 내림차순으로 정렬된다")
    @Test
    void 제적_위험자_두번째로_결석_지각의_내림차순으로_정렬된다() {
        addNotRiskCrew(VALID_CREW_NICKNAME);
        addWarningTargetCrew("빙봉");
        attendanceSystem.addAttendanceRecord("빙봉", LocalDateTime.of(2025, 2, 10, 8, 50));
        addWarningTargetCrew("이든");
        attendanceSystem.addAttendanceRecord("이든", LocalDateTime.of(2025, 2, 10, 13, 5));

        List<AttendanceState> states = attendanceSystem.findRiskCrew(LocalDate.of(2025, 2, 10));
        assertThat(states)
                .extracting(AttendanceState::getNickname)
                .containsExactly("빙봉", "이든");
    }

    @DisplayName("제적 위험자 조회 - 제적 위험자 세번째로 닉네임의 내림차순으로 정렬된다.")
    @Test
    void 제적_위험자_세번째로_닉네임의_내림차순으로_정렬된다() {
        addNotRiskCrew(VALID_CREW_NICKNAME);
        addWarningTargetCrew("가");
        addWarningTargetCrew("나");
        addWarningTargetCrew("다");

        List<AttendanceState> states = attendanceSystem.findRiskCrew(LocalDate.of(2025, 2, 8));
        assertThat(states)
                .extracting(AttendanceState::getNickname)
                .containsExactly("가", "나", "다");
    }


    String makeHolidayAttendanceExceptionMessage(LocalDate date) {
        return String.format(ExceptionMessage.HOLIDAY_ATTENDANCE.getMessage(),
                date.getMonth().getValue(), date.getDayOfMonth(),
                date.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.KOREA));
    }

    void addNotRiskCrew(String name) {
        crewStorage.add(name);
        attendanceSystem.addAttendanceRecord(name, LocalDateTime.of(2025, 2, 3, 8, 50));
        attendanceSystem.addAttendanceRecord(name, LocalDateTime.of(2025, 2, 4, 8, 50));
        attendanceSystem.addAttendanceRecord(name, LocalDateTime.of(2025, 2, 5, 8, 50));
        attendanceSystem.addAttendanceRecord(name, LocalDateTime.of(2025, 2, 6, 8, 50));
        attendanceSystem.addAttendanceRecord(name, LocalDateTime.of(2025, 2, 7, 8, 50));
    }

    void addWarningTargetCrew(String name) {
        crewStorage.add(name);
        attendanceSystem.addAttendanceRecord(name, LocalDateTime.of(2025, 2, 3, 8, 50));
        attendanceSystem.addAttendanceRecord(name, LocalDateTime.of(2025, 2, 4, 8, 50));
        attendanceSystem.addAttendanceRecord(name, LocalDateTime.of(2025, 2, 5, 8, 50));
        attendanceSystem.addAttendanceRecord(name, LocalDateTime.of(2025, 2, 6, 15, 0));
        attendanceSystem.addAttendanceRecord(name, LocalDateTime.of(2025, 2, 7, 15, 0));
    }

    void addCounselingTargetCrew(String name) {
        crewStorage.add(name);
        attendanceSystem.addAttendanceRecord(name, LocalDateTime.of(2025, 2, 3, 8, 50));
        attendanceSystem.addAttendanceRecord(name, LocalDateTime.of(2025, 2, 4, 8, 50));
        attendanceSystem.addAttendanceRecord(name, LocalDateTime.of(2025, 2, 5, 15, 0));
        attendanceSystem.addAttendanceRecord(name, LocalDateTime.of(2025, 2, 6, 15, 0));
        attendanceSystem.addAttendanceRecord(name, LocalDateTime.of(2025, 2, 7, 15, 0));
    }

    void addExpulsionTargetCrew(String name) {
        crewStorage.add(name);
        attendanceSystem.addAttendanceRecord(name, LocalDateTime.of(2025, 2, 3, 15, 0));
        attendanceSystem.addAttendanceRecord(name, LocalDateTime.of(2025, 2, 4, 15, 0));
        attendanceSystem.addAttendanceRecord(name, LocalDateTime.of(2025, 2, 5, 15, 0));
        attendanceSystem.addAttendanceRecord(name, LocalDateTime.of(2025, 2, 6, 15, 0));
        attendanceSystem.addAttendanceRecord(name, LocalDateTime.of(2025, 2, 7, 15, 0));
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