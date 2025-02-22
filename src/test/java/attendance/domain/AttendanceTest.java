package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class AttendanceTest {

    @Test
    void 주말이나_휴일이아니면_출석객체를_생성할수있다() {
        LocalTime time = LocalTime.of(13, 4);

        LocalDate monday = LocalDate.of(2024, 12, 9);
        LocalDateTime localDateTime = LocalDateTime.of(monday, time);

        assertThatCode(() -> Attendance.of(localDateTime))
            .doesNotThrowAnyException();
    }

    @Test
    void 등교날짜가_주말이면_예외가_발생한다() {
        LocalDateTime saturday = LocalDateTime.of(2024,12,14,10,0);

        assertThatThrownBy(() -> Attendance.of(saturday))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 등교날짜가_공휴일이면_예외가_발생한다() {
        LocalDateTime thursday_christmas = LocalDateTime.of(2025,12,25,10,0);

        assertThatThrownBy(() -> Attendance.of(thursday_christmas))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest(name = "[전] {0} -> [후] {1} : {2}")
    @CsvSource({
        "2025-02-17T13:31:00,2025-02-17T13:00:00,CHECKIN",
        "2025-02-18T10:31:00,2025-02-18T10:06:00,LATE",
        "2025-02-19T10:06:00,2025-02-19T10:00:00,CHECKIN",
        "2025-02-20T10:06:00,2025-02-20T10:31:00,ABSENCE",
        "2025-02-21T10:00:00,2025-02-21T10:06:00,LATE",
    })
    void 출석시간을_수정할수있고_수정된_시간에따라_상태가_변경된다(LocalDateTime before, LocalDateTime after, AttendanceStatus expected) {
        Attendance attendance = Attendance.of(before);
        attendance.modify(after);

        assertThat(attendance.getAttendedTime()).isEqualTo(after);
        assertThat(attendance.getStatus()).isEqualTo(expected);
    }
}
