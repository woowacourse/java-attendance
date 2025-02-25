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

    @Test
    void 출석을_변경하고_변경된_값을_반환한다() {
        List<LocalDateTime> dateTimes = List.of(LocalDateTime.of(2024, 12, 13, 9, 59));
        Attendances attendances = generateAttendances(dateTimes);

        LocalDateTime updateDateTime = LocalDateTime.of(2024, 12, 13, 10, 6);
        final var result = attendances.updateAttendance(updateDateTime);

        assertThat(result).isEqualTo(Attendance.from(LocalDateTime.of(2024, 12, 13, 10, 6)));
    }

    @Test
    void 출석_횟수를_계산한다() {
        List<LocalDateTime> dateTimes = List.of(
                LocalDateTime.of(2024, 12, 11, 9, 59),
                LocalDateTime.of(2024, 12, 12, 9, 59),
                LocalDateTime.of(2024, 12, 13, 9, 59)
        );
        Attendances attendances = generateAttendances(dateTimes);
        final var result = attendances.countAttend();

        assertThat(result).isEqualTo(3);
    }

    @Test
    void 지각_횟수를_계산한다() {
        List<LocalDateTime> dateTimes = List.of(
                LocalDateTime.of(2024, 12, 11, 10, 6),
                LocalDateTime.of(2024, 12, 12, 10, 6),
                LocalDateTime.of(2024, 12, 13, 10, 6)
        );
        Attendances attendances = generateAttendances(dateTimes);
        final var result = attendances.countLate();

        assertThat(result).isEqualTo(3);
    }

    @Test
    void 지각_횟수를_계산한다2() {
        List<LocalDateTime> dateTimes = List.of(
                LocalDateTime.of(2024, 12, 11, 10, 6),
                LocalDateTime.of(2024, 12, 12, 10, 6)
        );
        Attendances attendances = generateAttendances(dateTimes);
        final var result = attendances.countLate();

        assertThat(result).isEqualTo(2);
    }

    @Test
    void 결석_횟수를_계산한다() {
        List<LocalDateTime> dateTimes = List.of(
                LocalDateTime.of(2024, 12, 11, 10, 31),
                LocalDateTime.of(2024, 12, 12, 10, 31),
                LocalDateTime.of(2024, 12, 13, 10, 31)
        );
        Attendances attendances = generateAttendances(dateTimes);
        final var result = attendances.countAbsence();

        assertThat(result).isEqualTo(3);
    }

    public static Attendances generateAttendances(List<LocalDateTime> dateTimes) {
        List<Attendance> attendances = new ArrayList<>();
        for (LocalDateTime dateTime : dateTimes) {
            attendances.add(Attendance.from(dateTime));
        }
        return new Attendances(attendances);
    }
}
