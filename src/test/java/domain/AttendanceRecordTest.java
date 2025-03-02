package domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.format.DateTimeParseException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class AttendanceRecordTest {
    @Nested
    @DisplayName("출석 기록 생성 테스트")
    class ConstructorTest {
        @ParameterizedTest
        @DisplayName("날짜와 시간 문자열로 출석 기록을 생성한다")
        @CsvSource(value = {"9, 10:00", "10, 10:00", "30, 10:00", "31, 10:00"})
        void should_create_attendanceRecord_by_date_and_time(String date, String time) {
            // when & then
            AttendanceRecord attendanceRecord = AttendanceRecord.of(date, time);
        }

        @ParameterizedTest
        @DisplayName("시간 문자열로 출석 기록을 생성한다")
        @CsvSource(value = {"00:00", "10:00", "11:00", "12:00"})
        void should_create_attendanceRecord_by_time(String time) {
            // when & then
            AttendanceRecord attendanceRecord = AttendanceRecord.timeOf(time);
        }

        @ParameterizedTest
        @DisplayName("잘못된 날짜와 시간 문자열로 출석 기록을 생성하면 예외가 발생한다")
        @CsvSource(value = {"0, 10:00", "32, 10:00", "a, 10:00", "a1, 10:00", "1, 0000", "1, 0:00", "1, 00:0",
                "1, aa:bb"})
        void should_throw_exception_when_invalid_date_or_time(String date, String time) {
            // when & then
            assertThatThrownBy(() -> {
                AttendanceRecord.of(date, time);
            }).isInstanceOf(DateTimeParseException.class);
        }


        @ParameterizedTest
        @DisplayName("XX:XX 형식이 아닌 시간 문자열로 출석 기록을 생성하면 예외가 발생한다")
        @CsvSource(value = {"0000", "0:00", "00:0", "aa:bb"})
        void should_throw_exception_when_invalid_time(String time) {
            // when & then
            assertThatThrownBy(() -> {
                AttendanceRecord.timeOf(time);
            }).isInstanceOf(DateTimeParseException.class);
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
