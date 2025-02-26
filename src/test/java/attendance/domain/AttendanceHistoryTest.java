package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceHistoryTest {

    @DisplayName("출석 내역에 하나의 출석 기록을 추가한다.")
    @Test
    void test_addCrewRecord() {
        // given
        AttendanceHistory history = new AttendanceHistory();
        AttendanceRecord record = new AttendanceRecord(LocalDateTime.of(2024, 12, 2, 13, 0));

        // when
        history.addRecord(record);

        // then
        assertThat(history.getRecords()).hasSize(1);
        assertThat(history.getRecords().contains(record)).isTrue();
    }

    @DisplayName("해당 크루의 특정 날짜의 기록을 지각으로 수정한다.")
    @Test
    void test_modifyCrewRecord() {
        // given
        AttendanceHistory history = new AttendanceHistory();
        AttendanceRecord record = new AttendanceRecord(LocalDateTime.of(2024, 12, 2, 13, 0));
        history.addRecord(record);

        // when
        LocalDate targetDate = LocalDate.of(2024, 12, 2);
        LocalTime modifyTime = LocalTime.of(13, 6);
        history.modifyRecord(targetDate, modifyTime);

        // then
        AttendanceRecord findRecord = history.getRecordByDate(targetDate);
        assertThat(findRecord.getAttendanceStatus()).isEqualTo(AttendanceStatus.LATE);
    }

    @DisplayName("주어진 날짜에 해당하는 기록을 찾는다")
    @Test
    void test_getRecordByDate() {
        // given
        AttendanceHistory history = new AttendanceHistory();
        LocalDate targetDate = LocalDate.of(2024, 12, 3);
        AttendanceRecord record = new AttendanceRecord(LocalDateTime.of(targetDate, LocalTime.of(10, 0)));
        history.addRecord(record);

        // when
        AttendanceRecord findRecord = history.getRecordByDate(targetDate);

        // then
        assertThat(findRecord).isEqualTo(record);
    }

    @DisplayName("해당 크루의 총 결석 횟수가 2회면 경고 대상자 등급을 반환한다.")
    @Test
    void test_getWarning() {
        // given
        AttendanceHistory history = new AttendanceHistory();
        history.addRecord(new AttendanceRecord(LocalDateTime.of(2024, 12, 2, 14, 0)));
        history.addRecord(new AttendanceRecord(LocalDateTime.of(2024, 12, 3, 11, 0)));

        // when
        WarningStatus status = history.getWarningStatus();

        // then
        Assertions.assertThat(status).isEqualTo(WarningStatus.WARNING);
    }

    @DisplayName("출석 내역의 출석 상태 개수를 계산한다.")
    @Test
    void test_countPresentStatus() {
        // given
        AttendanceHistory history = new AttendanceHistory();
        history.addRecord(new AttendanceRecord(LocalDateTime.of(2024, 12, 2, 13, 0)));
        history.addRecord(new AttendanceRecord(LocalDateTime.of(2024, 12, 3, 10, 0)));

        // when
        long count = history.countByAttendanceStatus(AttendanceStatus.PRESENT);

        // then
        assertThat(count).isEqualTo(2);

    }
}