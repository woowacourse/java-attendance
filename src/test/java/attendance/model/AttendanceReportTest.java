package attendance.model;

import static org.assertj.core.api.Assertions.assertThat;

import attendance.TestUtil;
import org.junit.jupiter.api.Test;

class AttendanceReportTest {

    @Test
    void 출석기록에서_전체출석횟수를_계산한다() {
        //given
        AttendanceHistory attendanceHistory = new AttendanceHistory();

        attendanceHistory.addAttendanceDetail(TestUtil.creatAttendanceDetail(2024, 12, 2, 13, 0));
        attendanceHistory.addAttendanceDetail(TestUtil.creatAttendanceDetail(2024, 12, 3, 9, 58));
        attendanceHistory.addAttendanceDetail(TestUtil.creatAttendanceDetail(2024, 12, 4, 10, 2));

        AttendanceReport report = new AttendanceReport(TestUtil.getClock(), attendanceHistory,
                TestUtil.getTrainingStartDate());
        //then
        assertThat(report.calculateAttendanceCount()).isEqualTo(3);
    }
}