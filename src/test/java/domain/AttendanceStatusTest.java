package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.Current;

class AttendanceStatusTest {
    @ParameterizedTest
    @DisplayName("출석 기록으로 `출석`의 출석 상태를 계산한다")
    @CsvSource(value = {"9, 08:00", "9, 10:00", "9, 13:00", "9, 13:05", "10, 08:00", "10, 10:00", "10, 10:05"})
    void should_return_ATTENDANT_attendance_status_by_attendance_record(int date, LocalTime time) {
        // given
        AttendanceRecord attendanceRecord = new AttendanceRecord(Current.getToday()
                .withDayOfMonth(date), time);

        // when
        AttendanceStatus result = AttendanceStatus.calculateAttendanceStatus(attendanceRecord);

        // then
        assertThat(result).isEqualTo(AttendanceStatus.ATTENDANT);
    }

    @ParameterizedTest
    @DisplayName("출석 기록으로 `지각`의 출석 상태를 계산한다")
    @CsvSource(value = {"9, 13:06", "9, 13:30", "10, 10:06", "10, 10:30"})
    void should_return_LATE_attendance_status_by_attendance_record(int date, LocalTime time) {
        // given
        AttendanceRecord attendanceRecord = new AttendanceRecord(Current.getToday()
                .withDayOfMonth(date), time);

        // when
        AttendanceStatus result = AttendanceStatus.calculateAttendanceStatus(attendanceRecord);

        // then
        assertThat(result).isEqualTo(AttendanceStatus.LATE);
    }

    @ParameterizedTest
    @DisplayName("출석 기록으로 `결석`의 출석 상태를 계산한다")
    @CsvSource(value = {"9, 13:31", "9, 14:00", "10, 10:31", "10, 11:00"})
    void should_return_ABSENT_attendance_status_by_attendance_record(int date, LocalTime time) {
        // given
        AttendanceRecord attendanceRecord = new AttendanceRecord(Current.getToday()
                .withDayOfMonth(date), time);

        // when
        AttendanceStatus result = AttendanceStatus.calculateAttendanceStatus(attendanceRecord);

        // then
        assertThat(result).isEqualTo(AttendanceStatus.ABSENT);
    }

    @ParameterizedTest
    @DisplayName("시간 값이 없는 출석 기록은 `결석`의 출석 상태를 계산한다")
    @CsvSource(value = {"9", "10", "11"})
    void should_return_ABSENT_attendance_status_by_attendance_record(Integer date) {
        // given
        AttendanceRecord attendanceRecord = AttendanceRecord.dateOf(date);

        // when
        AttendanceStatus result = AttendanceStatus.calculateAttendanceStatus(attendanceRecord);

        // then
        assertThat(result).isEqualTo(AttendanceStatus.ABSENT);
    }
}
