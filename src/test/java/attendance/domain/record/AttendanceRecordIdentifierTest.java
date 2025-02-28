package attendance.domain.record;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceRecordIdentifierTest {

    LocalDate COMMON_DATE = LocalDate.of(2025, 2, 4);
    LocalTime COMMON_TIME = LocalTime.of(8, 50);

    @DisplayName("출석 기록이 어떤 크루의 기록인지 체크한다")
    @Test
    void 출석_기록이_어떤_크루의_기록인지_체크한다() {
        AttendanceRecordIdentifier identifier = new AttendanceRecordIdentifier("쿠키", COMMON_DATE);
        assertThat(identifier.checkNickname("쿠키")).isTrue();
        assertThat(identifier.checkNickname("빙봉")).isFalse();
    }

    @DisplayName("출석 기록인 현재 연도의 월에 포함된 기록인지 체크한다")
    @Test
    void 출석_기록인_현재_연도의_월에_포함된_기록인지_체크한다() {
        AttendanceRecordIdentifier identifierInMonth = new AttendanceRecordIdentifier("쿠키", LocalDate.of(2025, 2, 5));
        AttendanceRecordIdentifier identifierOutMonth = new AttendanceRecordIdentifier("쿠키", LocalDate.of(2025, 3, 5));

        assertThat(identifierInMonth.checkIsInMonth(2025, Month.FEBRUARY)).isTrue();
        assertThat(identifierOutMonth.checkIsInMonth(2025, Month.FEBRUARY)).isFalse();
    }
}