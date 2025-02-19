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

}
