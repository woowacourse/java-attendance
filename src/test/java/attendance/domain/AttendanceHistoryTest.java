package attendance.domain;

import static attendance.error.ErrorMessage.ERROR_CHECK_ATTENDANCE_AGAIN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import attendance.TestUtil;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceHistoryTest {

    @DisplayName("출석 내역에 하나의 출석 기록을 추가한다.")
    @Test
    void test_addCrewRecord() {
        // given
        AttendanceHistory history = new AttendanceHistory();
        AttendanceRecord record = TestUtil.createRecord(LocalDateTime.of(2024, 12, 2, 13, 0));

        // when
        history.addRecord(record);

        // then
        assertThat(history.getRecords()).hasSize(1);
        assertThat(history.getRecords().contains(record)).isTrue();
    }

    @DisplayName("해당 날짜에 이미 출석한 경우 예외가 발생한다.")
    @Test
    void test_addCrewRecord_sameDate() {
        // given
        AttendanceHistory history = new AttendanceHistory();
        history.addRecord(TestUtil.createRecord(LocalDateTime.of(2024, 12, 2, 13, 0)));

        // when & then
        assertThatThrownBy(() -> history.addRecord(
                TestUtil.createRecord(LocalDateTime.of(2024, 12, 2, 13, 0))
        ))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ERROR_CHECK_ATTENDANCE_AGAIN);
    }

    @DisplayName("해당 크루의 특정 날짜의 기록을 지각으로 수정한다.")
    @Test
    void test_modifyCrewRecord() {
        // given
        AttendanceHistory history = new AttendanceHistory();
        AttendanceRecord record = TestUtil.createRecord(LocalDateTime.of(2024, 12, 2, 13, 0));
        history.addRecord(record);

        // when
        LocalDate targetDate = LocalDate.of(2024, 12, 2);
        LocalTime modifyTime = LocalTime.of(13, 6);
        history.modifyRecord(TestUtil.WoowaDatefrom(targetDate), modifyTime);

        // then
        AttendanceRecord findRecord = history.getRecordByDate(TestUtil.WoowaDatefrom(targetDate));
        assertThat(findRecord.getAttendanceStatus()).isEqualTo(AttendanceStatus.LATE);
    }

    @DisplayName("주어진 날짜에 해당하는 기록을 찾는다")
    @Test
    void test_getRecordByDate() {
        // given
        AttendanceHistory history = new AttendanceHistory();
        LocalDate targetDate = LocalDate.of(2024, 12, 3);

        AttendanceRecord record = TestUtil.createRecord(LocalDateTime.of(targetDate, LocalTime.of(10, 0)));
        history.addRecord(record);

        // when
        AttendanceRecord findRecord = history.getRecordByDate(TestUtil.WoowaDatefrom(targetDate));

        // then
        assertThat(findRecord).isEqualTo(record);
    }

}