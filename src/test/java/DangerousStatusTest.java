import static domain.Dangerous.DISMISSAL;
import static org.assertj.core.api.Assertions.assertThat;

import domain.AttendTime;
import domain.AttendanceHistory;
import domain.DangerousStatus;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DangerousStatusTest {

    @DisplayName("결석 상태를 반환한다.")
    @Test
    void test1() {
        List<AttendTime> attendTimes = new ArrayList<>();
        attendTimes.add(new AttendTime("2024-12-13 09:59"));
        AttendanceHistory attendanceHistory = new AttendanceHistory(attendTimes);

        DangerousStatus dangerousStatus = new DangerousStatus(attendanceHistory.calculateOnTime(),
                attendanceHistory.calculateLate(), attendanceHistory.calculateAbsent());
        assertThat(dangerousStatus.getStatus()).isEqualTo(DISMISSAL.getStatus());
    }
}
