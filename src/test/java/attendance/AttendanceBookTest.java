package attendance;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

        LocalDateTime dateTime = LocalDateTime.of(2024, 12, 16, 12, 59);
        AttendanceDate attendanceDate = new AttendanceDate(dateTime.toLocalDate());
        AttendanceTime attendanceTime = new AttendanceTime(dateTime.toLocalTime());
        Attendance comparison = new Attendance(attendanceDate, attendanceTime);

        assertThat(result).isEqualTo(comparison);
    }

    @Test
    void 이미_출석했다면_예외를_던진다() {
        List<LocalDateTime> attendances = List.of(LocalDateTime.of(2024, 12, 13, 9, 59));
        AttendanceBook attendanceBook = new AttendanceBook(Map.of("훌라", generateAttendances(attendances)));

        assertThatThrownBy(() -> attendanceBook.attend("훌라", LocalDateTime.of(2024, 12, 13, 9, 59)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    private static Attendances generateAttendances(List<LocalDateTime> dateTimes) {
        List<Attendance> attendances = new ArrayList<>();
        for (LocalDateTime dateTime : dateTimes) {
            Attendance attendance = new Attendance(new AttendanceDate(dateTime.toLocalDate()),
                    new AttendanceTime(dateTime.toLocalTime()));
            attendances.add(attendance);
        }
        return new Attendances(attendances);
    }
}
