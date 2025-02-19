import domain.Attendance;
import domain.AttendanceDto;
import domain.Day;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

public class AttendanceUpdateTest {

    @Test
    void 수정날짜와_시간_입력시_기존_출석시간이_변경된다() {

        final var date = LocalDate.of(2024, 12, 3);

        Day day = new Day(date);
        Attendance attendance = new Attendance(day, LocalTime.of(10, 7));

        final var modifiedTime = LocalTime.of(9, 58);

        attendance.setAttendanceTime(modifiedTime);

        AttendanceDto attendanceDto = attendance.toDto();

        assertThat(attendanceDto.getAttendanceTime()).isEqualTo(LocalTime.of(9, 58));
    }

    @Test
    void 수정된_출석시간에_따라_출석상태가_변경된다_지각_출석() {

        final var date = LocalDate.of(2024, 12, 3);

        Day day = new Day(date);
        Attendance attendance = new Attendance(day, LocalTime.of(10, 7));

        final var modifiedTime = LocalTime.of(9, 58);

        attendance.setAttendanceTime(modifiedTime);

        AttendanceDto attendanceDto = attendance.toDto();

        assertThat(attendanceDto.getLate()).isEqualTo(false);
    }

    @Test
    void 수정된_출석시간에_따라_출석상태가_변경된다_지각_결석() {

        final var date = LocalDate.of(2024, 12, 3);

        Day day = new Day(date);
        Attendance attendance = new Attendance(day, LocalTime.of(10, 7));

        final var modifiedTime = LocalTime.of(10, 31);

        attendance.setAttendanceTime(modifiedTime);

        AttendanceDto attendanceDto = attendance.toDto();

        assertThat(attendanceDto.getAbsent()).isEqualTo(true);
    }

    @Test
    void 수정된_출석시간에_따라_출석상태가_변경된다_출석_지각() {

        final var date = LocalDate.of(2024, 12, 3);

        Day day = new Day(date);
        Attendance attendance = new Attendance(day, LocalTime.of(10, 4));

        final var modifiedTime = LocalTime.of(10, 7);

        attendance.setAttendanceTime(modifiedTime);

        AttendanceDto attendanceDto = attendance.toDto();

        assertThat(attendanceDto.getLate()).isEqualTo(true);
    }

    @Test
    void 수정된_출석시간에_따라_출석상태가_변경된다_출석_결석() {

        final var date = LocalDate.of(2024, 12, 3);

        Day day = new Day(date);
        Attendance attendance = new Attendance(day, LocalTime.of(10, 4));

        final var modifiedTime = LocalTime.of(10, 31);

        attendance.setAttendanceTime(modifiedTime);

        AttendanceDto attendanceDto = attendance.toDto();

        assertThat(attendanceDto.getAbsent()).isEqualTo(true);
    }
}
