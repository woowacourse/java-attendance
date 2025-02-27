import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThatNoException;

class AttendanceRecordsTest {
    @DisplayName("새 출석 기록을 저장할 수 있다.")
    @Test
    void addTest() {
        // given
        AttendanceRecords attendanceRecords = new AttendanceRecords();
        LocalDateTime dateTime = LocalDateTime.of(2024, 12, 2, 13, 10);
        AttendanceRecord attendanceRecord = new AttendanceRecord(dateTime);

        // then
        assertThatNoException().isThrownBy(() -> attendanceRecords.add(attendanceRecord));
    }
}
