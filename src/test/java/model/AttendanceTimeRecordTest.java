package model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceTimeRecordTest {
    AttendanceTimeRecord attendanceTimeRecord = new AttendanceTimeRecord(List.of(
            LocalDateTime.of(2024, 12, 13, 10, 0),
            LocalDateTime.of(2024, 12, 12, 10, 6),
            LocalDateTime.of(2024, 12, 11, 10, 31),
            LocalDateTime.of(2024, 12, 10, 10, 0),
            LocalDateTime.of(2024, 12, 9, 13, 0)
    ));

    @Test
    @DisplayName("테스트 날짜가 저장되어있지 않는지 테스트")
    void 테스트_날짜가_존재하지_않는지_테스트() {
        LocalDate testDate = LocalDate.of(2024,12,20);
        boolean result = attendanceTimeRecord.checkAttendanceRecordByLocalDate(testDate);
        assertFalse(result);
    }

    @Test
    @DisplayName("테스트 날짜가 존재하는지 테스트")
    void 테스트_날짜가_존재하는지_테스트() {
        LocalDate testDate = LocalDate.of(2024,12,13);
        boolean result = attendanceTimeRecord.checkAttendanceRecordByLocalDate(testDate);
        assertTrue(result);
    }
}