package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceTypeTest {
    @DisplayName("기능: 출석 날짜와 시간에 대한 출석 출결 유형 반환")
    @Test
    void checkAttendanceTypeSafe() {
        LocalDateTime localDateTime = LocalDateTime.of(2025, 2, 20, 10, 0);
        assertThat(AttendanceType.of(localDateTime)).isEqualTo(AttendanceType.SAFE);
    }

    @DisplayName("기능: 출석 날짜와 시간에 대한 지각 출결 유형 반환")
    @Test
    void checkAttendanceTypeLate() {
        LocalDateTime localDateTime = LocalDateTime.of(2025, 2, 20, 10, 10);
        assertThat(AttendanceType.of(localDateTime)).isEqualTo(AttendanceType.LATE);
    }

    @DisplayName("기능: 출석 날짜와 시간에 대한 결석 출결 유형 반환")
    @Test
    void checkAttendanceTypeAbsent() {
        LocalDateTime localDateTime = LocalDateTime.of(2025, 2, 20, 10, 40);
        assertThat(AttendanceType.of(localDateTime)).isEqualTo(AttendanceType.ABSENT);
    }

    @DisplayName("기능: 출석 날짜와 시간에 대한 자유 출결 유형 반환")
    @Test
    void checkAttendanceTypeFree() {
        LocalDateTime localDateTime = LocalDateTime.of(2025, 2, 22, 10, 0);
        assertThat(AttendanceType.of(localDateTime)).isEqualTo(AttendanceType.FREE);
    }
}
