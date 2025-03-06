import domain.AttendanceStatus;
import domain.AttendanceStatusCounter;
import java.util.HashMap;
import java.util.Map;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceStatusCounterTest {

    @DisplayName("원하는 출석 상태의 누적 횟수를 조회한다")
    @Test
    void retrieveAttendanceCount() {
        // given
        Map<AttendanceStatus, Integer> attendanceStatusCounts = new HashMap<>(
                Map.of(
                        AttendanceStatus.ATTENDANCE, 1,
                        AttendanceStatus.LATE, 2,
                        AttendanceStatus.ABSENCE, 3
                )
        );
        AttendanceStatusCounter attendanceStatusCounter = AttendanceStatusCounter.create(attendanceStatusCounts);

        // when
        int attendanceCount = attendanceStatusCounter.retrieveAttendanceStatusCount(AttendanceStatus.ATTENDANCE);
        int lateCount = attendanceStatusCounter.retrieveAttendanceStatusCount(AttendanceStatus.LATE);
        int absenceCount = attendanceStatusCounter.retrieveAttendanceStatusCount(AttendanceStatus.ABSENCE);

        //then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(attendanceCount).isEqualTo(1);
            softly.assertThat(lateCount).isEqualTo(2);
            softly.assertThat(absenceCount).isEqualTo(3);
        });
    }
}
