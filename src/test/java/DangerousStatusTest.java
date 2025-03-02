import static org.assertj.core.api.Assertions.assertThat;

import domain.AttendanceRecord;
import domain.AttendanceStatus;
import domain.DangerousStatus;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

public class DangerousStatusTest {

    @Test
    void 제적_대상자인지_확인한다() {
        AttendanceRecord attendanceRecord = new AttendanceRecord(() -> LocalDate.of(2024, 12, 9));

        DangerousStatus dangerousStatus = DangerousStatus.of(
                attendanceRecord.countStatus(AttendanceStatus.LATE),
                attendanceRecord.countStatus(AttendanceStatus.ABSENCE));

        assertThat(dangerousStatus).isEqualTo(DangerousStatus.DISMISSAL);
    }

    @Test
    void 면담_대상자인지_확인한다() {
        AttendanceRecord attendanceRecord = new AttendanceRecord(() -> LocalDate.of(2024, 12, 4));

        DangerousStatus dangerousStatus = DangerousStatus.of(
                attendanceRecord.countStatus(AttendanceStatus.LATE),
                attendanceRecord.countStatus(AttendanceStatus.ABSENCE));

        assertThat(dangerousStatus).isEqualTo(DangerousStatus.INTERVIEW);
    }

    @Test
    void 경고_대상자인지_확인한다() {
        AttendanceRecord attendanceRecord = new AttendanceRecord(() -> LocalDate.of(2024, 12, 3));

        DangerousStatus dangerousStatus = DangerousStatus.of(
                attendanceRecord.countStatus(AttendanceStatus.LATE),
                attendanceRecord.countStatus(AttendanceStatus.ABSENCE));

        assertThat(dangerousStatus).isEqualTo(DangerousStatus.WARNING);
    }

    @Test
    void 모범인지_확인한다() {
        AttendanceRecord attendanceRecord = new AttendanceRecord(() -> LocalDate.of(2024, 12, 2));

        DangerousStatus dangerousStatus = DangerousStatus.of(
                attendanceRecord.countStatus(AttendanceStatus.LATE),
                attendanceRecord.countStatus(AttendanceStatus.ABSENCE));

        assertThat(dangerousStatus).isEqualTo(DangerousStatus.GOOD);
    }


}
