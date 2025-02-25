package attendance;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class AttendanceBookTest {

    @Test
    void 닉네임이_존재하지_않으면_예외를_던진다() {
        List<LocalDateTime> attendances = List.of(LocalDateTime.of(2024, 12, 13, 9, 59));
        AttendanceBook attendanceBook = new AttendanceBook(Map.of("훌라", generateAttendances(attendances)));

        assertThatThrownBy(() -> attendanceBook.attend("모루", LocalDateTime.of(2024, 12, 16, 12, 59)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 닉네임과_시간을_입력하면_출석한다() {
        List<LocalDateTime> attendances = List.of(LocalDateTime.of(2024, 12, 13, 9, 59));
        Map<String, Attendances> crewAttendances = Map.of("훌라", generateAttendances(attendances));
        AttendanceBook attendanceBook = new AttendanceBook(new HashMap<>(crewAttendances));

        final var result = attendanceBook.attend("훌라", LocalDateTime.of(2024, 12, 16, 12, 59));

        assertThat(result).isEqualTo(LocalDateTime.of(2024, 12, 16, 12, 59));
    }

    @Test
    void 이미_출석했다면_예외를_던진다() {
        List<LocalDateTime> attendances = List.of(LocalDateTime.of(2024, 12, 13, 9, 59));
        AttendanceBook attendanceBook = new AttendanceBook(Map.of("훌라", generateAttendances(attendances)));

        assertThatThrownBy(() -> attendanceBook.attend("훌라", LocalDateTime.of(2024, 12, 13, 9, 59)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    private static Attendances generateAttendances(List<LocalDateTime> dateTimes) {
        Map<AttendanceDate, AttendanceTime> attendances = new HashMap<>();
        for (LocalDateTime dateTime : dateTimes) {
            attendances.put(new AttendanceDate(dateTime.toLocalDate()), new AttendanceTime(dateTime.toLocalTime()));
        }
        return new Attendances(attendances);
    }
}
