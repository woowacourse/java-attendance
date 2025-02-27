package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import attendance.TestUtil;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceRecordTest {

    @DisplayName("월요일 출석 기록에 대해서 출석 상태를 알맞게 계산한다")
    @Test
    void test_monday_presentRecord() {
        // given
        LocalDateTime attendanceDateTime = LocalDateTime.of(2024, 12, 2, 13, 0);

        // when
        AttendanceRecord record = TestUtil.createRecord(attendanceDateTime);

        // then
        AttendanceStatus status = record.getAttendanceStatus();
        assertThat(status).isEqualTo(AttendanceStatus.PRESENT);
    }

    @DisplayName("월요일 지각 기록에 대해서 출석 상태를 알맞게 계산한다")
    @Test
    void test_monday_lateRecord() {
        // given
        LocalDateTime attendanceDateTime = LocalDateTime.of(2024, 12, 2, 13, 6);

        // when
        AttendanceRecord record = TestUtil.createRecord(attendanceDateTime);

        // then
        AttendanceStatus status = record.getAttendanceStatus();
        assertThat(status).isEqualTo(AttendanceStatus.LATE);
    }

    @DisplayName("월요일 결석 기록에 대해서 출석 상태를 알맞게 계산한다")
    @Test
    void test_monday_absentRecord() {
        // given
        LocalDateTime attendanceDateTime = LocalDateTime.of(2024, 12, 2, 13, 31);

        // when
        AttendanceRecord record = TestUtil.createRecord(attendanceDateTime);

        // then
        AttendanceStatus status = record.getAttendanceStatus();
        assertThat(status).isEqualTo(AttendanceStatus.ABSENT);
    }

    @DisplayName("월요일이 아닌 요일의 출석 기록에 대해서 출석 상태를 알맞게 계산한다")
    @Test
    void test_presentRecord() {
        // given
        LocalDateTime attendanceDateTime = LocalDateTime.of(2024, 12, 3, 10, 0);

        // when
        AttendanceRecord record = TestUtil.createRecord(attendanceDateTime);

        // then
        AttendanceStatus status = record.getAttendanceStatus();
        assertThat(status).isEqualTo(AttendanceStatus.PRESENT);
    }

    @DisplayName("월요일이 아닌 요일의 지각 기록에 대해서 출석 상태를 알맞게 계산한다")
    @Test
    void test_lateRecord() {
        // given
        LocalDateTime attendanceDateTime = LocalDateTime.of(2024, 12, 3, 10, 6);

        // when
        AttendanceRecord record = TestUtil.createRecord(attendanceDateTime);

        // then
        AttendanceStatus status = record.getAttendanceStatus();
        assertThat(status).isEqualTo(AttendanceStatus.LATE);
    }

    @DisplayName("월요일이 아닌 요일의 결석 기록에 대해서 출석 상태를 알맞게 계산한다")
    @Test
    void test_absentRecord() {
        // given
        LocalDateTime attendanceDateTime = LocalDateTime.of(2024, 12, 3, 10, 31);

        // when
        AttendanceRecord record = TestUtil.createRecord(attendanceDateTime);

        // then
        AttendanceStatus status = record.getAttendanceStatus();
        assertThat(status).isEqualTo(AttendanceStatus.ABSENT);
    }

    @DisplayName("특정 날짜와 현재 출석 날짜의 날짜가 같다면 true를 반환한다.")
    @Test
    void test_isSameDate_true() {
        // given
        LocalDateTime attendanceDateTime = LocalDateTime.of(2024, 12, 3, 10, 0);
        AttendanceRecord record = TestUtil.createRecord(attendanceDateTime);

        // when
        boolean isSame = record.isSameDate(TestUtil.WoowaDatefrom(LocalDate.of(2024, 12, 3)));

        // then
        assertThat(isSame).isTrue();
    }

    @DisplayName("수정 기능을 통해 출석 상태가 지각 상태로 수정될 수 있다.")
    @Test
    void test_modifyTime() {
        // given
        LocalDateTime attendanceDateTime = LocalDateTime.of(2024, 12, 3, 10, 0);
        AttendanceRecord record = TestUtil.createRecord(attendanceDateTime);

        // when
        LocalTime modifyTime = LocalTime.of(10, 6);
        record.modify(modifyTime);

        // then
        assertThat(record.getAttendanceStatus()).isEqualTo(AttendanceStatus.LATE);
    }

    @DisplayName("특정 날짜와 현재 출석 날짜의 날짜가 다르다면 false를 반환한다.")
    @Test
    void test_isSameDate_false() {
        // given
        LocalDateTime attendanceDateTime = LocalDateTime.of(2024, 12, 3, 10, 31);
        AttendanceRecord record = TestUtil.createRecord(attendanceDateTime);

        // when
        boolean isSame = record.isSameDate(TestUtil.WoowaDatefrom(LocalDate.of(2024, 12, 4)));

        // then
        assertThat(isSame).isFalse();
    }

}