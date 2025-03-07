package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceStatusCountTest {
    @Test
    @DisplayName("출석 상태 개수 정확히 계산한다")
    void should_return_correct_attendance_status_count() {
        // given
        AttendanceStatusCount attendanceStatusCount = new AttendanceStatusCount(
                Map.of(AttendanceStatus.ATTENDANT, 1L,
                        AttendanceStatus.LATE, 2L,
                        AttendanceStatus.ABSENT, 3L));

        // when
        long attendantCount = attendanceStatusCount.getCount(AttendanceStatus.ATTENDANT);
        long lateCount = attendanceStatusCount.getCount(AttendanceStatus.LATE);
        long absentCount = attendanceStatusCount.getCount(AttendanceStatus.ABSENT);

        // then
        assertAll(
                () -> assertThat(attendantCount).isEqualTo(1L),
                () -> assertThat(lateCount).isEqualTo(2L),
                () -> assertThat(absentCount).isEqualTo(3L)
        );
    }
}
