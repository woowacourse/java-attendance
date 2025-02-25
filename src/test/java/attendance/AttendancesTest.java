package attendance;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

public class AttendancesTest {

    @Test
    void 해당_날짜_기록이_존재하면_true를_반환한다() {
        List<LocalDateTime> dateTimes = List.of(LocalDateTime.of(2024, 12, 13, 9, 59));
        Attendances attendances = generateAttendances(dateTimes);
        LocalDate date = LocalDate.of(2024, 12, 13);
        final var result = attendances.existsByDate(date);

        assertThat(result).isTrue();
    }

    @Test
    void 해당_날짜_기록이_존재하면_true를_반환한다2() {
        List<LocalDateTime> dateTimes = List.of(LocalDateTime.of(2024, 12, 12, 9, 59));
        Attendances attendances = generateAttendances(dateTimes);
        LocalDate date = LocalDate.of(2024, 12, 12);
        final var result = attendances.existsByDate(date);

        assertThat(result).isTrue();
    }

    @Test
    void 해당_날짜_기록이_존재하지_않으면_false를_반환한다() {
        List<LocalDateTime> dateTimes = List.of(LocalDateTime.of(2024, 12, 13, 9, 59));
        Attendances attendances = generateAttendances(dateTimes);
        LocalDate date = LocalDate.of(2024, 12, 12);
        final var result = attendances.existsByDate(date);

        assertThat(result).isFalse();
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
