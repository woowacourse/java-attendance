package attendance.domain;

import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceRecordTest {

    @DisplayName("월요일 출석 기록에 대해서 출석 상태를 알맞게 계산한다")
    @Test
    void test_presentRecord() {
        // given
        LocalDateTime attendanceDateTime = LocalDateTime.of(2024, 12, 2, 13, 0);

        // when
        AttendanceRecord record = new AttendanceRecord(attendanceDateTime);

        // then
        AttendanceStatus status = record.getAttendanceStatus();
        Assertions.assertThat(status).isEqualTo(AttendanceStatus.PRESENT);
    }

    @DisplayName("월요일 지각 기록에 대해서 출석 상태를 알맞게 계산한다")
    @Test
    void test_lateRecord() {
        // given
        LocalDateTime attendanceDateTime = LocalDateTime.of(2024, 12, 2, 13, 6);

        // when
        AttendanceRecord record = new AttendanceRecord(attendanceDateTime);

        // then
        AttendanceStatus status = record.getAttendanceStatus();
        Assertions.assertThat(status).isEqualTo(AttendanceStatus.LATE);
    }

    @DisplayName("월요일 결석 기록에 대해서 출석 상태를 알맞게 계산한다")
    @Test
    void test_absentRecord() {
        // given
        LocalDateTime attendanceDateTime = LocalDateTime.of(2024, 12, 2, 13, 31);

        // when
        AttendanceRecord record = new AttendanceRecord(attendanceDateTime);

        // then
        AttendanceStatus status = record.getAttendanceStatus();
        Assertions.assertThat(status).isEqualTo(AttendanceStatus.ABSENT);
    }
}