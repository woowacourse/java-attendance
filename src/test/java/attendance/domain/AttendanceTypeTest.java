package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class AttendanceTypeTest {
    @DisplayName("정상: 날짜와 시간에 따른 출석 타입 변환 확인")
    @ParameterizedTest
    @CsvSource(value = {"2025-02-24T13:00", "2025-02-28T10:00"}, delimiter = 'T')
    void successExecutionSafe(String date, String time) {
        AttendanceDate attendanceDate = new AttendanceDate(LocalDate.parse(date));
        AttendanceTime attendanceTime = new AttendanceTime(time);
        AttendanceType attendanceType = AttendanceType.of(attendanceDate, attendanceTime);
        assertThat(attendanceType).isEqualTo(AttendanceType.SAFE);
    }

    @DisplayName("정상: 날짜와 시간에 따른 지각 타입 변환 확인")
    @ParameterizedTest
    @CsvSource(value = {"2025-02-24T13:07", "2025-02-28T10:07"}, delimiter = 'T')
    void successExecutionLate(String date, String time) {
        AttendanceDate attendanceDate = new AttendanceDate(LocalDate.parse(date));
        AttendanceTime attendanceTime = new AttendanceTime(time);
        AttendanceType attendanceType = AttendanceType.of(attendanceDate, attendanceTime);
        assertThat(attendanceType).isEqualTo(AttendanceType.LATE);
    }

    @DisplayName("정상: 날짜와 시간에 따른 결석 타입 변환 확인")
    @ParameterizedTest
    @CsvSource(value = {"2025-02-24T13:37", "2025-02-28T10:37"}, delimiter = 'T')
    void successExecutionAbsent(String date, String time) {
        AttendanceDate attendanceDate = new AttendanceDate(LocalDate.parse(date));
        AttendanceTime attendanceTime = new AttendanceTime(time);
        AttendanceType attendanceType = AttendanceType.of(attendanceDate, attendanceTime);
        assertThat(attendanceType).isEqualTo(AttendanceType.ABSENT);
    }

    @DisplayName("정상: 주말 날짜인 경우 자유 타입 변환 확인")
    @ParameterizedTest
    @CsvSource(value = {"2025-02-22T13:00", "2025-02-23T10:00"}, delimiter = 'T')
    void successExecutionFree(String date, String time) {
        AttendanceDate attendanceDate = new AttendanceDate(LocalDate.parse(date));
        AttendanceTime attendanceTime = new AttendanceTime(time);
        AttendanceType attendanceType = AttendanceType.of(attendanceDate, attendanceTime);
        assertThat(attendanceType).isEqualTo(AttendanceType.FREE);
    }
}
