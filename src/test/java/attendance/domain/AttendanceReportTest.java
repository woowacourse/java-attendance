package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import attendance.TestUtil;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceReportTest {

    @DisplayName("해당 크루의 총 결석 횟수가 2회면 경고 대상자 등급을 반환한다.")
    @Test
    void test_getWarning() {
        // given
        AttendanceHistory history = new AttendanceHistory();
        history.addRecord(TestUtil.createRecord(LocalDateTime.of(2024, 12, 2, 14, 0)));
        history.addRecord(TestUtil.createRecord(LocalDateTime.of(2024, 12, 3, 11, 0)));
        AttendanceReport report = TestUtil.toReport(history, 2, 3);

        // when
        WarningStatus status = report.getWarningStatus();

        // then
        assertThat(status).isEqualTo(WarningStatus.WARNING);
    }

    @DisplayName("출석 내역의 출석 상태 개수를 계산한다.")
    @Test
    void test_countPresentStatus() {
        // given
        AttendanceHistory history = new AttendanceHistory();
        history.addRecord(TestUtil.createRecord(LocalDateTime.of(2024, 12, 2, 13, 0)));
        history.addRecord(TestUtil.createRecord(LocalDateTime.of(2024, 12, 3, 10, 0)));
        AttendanceReport report = TestUtil.toReport(history, 2, 3);

        // when
        long count = report.countPresent();

        // then
        assertThat(count).isEqualTo(2);
    }

    @DisplayName("출석 기록이 없는 날짜는 결석 처리한다.")
    @Test
    void test_countAbsentWithNoAttendance() {
        // given
        AttendanceHistory history = new AttendanceHistory();
        history.addRecord(TestUtil.createRecord(LocalDateTime.of(2024, 12, 2, 13, 0)));
        history.addRecord(TestUtil.createRecord(LocalDateTime.of(2024, 12, 3, 10, 0)));
        AttendanceReport report = TestUtil.toReport(history, 2, 5);

        // when
        long count = report.countAbsent();

        // then
        assertThat(count).isEqualTo(2);
    }

    @DisplayName("출석 기록이 없는 날짜들을 반환한다.")
    @Test
    void test_getNoAttendanceDates() {
        // given
        AttendanceHistory history = new AttendanceHistory();
        history.addRecord(TestUtil.createRecord(LocalDateTime.of(2024, 12, 2, 13, 0)));
        history.addRecord(TestUtil.createRecord(LocalDateTime.of(2024, 12, 3, 10, 0)));
        AttendanceReport report = TestUtil.toReport(history, 2, 5);

        // when
        List<WoowaDate> noAttendanceDates = report.getNoAttendanceDates();

        // then
        assertThat(noAttendanceDates).hasSize(2);
        assertThat(noAttendanceDates.get(0)).isEqualTo(TestUtil.WoowaDatefrom(LocalDate.of(2024, 12, 4)));
        assertThat(noAttendanceDates.get(1)).isEqualTo(TestUtil.WoowaDatefrom(LocalDate.of(2024, 12, 5)));
    }

}