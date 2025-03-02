package domain;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceRecordTest {
    @Test
    @DisplayName("출석 기록이 같은 날인지 확인한다")
    void should_return_true_when_same_date() {
        // given
        LocalDate date = LocalDate.parse("2024-12-11");
        LocalTime time = LocalTime.parse("10:00");
        LocalTime anotherTime = LocalTime.parse("11:00");
        AttendanceRecord attendanceRecord = new AttendanceRecord(date, time);
        AttendanceRecord targetAttendanceRecord = new AttendanceRecord(date, anotherTime);

        // when
        boolean result = attendanceRecord.isSameDate(targetAttendanceRecord);

        // then
        assertTrue(result);
    }
}
