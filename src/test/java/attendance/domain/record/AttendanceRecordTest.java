package attendance.domain.record;

import static org.assertj.core.api.Assertions.assertThat;

import attendance.domain.checker.AttendanceType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceRecordTest {

    LocalDate COMMON_DATE = LocalDate.of(2025, 2, 4);
    LocalTime COMMON_TIME = LocalTime.of(8, 50);

    @DisplayName("출석 기록를 어떤 크루의 어떤 날짜의 기록인지 체크한다")
    @Test
    void 출석_기록를_어떤_크루의_어떤_날짜의_기록인지_체크한다() {
        LocalDateTime dateTime = LocalDateTime.of(COMMON_DATE, COMMON_TIME);
        AttendanceRecord record = new AttendanceRecord("쿠키", dateTime, AttendanceType.ATTENDANCE);

        assertThat(record.isSame("쿠키", COMMON_DATE)).isTrue();
        assertThat(record.isSame("쿠키", COMMON_DATE.plusDays(1))).isFalse();
    }

    @DisplayName("출석 기록이 어떤 크루의 기록인지 체크한다")
    @Test
    void 출석_기록이_어떤_크루의_기록인지_체크한다() {
        LocalDateTime dateTime = LocalDateTime.of(COMMON_DATE, COMMON_TIME);
        AttendanceRecord record = new AttendanceRecord("쿠키", dateTime, AttendanceType.ATTENDANCE);

        assertThat(record.checkNickname("쿠키")).isTrue();
        assertThat(record.checkNickname("빙봉")).isFalse();
    }

    @DisplayName("출석 기록인 현재 연도의 월에 포함된 기록인지 체크한다")
    @Test
    void 출석_기록인_현재_연도의_월에_포함된_기록인지_체크한다() {
        LocalDateTime dateTimeInMonth = LocalDateTime.of(LocalDate.of(2025, 2, 5), COMMON_TIME);
        AttendanceRecord recordInMonth = new AttendanceRecord("쿠키", dateTimeInMonth, AttendanceType.ATTENDANCE);
        LocalDateTime dateTimeOutMonth = LocalDateTime.of(LocalDate.of(2025, 3, 5), COMMON_TIME);
        AttendanceRecord recordOutMonth = new AttendanceRecord("빙봉", dateTimeOutMonth, AttendanceType.ATTENDANCE);

        assertThat(recordInMonth.checkIsInMonth(2025, Month.FEBRUARY)).isTrue();
        assertThat(recordOutMonth.checkIsInMonth(2025, Month.FEBRUARY)).isFalse();
    }

    @DisplayName("출석 기록의 타입을 체크한다")
    @Test
    void 출석_기록의_타입을_체크한다() {
        LocalDateTime dateTime = LocalDateTime.of(COMMON_DATE, COMMON_TIME);
        AttendanceRecord record = new AttendanceRecord("쿠키", dateTime, AttendanceType.ATTENDANCE);

        assertThat(record.checkType(AttendanceType.ATTENDANCE)).isTrue();
        assertThat(record.checkType(AttendanceType.LATE)).isFalse();
    }
}