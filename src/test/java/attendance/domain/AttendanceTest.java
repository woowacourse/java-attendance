package attendance.domain;

import static java.time.LocalDate.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class AttendanceTest {

    @Test
    void 출석_시간을_알려주면_출석이_생성된다() {
        assertDoesNotThrow(() -> new Attendance(
                new AttendanceDate(LocalDate.of(2025, 2, 18)),
                new AttendanceTime(LocalTime.of(10, 0)))
        );
    }

    @Test
    void 휴일은_출석할_수_없다() {
        assertThatThrownBy(() -> new Attendance(
                new AttendanceDate(LocalDate.of(2025, 2, 16)),
                new AttendanceTime(LocalTime.of(10, 0))
        )).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("2월 16일 일요일은 등교일이 아닙니다.");
    }

    @CsvSource(value = {"7,59", "23,1"})
    @ParameterizedTest
    void 캠퍼스_운영_시간이_아니면_출석할_수_없다(int hour, int minute) {
        assertThatThrownBy(() -> new Attendance(
                new AttendanceDate(LocalDate.of(2025, 2, 18)),
                new AttendanceTime(LocalTime.of(hour, minute))
        )).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("%02d:%02d은 캠퍼스 운영 시간이 아닙니다.", hour, minute);
    }

    @Test
    void 변경_시간을_알려주면_출석_시간을_수정한다() {
        // Given
        AttendanceDate attendanceDate = new AttendanceDate(of(2025, 2, 18));
        Attendance attendance = new Attendance(
                attendanceDate,
                new AttendanceTime(LocalTime.of(9, 0)));

        // When
        Attendance changedAttendance = attendance.changeAttendanceTime(LocalTime.of(10, 30));

        // Then
        assertThat(changedAttendance)
                .isEqualTo(new Attendance(attendanceDate, new AttendanceTime(LocalTime.of(10, 30))));
    }

    @CsvSource(value = {"18,true", "19,false"})
    @ParameterizedTest
    void 날짜를_알려주면_같은_날짜의_출석인지_알려준다(int day, boolean expected) {
        Attendance attendance = new Attendance(
                new AttendanceDate(of(2025, 2, 18)),
                new AttendanceTime(LocalTime.of(9, 0)));

        assertThat(attendance.isSameDate(of(2025, 2, day))).isEqualTo(expected);
    }

    @CsvSource(value = {"5,OK", "30,LATE", "31,ABSENT"})
    @ParameterizedTest
    void 출석_시간을_통해_출석_상태를_알려준다(int minute, AttendanceStatus expected) {
        Attendance attendance = new Attendance(
                new AttendanceDate(of(2025, 2, 20)),
                new AttendanceTime(LocalTime.of(10, minute)));

        assertThat(attendance.calculateStatus()).isEqualTo(expected);
    }

}
