package attendance;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;

public class AttendanceTest {

    @Test
    void 날짜가_같다면_true를_반환한다() {
        Attendance attendance = Attendance.from(LocalDateTime.of(2024, 12, 13, 9, 59));
        final var result = attendance.isEqualToDate(LocalDate.of(2024, 12, 13));

        assertThat(result).isTrue();
    }

    @Test
    void 날짜가_다르면_false를_반환한다() {
        Attendance attendance = Attendance.from(LocalDateTime.of(2024, 12, 13, 9, 59));
        final var result = attendance.isEqualToDate(LocalDate.of(2024, 12, 14));

        assertThat(result).isFalse();
    }

    @Test
    void 시간을_입력_받아_출석을_수정한다() {
        Attendance before = Attendance.from(LocalDateTime.of(2024, 12, 13, 9, 59));
        LocalTime time = LocalTime.of(10, 6);

        before.updateTime(time);
        Attendance updated = Attendance.from(LocalDateTime.of(2024, 12, 13, 10, 6));

        assertThat(before).isEqualTo(updated);
    }
}
