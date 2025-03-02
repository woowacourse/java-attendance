package attendance.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import org.junit.jupiter.api.Test;

public class AttendancesTest {

    @Test
    void 출석을_변경하고_변경된_값을_반환한다() {
        List<LocalDateTime> dateTimes = List.of(LocalDateTime.of(2024, 12, 13, 9, 59));
        Attendances attendances = generateAttendances(dateTimes);

        LocalDateTime updateDateTime = LocalDateTime.of(2024, 12, 13, 10, 6);
        final var result = attendances.updateAttendance(updateDateTime);

        assertThat(result.getDateTime()).isEqualTo(LocalDateTime.of(2024, 12, 13, 10, 6));
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

    @Test
    void 결석_2회이면_경고대상자이다() {
        List<LocalDateTime> dateTimes = List.of(
                LocalDateTime.of(2024, 12, 11, 10, 31),
                LocalDateTime.of(2024, 12, 12, 10, 31)
        );
        Attendances attendances = generateAttendances(dateTimes);

        final var result = attendances.calculatePenalty();

        assertThat(result).isEqualTo(AttendancePenalty.CAUTION);
    }

    @Test
    void 결석_3회이면_면담대상자이다() {
        List<LocalDateTime> dateTimes = List.of(
                LocalDateTime.of(2024, 12, 11, 10, 31),
                LocalDateTime.of(2024, 12, 12, 10, 31),
                LocalDateTime.of(2024, 12, 13, 10, 31)
        );
        Attendances attendances = generateAttendances(dateTimes);
        final var result = attendances.calculatePenalty();

        assertThat(result).isEqualTo(AttendancePenalty.INTERVIEW);
    }

    @Test
    void 결석_5회_초과이면_제적대상자이다() {
        List<LocalDateTime> dateTimes = List.of(
                LocalDateTime.of(2024, 12, 10, 10, 31),
                LocalDateTime.of(2024, 12, 11, 10, 31),
                LocalDateTime.of(2024, 12, 12, 10, 31),
                LocalDateTime.of(2024, 12, 13, 10, 31),
                LocalDateTime.of(2024, 12, 17, 10, 31),
                LocalDateTime.of(2024, 12, 18, 10, 31)
        );
        Attendances attendances = generateAttendances(dateTimes);

        final var result = attendances.calculatePenalty();

        assertThat(result).isEqualTo(AttendancePenalty.EXPULSION);
    }

    @Test
    void 결석_2회_미만이면_아무_대상자도_아니다() {
        List<LocalDateTime> dateTimes = List.of(
                LocalDateTime.of(2024, 12, 10, 10, 31)
        );
        Attendances attendances = generateAttendances(dateTimes);

        final var result = attendances.calculatePenalty();

        assertThat(result).isEqualTo(AttendancePenalty.NONE);
    }

    @Test
    void 지각_3회도_결석_1회로_간주한다() {
        List<LocalDateTime> dateTimes = List.of(
                LocalDateTime.of(2024, 12, 10, 10, 6),
                LocalDateTime.of(2024, 12, 11, 10, 6),
                LocalDateTime.of(2024, 12, 12, 10, 6),
                LocalDateTime.of(2024, 12, 13, 10, 31),
                LocalDateTime.of(2024, 12, 17, 10, 31)
        );
        Attendances attendances = generateAttendances(dateTimes);

        final var result = attendances.calculatePenalty();

        assertThat(result).isEqualTo(AttendancePenalty.INTERVIEW);
    }

    public static Attendances generateAttendances(List<LocalDateTime> dateTimes) {
        List<Attendance> attendances = new ArrayList<>();
        for (LocalDateTime dateTime : dateTimes) {
            attendances.add(Attendance.from(dateTime));
        }
        return new Attendances(attendances);
    }
}
