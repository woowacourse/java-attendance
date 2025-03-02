package model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceStatusRecordTest {
    AttendanceStatusRecord attendanceStatusRecord = new AttendanceStatusRecord(List.of(
            LocalDateTime.of(2024, 12, 13, 10, 0),
            LocalDateTime.of(2024, 12, 12, 10, 6),
            LocalDateTime.of(2024, 12, 11, 10, 31),
            LocalDateTime.of(2024, 12, 10, 10, 0),
            LocalDateTime.of(2024, 12, 9, 13, 0)
    ));

    @Test
    @DisplayName("출결 상태에 따른 횟수 가져오기 테스트")
    void findAttendanceStatusCount() {
        long expect = 3;
        long result = attendanceStatusRecord.findAttendanceStatusCount(AttendanceStatus.ATTENDANCE);
        Assertions.assertEquals(expect, result);
    }

    @Test
    void modifyAttendanceStatusRecord() {
        LocalDate testLocalDate = LocalDate.of(2024, 12, 13);
        AttendanceStatus beforeAttendanceStatus = attendanceStatusRecord
                .getAttendanceStatusRecords().get(testLocalDate);
        attendanceStatusRecord.modifyAttendanceStatusRecord(testLocalDate, AttendanceStatus.ABSENT);
        AttendanceStatus afterAttendanceStatus = attendanceStatusRecord
                .getAttendanceStatusRecords().get(testLocalDate);
        assertNotEquals(beforeAttendanceStatus, afterAttendanceStatus);
    }

    @Test
    void registerAttendanceStatusRecord() {
        LocalDate today = LocalDate.of(2024, 12, 14);
        attendanceStatusRecord.registerAttendanceStatusRecord(today, AttendanceStatus.ATTENDANCE);
        AttendanceStatus expect = AttendanceStatus.ATTENDANCE;
        AttendanceStatus result = attendanceStatusRecord.getAttendanceStatusRecords().get(today);
        assertEquals(expect, result);
    }

    @Test
    void putAttendanceStateToAbsent() {
        LocalDate today = LocalDate.of(2024, 12, 14);
        attendanceStatusRecord.putAttendanceStateToAbsent(today);
        AttendanceStatus expect = AttendanceStatus.ABSENT;
        AttendanceStatus result = attendanceStatusRecord.getAttendanceStatusRecords().get(today);
        assertEquals(expect, result);
    }
}