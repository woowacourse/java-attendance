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

    @Test
    void 출석기록에서_전체지각횟수를_계산한다() {
        //given
        AttendanceHistory attendanceHistory = new AttendanceHistory();

        attendanceHistory.addAttendanceDetail(TestUtil.creatAttendanceDetail(2024, 12, 2, 13, 10));
        attendanceHistory.addAttendanceDetail(TestUtil.creatAttendanceDetail(2024, 12, 3, 10, 10));
        attendanceHistory.addAttendanceDetail(TestUtil.creatAttendanceDetail(2024, 12, 4, 10, 10));

        AttendanceReport report = new AttendanceReport(TestUtil.getClock(), attendanceHistory,
                TestUtil.getTrainingStartDate());
        //then
        assertThat(report.calculateLateCount()).isEqualTo(3);
    }

    @Test
    void 출석기록에서_전체결석횟수를_계산한다() {
        //given
        AttendanceHistory attendanceHistory = new AttendanceHistory();

        attendanceHistory.addAttendanceDetail(TestUtil.creatAttendanceDetail(2024, 12, 2, 13, 31));
        attendanceHistory.addAttendanceDetail(TestUtil.creatAttendanceDetail(2024, 12, 3, 10, 31));
        attendanceHistory.addAttendanceDetail(TestUtil.creatAttendanceDetail(2024, 12, 4, 10, 31));

        AttendanceReport report = new AttendanceReport(TestUtil.clockOf(4), attendanceHistory,
                TestUtil.getTrainingStartDate());
        //then
        assertThat(report.calculateAbsenceCount()).isEqualTo(3);
    }

    @Test
    void 출석기록이_없는경우_결석으로_기록한다() {
        //given
        AttendanceHistory attendanceHistory = new AttendanceHistory();

        attendanceHistory.addAttendanceDetail(TestUtil.creatAttendanceDetail(2024, 12, 2, 13, 31));
        attendanceHistory.addAttendanceDetail(TestUtil.creatAttendanceDetail(2024, 12, 3, 10, 31));
        attendanceHistory.addAttendanceDetail(TestUtil.creatAttendanceDetail(2024, 12, 4, 10, 31));

        AttendanceReport report = new AttendanceReport(TestUtil.clockOf(5), attendanceHistory,
                TestUtil.getTrainingStartDate());
        //then
        assertThat(report.calculateAbsenceCount()).isEqualTo(4);
    }

}
