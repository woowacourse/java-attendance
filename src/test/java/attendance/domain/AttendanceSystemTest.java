package attendance.domain;

import static attendance.domain.AttendanceType.ATTENDANCE;
import static attendance.domain.AttendanceType.EXPULSION;
import static attendance.domain.AttendanceType.LATE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import attendance.exception.ExceptionMessage;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceSystemTest {

    final static LocalDate HOLIDAY = LocalDate.of(2024, 12, 7);
    final static LocalDate NOT_HOIlDAY = LocalDate.of(2024, 12, 12);
    final static LocalTime NOT_CAMPUS_TIME = CampusSchedule.CAMPUS_OPEN_TIME.getTime().minusMinutes(1);
    final static LocalTime ATTENDANCE_TIME = CampusSchedule.NOT_MONDAY_EDUCATION_START_TIME.getTime();
    final static LocalTime LATE_TIME = CampusSchedule.NOT_MONDAY_EDUCATION_START_TIME.getTime()
            .plusMinutes(LATE.getOverMinutes());
    final static LocalTime EXPULSION_TIME = CampusSchedule.NOT_MONDAY_EDUCATION_START_TIME.getTime()
            .plusMinutes(EXPULSION.getOverMinutes());

    AttendanceSystem attendanceSystem;
    CrewStorage crewStorage;
    AttendanceRecordStorage attendanceRecordStorage;
    HolidayChecker holidayChecker;

    @BeforeEach
    void beforeEach() {
        crewStorage = new CrewStorage();
        attendanceRecordStorage = new AttendanceRecordStorage();
        holidayChecker = new HolidayChecker();
        attendanceSystem = new AttendanceSystem(
                crewStorage,
                attendanceRecordStorage,
                holidayChecker);
    }

    @DisplayName("출석 저장 - 닉네임과 등교 시간을 입력받아 출석 기록 저장")
    @Test
    void 닉네임과_등교_시간을_입력받아_출석_기록_저장() {
        crewStorage.add(new Crew("쿠키"));
        LocalDateTime dateTime = LocalDateTime.of(NOT_HOIlDAY, ATTENDANCE_TIME);

        attendanceSystem.saveAttendanceRecord("쿠키", dateTime);

        AttendanceRecord record = attendanceRecordStorage.find("쿠키", NOT_HOIlDAY).get();
        assertThat(record.getNickname()).isEqualTo("쿠키");
        assertThat(record.getDate()).isEqualTo(NOT_HOIlDAY);
        assertThat(record.getTime()).isEqualTo(ATTENDANCE_TIME);
        assertThat(record.getType()).isEqualTo(ATTENDANCE);
    }

    @DisplayName("출석 저장 - 결석 기록은 저장되지 않는다.")
    @Test
    void 결석_기록은_저장되지_않는다() {
        crewStorage.add(new Crew("쿠키"));
        LocalDateTime dateTime = LocalDateTime.of(NOT_HOIlDAY, EXPULSION_TIME);

        attendanceSystem.saveAttendanceRecord("쿠키", dateTime);

        Optional<AttendanceRecord> record = attendanceRecordStorage.find("쿠키", NOT_HOIlDAY);
        assertThat(record).isEmpty();
    }

    @DisplayName("출석 저장 - 캠퍼스 운영시간이 아닌 경우 예외 발생")
    @Test
    void 캠퍼스_운영시간이_아닌_경우_예외_발생() {
        crewStorage.add(new Crew("쿠키"));
        LocalDateTime dateTime = LocalDateTime.of(NOT_HOIlDAY, NOT_CAMPUS_TIME);

        assertThatIllegalArgumentException()
                .isThrownBy(() -> attendanceSystem.saveAttendanceRecord("쿠키", dateTime))
                .withMessage(ExceptionMessage.NOT_CAMPUS_TIME.getContent());
    }

    @DisplayName("출석 저장 - 이미 출석을 완료한 경우 예외 발생")
    @Test
    void 이미_출석을_완료한_경우_예외_발생() {
        crewStorage.add(new Crew("쿠키"));
        LocalDateTime dateTime = LocalDateTime.of(NOT_HOIlDAY, ATTENDANCE_TIME);
        attendanceSystem.saveAttendanceRecord("쿠키", dateTime);

        assertThatIllegalArgumentException()
                .isThrownBy(() -> attendanceSystem.saveAttendanceRecord("쿠키", dateTime))
                .withMessage(ExceptionMessage.ALREADY_ATTENDANCE.getContent());
    }

    @DisplayName("출석 저장 - 휴일의 경우 예외 발생")
    @Test
    void 휴일의_경우_예외_발생() {
        crewStorage.add(new Crew("쿠키"));
        LocalDateTime dateTime = LocalDateTime.of(HOLIDAY, ATTENDANCE_TIME);

        String exceptionMessage = String.format(ExceptionMessage.HOLIDAY.getContent(),
                HOLIDAY.getMonth().getValue(), HOLIDAY.getDayOfMonth(),
                HOLIDAY.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA));
        assertThatIllegalArgumentException()
                .isThrownBy(() -> attendanceSystem.saveAttendanceRecord("쿠키", dateTime))
                .withMessage(exceptionMessage);
    }

    @DisplayName("출석 저장 - 등록되지 않은 닉네임의 경우 예외 발생")
    @Test
    void 등록되지_않은_닉네임의_경우_예외_발생() {
        crewStorage.add(new Crew("쿠키"));
        LocalDateTime dateTime = LocalDateTime.of(NOT_HOIlDAY, ATTENDANCE_TIME);

        assertThatIllegalArgumentException()
                .isThrownBy(() -> attendanceSystem.saveAttendanceRecord("빙봉", dateTime))
                .withMessage(ExceptionMessage.NOT_FOUND_CREW.getContent());
    }

    @DisplayName("출석 수정 - 출석 기록을 수정한다.")
    @Test
    void 출석_수정_출석_기록을_수정한다() {
        saveRecord("쿠키", NOT_HOIlDAY, LATE_TIME);

        attendanceSystem.updateAttendanceRecord("쿠키", NOT_HOIlDAY, ATTENDANCE_TIME);

        AttendanceRecord record = attendanceRecordStorage.find("쿠키", NOT_HOIlDAY).get();
        assertThat(record.getTime()).isEqualTo(ATTENDANCE_TIME);
    }

    @DisplayName("출석 수정 - 결석 기록으로 수정할 시 출석 기록이 제거된다.")
    @Test
    void 출석_수정_결석_기록으로_수정할_시_출석_기록이_제거된다() {
        saveRecord("쿠키", NOT_HOIlDAY, LATE_TIME);

        attendanceSystem.updateAttendanceRecord("쿠키", NOT_HOIlDAY, EXPULSION_TIME);

        Optional<AttendanceRecord> record = attendanceRecordStorage.find("쿠키", NOT_HOIlDAY);
        assertThat(record).isEmpty();
    }


    @DisplayName("출석 수정 - 캠퍼스 운영시간이 아닌 경우 예외 발생")
    @Test
    void 출석_수정_캠퍼스_운영시간이_아닌_경우_예외_발생() {
        saveRecord("쿠키", NOT_HOIlDAY, LATE_TIME);

        assertThatIllegalArgumentException()
                .isThrownBy(() -> attendanceSystem.updateAttendanceRecord("쿠키", NOT_HOIlDAY, NOT_CAMPUS_TIME))
                .withMessage(ExceptionMessage.NOT_CAMPUS_TIME.getContent());
    }

    @DisplayName("출석 수정 - 휴일의 출석 기록 수정시 예외 발생")
    @Test
    void 출석_수정_휴일의_출석_기록_수정시_예외_발생() {
        crewStorage.add(new Crew("쿠키"));

        String exceptionMessage = String.format(ExceptionMessage.HOLIDAY.getContent(),
                HOLIDAY.getMonth().getValue(), HOLIDAY.getDayOfMonth(),
                HOLIDAY.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA));
        assertThatIllegalArgumentException()
                .isThrownBy(() -> attendanceSystem.updateAttendanceRecord("쿠키", HOLIDAY, ATTENDANCE_TIME))
                .withMessage(exceptionMessage);
    }

    @DisplayName("출석 수정 - 등록되지 않은 닉네임의 경우 예외 발생")
    @Test
    void 출석_수정_등록되지_않은_닉네임의_경우_예외_발생() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> attendanceSystem.updateAttendanceRecord("빙봉", HOLIDAY, ATTENDANCE_TIME))
                .withMessage(ExceptionMessage.NOT_FOUND_CREW.getContent());
    }

    @DisplayName("출석 조회 - 크루별 출석을 조회할 수 있다.")
    @Test
    void 출석_조회_크루별_출석을_조회할_수_있다() {
        saveRecord("쿠키", NOT_HOIlDAY, LATE_TIME);
        saveRecord("쿠키", NOT_HOIlDAY.plusDays(1), LATE_TIME);
        saveRecord("쿠키", NOT_HOIlDAY.minusDays(1), LATE_TIME);

        List<AttendanceRecord> records = attendanceSystem.searchAttendanceRecordsByCrew("쿠키", 2024, Month.DECEMBER);
        long lateCount = records.stream().filter(record -> record.getType() == LATE).count();

        assertThat(records).hasSize(22);
        assertThat(lateCount).isEqualTo(3);
    }

    @DisplayName("출석 조회 - 등록되지 않은 닉네임의 경우 예외 발생")
    @Test
    void 출석_조회_등록되지_않은_닉네임의_경우_예외_발생() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> attendanceSystem.searchAttendanceRecordsByCrew("빙봉", 2024, Month.DECEMBER))
                .withMessage(ExceptionMessage.NOT_FOUND_CREW.getContent());
    }

    @DisplayName("출석 상태 조회 - 해당 크루의 제적 위험 정보를 조회한다.")
    @Test
    void 출석_상태_조회_해당_크루의_제적_위험_정보를_조회한다() {
        saveRiskRecord("쿠키");
        RiskStatistic riskStatistic = attendanceSystem.searchRiskStatistic(
                "쿠키",
                LocalDate.of(2024, 12, 9),
                LocalDate.of(2024, 12, 13));
        assertThat(riskStatistic.getRiskType()).isEqualTo(RiskType.COUNSELING);
    }


    @DisplayName("출석 상태 조회 - 등록되지 않은 닉네임의 경우 예외 발생")
    @Test
    void 출석_상태_조회_등록되지_않은_닉네임의_경우_예외_발생() {
        LocalDate startDate = LocalDate.of(2024, 12, 9);
        LocalDate endDate = LocalDate.of(2024, 12, 13);
        assertThatIllegalArgumentException()
                .isThrownBy(() -> attendanceSystem.searchRiskStatistic("빙봉", startDate, endDate))
                .withMessage(ExceptionMessage.NOT_FOUND_CREW.getContent());
    }

    @DisplayName("제적 위험자 조회 - 제적 위험자를 조회할 수 있다.")
    @Test
    void 제적_위험자_조회_제적_위험자를_조회할_수_있다() {
        saveRiskRecord(List.of("쿠키1", "쿠키2", "쿠키3"));
        saveNotRiskRecord(List.of("쿠키4", "쿠키5"));

        List<RiskStatistic> riskStatistics = attendanceSystem.searchRiskStatistics(
                LocalDate.of(2024, 12, 9),
                LocalDate.of(2024, 12, 13));
        assertThat(riskStatistics)
                .extracting(RiskStatistic::getNickname)
                .containsExactlyInAnyOrder("쿠키1", "쿠키2", "쿠키3");
    }


    private void saveRecord(String nickName, LocalDate date, LocalTime time) {
        if (!crewStorage.isContained(nickName)) {
            crewStorage.add(new Crew(nickName));
        }
        LocalDateTime dateTime = LocalDateTime.of(date, time);
        attendanceSystem.saveAttendanceRecord("쿠키", dateTime);
    }

    private void saveRiskRecord(List<String> nicknames) {
        nicknames.forEach(this::saveRiskRecord);
    }

    private void saveRiskRecord(String nickname) {
        if (!crewStorage.isContained(nickname)) {
            crewStorage.add(new Crew(nickname));
        }
    }

    private void saveNotRiskRecord(List<String> nicknames) {
        nicknames.forEach(this::saveNotRiskRecord);
    }

    private void saveNotRiskRecord(String nickname) {
        if (!crewStorage.isContained(nickname)) {
            crewStorage.add(new Crew(nickname));
        }
        attendanceRecordStorage.add(makeRecord(nickname, LocalDateTime.of(2024, 12, 9, 8, 10, 0), ATTENDANCE));
        attendanceRecordStorage.add(makeRecord(nickname, LocalDateTime.of(2024, 12, 10, 8, 10, 0), ATTENDANCE));
        attendanceRecordStorage.add(makeRecord(nickname, LocalDateTime.of(2024, 12, 11, 8, 10, 0), ATTENDANCE));
        attendanceRecordStorage.add(makeRecord(nickname, LocalDateTime.of(2024, 12, 12, 8, 10, 0), ATTENDANCE));
        attendanceRecordStorage.add(makeRecord(nickname, LocalDateTime.of(2024, 12, 13, 8, 10, 0), ATTENDANCE));
    }

    private AttendanceRecord makeRecord(
            String nickname, LocalDateTime arrivalDateTime, AttendanceType attendanceType
    ) {
        return new AttendanceRecord(nickname, arrivalDateTime, attendanceType);
    }
}