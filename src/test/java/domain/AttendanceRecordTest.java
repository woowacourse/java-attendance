package domain;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class AttendanceRecordTest {
    @Nested
    @DisplayName("출석 기록 생성 테스트")
    class ConstructorTest {
        @Test
        @DisplayName("날짜와 시간 문자열로 출석 기록을 생성한다")
        void should_create_attendanceRecord_by_date_and_time() {
            // given
            String date = "11";
            String time = "10:00";

            // when & then
            AttendanceRecord attendanceRecord = AttendanceRecord.of(date, time);
        }

        @Test
        @DisplayName("시간 문자열로 출석 기록을 생성한다")
        void should_create_attendanceRecord_by_time() {
            // given
            String time = "10:00";

            // when & then
            AttendanceRecord attendanceRecord = AttendanceRecord.timeOf(time);
        }
    }

    @Test
    @DisplayName("출석 기록이 같은 날인지 확인한다")
    void should_return_true_when_same_date() {
        // given
        String date = "11";
        String time = "10:00";
        String otherTime = "11:00";
        AttendanceRecord attendanceRecord = AttendanceRecord.of(date, time);
        AttendanceRecord otherAttendanceRecord = AttendanceRecord.of(date, otherTime);

        // when
        boolean result = attendanceRecord.isSameDate(otherAttendanceRecord);

        // then
        assertTrue(result);
    }
}
