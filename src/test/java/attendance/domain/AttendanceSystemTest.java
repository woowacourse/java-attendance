package attendance.domain;

import static attendance.domain.AttendanceType.ATTENDANCE;
import static attendance.domain.AttendanceType.EXPULSION;
import static attendance.domain.AttendanceType.LATE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.List;
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

        AttendanceRecord savedRecord = attendanceSystem.searchAttendanceRecordsByCrew("쿠키", Month.DECEMBER).getFirst();
        assertThat(savedRecord)
                .isEqualTo(new AttendanceRecord("쿠키", dateTime, ATTENDANCE));
    }

    @DisplayName("출석 저장 - 결석 기록은 저장되지 않는다.")
    @Test
    void 결석_기록은_저장되지_않는다() {
        crewStorage.add(new Crew("쿠키"));
        LocalDateTime dateTime = LocalDateTime.of(NOT_HOIlDAY, EXPULSION_TIME);

        attendanceSystem.saveAttendanceRecord("쿠키", dateTime);

        List<AttendanceRecord> savedRecords = attendanceSystem.searchAttendanceRecordsByCrew("쿠키", Month.DECEMBER);
        assertThat(savedRecords).isEmpty();
    }

    @DisplayName("출석 저장 - 캠퍼스 운영시간이 아닌 경우 예외 발생")
    @Test
    void 캠퍼스_운영시간이_아닌_경우_예외_발생() {
        crewStorage.add(new Crew("쿠키"));
        LocalDateTime dateTime = LocalDateTime.of(NOT_HOIlDAY, NOT_CAMPUS_TIME);

        assertThatIllegalArgumentException()
                .isThrownBy(() -> attendanceSystem.saveAttendanceRecord("쿠키", dateTime))
                .withMessage("[ERROR] 캠퍼스 운영시간이 아닙니다.");
    }

    @DisplayName("출석 저장 - 이미 출석을 완료한 경우 예외 발생")
    @Test
    void 이미_출석을_완료한_경우_예외_발생() {
        crewStorage.add(new Crew("쿠키"));
        LocalDateTime dateTime = LocalDateTime.of(NOT_HOIlDAY, ATTENDANCE_TIME);
        attendanceSystem.saveAttendanceRecord("쿠키", dateTime);

        assertThatIllegalArgumentException()
                .isThrownBy(() -> attendanceSystem.saveAttendanceRecord("쿠키", dateTime))
                .withMessage("[ERROR] 이미 출석을 완료하셨습니다. 수정 기능을 이용해주세요.");
    }

    @DisplayName("출석 저장 - 휴일의 경우 예외 발생")
    @Test
    void 휴일의_경우_예외_발생() {
        crewStorage.add(new Crew("쿠키"));
        LocalDateTime dateTime = LocalDateTime.of(HOLIDAY, ATTENDANCE_TIME);

        assertThatIllegalArgumentException()
                .isThrownBy(() -> attendanceSystem.saveAttendanceRecord("쿠키", dateTime))
                .withMessage("[ERROR] 12월 7일 토요일은 등교일이 아닙니다.");
    }

    @DisplayName("출석 저장 - 등록되지 않은 닉네임의 경우 예외 발생")
    @Test
    void 등록되지_않은_닉네임의_경우_예외_발생() {
        crewStorage.add(new Crew("쿠키"));
        LocalDateTime dateTime = LocalDateTime.of(NOT_HOIlDAY, ATTENDANCE_TIME);

        assertThatIllegalArgumentException()
                .isThrownBy(() -> attendanceSystem.saveAttendanceRecord("빙봉", dateTime))
                .withMessage("[ERROR] 등록되지 않은 닉네임입니다.");
    }

    @DisplayName("출석 수정 - 출석 기록을 수정한다.")
    @Test
    void 출석_수정_출석_기록을_수정한다() {
        saveRecord("쿠키", NOT_HOIlDAY, LATE_TIME);

        attendanceSystem.updateAttendanceRecord("쿠키", NOT_HOIlDAY, ATTENDANCE_TIME);

        AttendanceRecord records = attendanceSystem.searchAttendanceRecordsByCrew("쿠키", Month.DECEMBER).getFirst();
        assertThat(records)
                .isEqualTo(new AttendanceRecord("쿠키", LocalDateTime.of(NOT_HOIlDAY, ATTENDANCE_TIME), ATTENDANCE));
    }

    @DisplayName("출석 수정 - 결석 기록으로 수정할 시 출석 기록이 제거된다.")
    @Test
    void 출석_수정_결석_기록으로_수정할_시_출석_기록이_제거된다() {
        saveRecord("쿠키", NOT_HOIlDAY, LATE_TIME);

        attendanceSystem.updateAttendanceRecord("쿠키", NOT_HOIlDAY, EXPULSION_TIME);

        List<AttendanceRecord> records = attendanceSystem.searchAttendanceRecordsByCrew("쿠키", Month.DECEMBER);
        assertThat(records).isEmpty();
    }


    @DisplayName("출석 수정 - 캠퍼스 운영시간이 아닌 경우 예외 발생")
    @Test
    void 출석_수정_캠퍼스_운영시간이_아닌_경우_예외_발생() {
        saveRecord("쿠키", NOT_HOIlDAY, LATE_TIME);

        assertThatIllegalArgumentException()
                .isThrownBy(() -> attendanceSystem.updateAttendanceRecord("쿠키", NOT_HOIlDAY, NOT_CAMPUS_TIME))
                .withMessage("[ERROR] 캠퍼스 운영시간이 아닙니다.");
    }

    @DisplayName("출석 수정 - 휴일의 출석 기록 수정시 예외 발생")
    @Test
    void 출석_수정_휴일의_출석_기록_수정시_예외_발생() {
        crewStorage.add(new Crew("쿠키"));
        assertThatIllegalArgumentException()
                .isThrownBy(() -> attendanceSystem.updateAttendanceRecord("쿠키", HOLIDAY, ATTENDANCE_TIME))
                .withMessage("[ERROR] 12월 7일 토요일은 등교일이 아닙니다.");
    }

    @DisplayName("출석 수정 - 등록되지 않은 닉네임의 경우 예외 발생")
    @Test
    void 출석_수정_등록되지_않은_닉네임의_경우_예외_발생() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> attendanceSystem.updateAttendanceRecord("빙봉", HOLIDAY, ATTENDANCE_TIME))
                .withMessage("[ERROR] 등록되지 않은 닉네임입니다.");
    }

    @DisplayName("출석 조회 - 크루별 출석을 조회할 수 있다.")
    @Test
    void 출석_조회_크루별_출석을_조회할_수_있다() {
        saveRecord("쿠키", NOT_HOIlDAY, LATE_TIME);
        saveRecord("쿠키", NOT_HOIlDAY.plusDays(1), LATE_TIME);
        saveRecord("쿠키", NOT_HOIlDAY.minusDays(1), LATE_TIME);

        List<AttendanceRecord> records = attendanceSystem.searchAttendanceRecordsByCrew("쿠키", Month.DECEMBER);
        assertThat(records).hasSize(3);
    }

    @DisplayName("출석 조회 - 등록되지 않은 닉네임의 경우 예외 발생")
    @Test
    void 출석_조회_등록되지_않은_닉네임의_경우_예외_발생() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> attendanceSystem.searchAttendanceRecordsByCrew("빙봉", Month.DECEMBER))
                .withMessage("[ERROR] 등록되지 않은 닉네임입니다.");
    }

    @DisplayName("제적 위험자 조회 - 제적 위험자를 조회할 수 있다.")
    @Test
    void 제적_위험자_조회_제적_위험자를_조회할_수_있다() {
        saveRiskRecord(List.of("쿠키1", "쿠키2", "쿠키3"));
        saveNotRiskRecord(List.of("쿠키4", "쿠키5"));

        List<RiskStatistics> riskStatistics = attendanceSystem.searchRiskStatistics(
                LocalDate.of(2024, 12, 9),
                LocalDate.of(2024, 12, 13));
        assertThat(riskStatistics)
                .extracting(RiskStatistics::getNickname)
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