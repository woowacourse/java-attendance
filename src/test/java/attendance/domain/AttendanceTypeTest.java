package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceTypeTest {
    @DisplayName("기능: 출석 날짜와 시간에 대한 알맞은 AttendanceType 반환 확인")
    @Test
    void createAttendanceTypeOfLocalDateTime() {
        List<LocalDateTime> localDateTime =
                List.of(
                        LocalDateTime.of(
                                2025, 2, 20, 9, 0, 0
                        ),
                        LocalDateTime.of(
                                2025, 2, 20, 10, 10, 0
                        ),
                        LocalDateTime.of(
                                2025, 2, 20, 10, 40, 0
                        )
                );

        assertThat(AttendanceType.of(localDateTime.get(0))).isEqualTo(AttendanceType.SAFE);
        assertThat(AttendanceType.of(localDateTime.get(1))).isEqualTo(AttendanceType.LATE);
        assertThat(AttendanceType.of(localDateTime.get(2))).isEqualTo(AttendanceType.ABSENT);
    }
}
