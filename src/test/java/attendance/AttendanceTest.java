package attendance;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;

public class AttendanceTest {

    @Test
    void 날짜가_같다면_true를_반환한다() {
        Attendance attendance = generateAttendance(LocalDateTime.of(2024, 12, 13, 9, 59));
        final var result = attendance.isEqualToDate(LocalDate.of(2024, 12, 13));

        assertThat(result).isTrue();
    }

    @Test
    void 날짜가_다르면_false를_반환한다() {
        Attendance attendance = generateAttendance(LocalDateTime.of(2024, 12, 13, 9, 59));
        final var result = attendance.isEqualToDate(LocalDate.of(2024, 12, 14));

        assertThat(result).isFalse();
    }

    public static Attendance generateAttendance(LocalDateTime dateTime) {
        LocalDate date = dateTime.toLocalDate();
        LocalTime time = dateTime.toLocalTime();
        return new Attendance(new AttendanceDate(date), new AttendanceTime(time));
    }
}
