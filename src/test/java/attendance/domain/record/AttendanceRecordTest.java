package attendance.domain.record;

import static org.assertj.core.api.Assertions.assertThat;

import attendance.domain.checker.AttendanceType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceRecordTest {

    LocalDate COMMON_DATE = LocalDate.of(2025, 2, 4);
    LocalTime COMMON_TIME = LocalTime.of(8, 50);

    @DisplayName("출석 기록의 타입을 체크한다")
    @Test
    void 출석_기록의_타입을_체크한다() {
        LocalDateTime dateTime = LocalDateTime.of(COMMON_DATE, COMMON_TIME);
        AttendanceRecord record = new AttendanceRecord("쿠키", dateTime, AttendanceType.ATTENDANCE);

        assertThat(record.checkType(AttendanceType.ATTENDANCE)).isTrue();
        assertThat(record.checkType(AttendanceType.LATE)).isFalse();
    }
}