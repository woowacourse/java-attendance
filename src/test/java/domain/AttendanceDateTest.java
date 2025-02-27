package domain;

import domain.attendance.AttendanceDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static java.time.DayOfWeek.*;
import static org.assertj.core.api.Assertions.*;

class AttendanceDateTest {
    @Nested
    class AttendanceDateConstructTest{
        AttendanceDate attendanceDate;
        @BeforeEach
        void setUpLocalDate(){
            attendanceDate = new AttendanceDate(LocalDateTime.of(
                    2025,
                    2,
                    27,
                    11,
                    10)); // 목요일
        }

        @DisplayName("AttendanceDate의 요일을 검사한다.")
        @Test
        void checkDayOfWeek(){
            assertThat(attendanceDate.getDayOfWeek()).isEqualTo(THURSDAY);
        }
    }

    @Nested
    class AttendanceStatusTest{
        AttendanceDate attendanceDate;
        @DisplayName("목요일 10:00의 경우 출석")
        @Test
        void weekOfDayAttendanceTest(){
            AttendanceDate attendanceDate = new AttendanceDate(LocalDateTime.of(
                    2025,
                    2,
                    27,
                    10,
                    0)); // 목요일
            assertThat(attendanceDate.isAttendance()).isTrue();
        }
    }
}