import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import model.Attendance;
import model.AttendanceBook;
import model.CrewAttendances;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RiskOfDismissalTest {

    @DisplayName("크루의 결석, 지각 총합을 구할 수 있다.")
    @Test
    void total_by_crew() {
        // given
        CrewAttendances crewAttendances = new CrewAttendances("율무",
                List.of(
                        new Attendance(LocalDate.of(2024, 12, 2), LocalTime.of(10, 6)),
                        new Attendance(LocalDate.of(2024, 12, 3), LocalTime.of(10, 3)),
                        new Attendance(LocalDate.of(2024, 12, 4), LocalTime.of(10, 4)),
                        new Attendance(LocalDate.of(2024, 12, 5), LocalTime.of(10, 10))
                )
        );

        // when
        final var sum = crewAttendances.attendPolicyCountSum(LocalDate.of(2024, 12, 6));

        // then
        Assertions.assertThat(sum)
                .isEqualTo(2);
    }

    @DisplayName("크루의 결석, 지각 총합을 구할 수 있다. 하지만, 지각 3번은 결석 1번이다.")
    @Test
    void total_by_crew2() {
        // given
        CrewAttendances crewAttendances = new CrewAttendances("율무",
                List.of(
                        new Attendance(LocalDate.of(2024, 12, 2), LocalTime.of(10, 6)),
                        new Attendance(LocalDate.of(2024, 12, 3), LocalTime.of(10, 6)),
                        new Attendance(LocalDate.of(2024, 12, 4), LocalTime.of(10, 6)),
                        new Attendance(LocalDate.of(2024, 12, 5), LocalTime.of(10, 31))
                )
        );

        // when
        final var sum = crewAttendances.attendPolicyCountSum(LocalDate.of(2024, 12, 6));

        // then
        Assertions.assertThat(sum)
                .isEqualTo(2);
    }

    @DisplayName("제적 위험자를 구할 수 있다.")
    @Test
    void risk_of_dismissal() {
        // given
        CrewAttendances crew1 = new CrewAttendances("율무", List.of(
                new Attendance(LocalDate.of(2024, 12, 3), LocalTime.of(10, 6)),
                new Attendance(LocalDate.of(2024, 12, 4), LocalTime.of(10, 2)),
                new Attendance(LocalDate.of(2024, 12, 5), LocalTime.of(10, 57))
        ));
        CrewAttendances crew2 = new CrewAttendances("열무", List.of(
                new Attendance(LocalDate.of(2024, 12, 3), LocalTime.of(10, 6)),
                new Attendance(LocalDate.of(2024, 12, 4), LocalTime.of(10, 6)),
                new Attendance(LocalDate.of(2024, 12, 5), LocalTime.of(10, 57))
        ));
        CrewAttendances crew3 = new CrewAttendances("군자", List.of(
                new Attendance(LocalDate.of(2024, 12, 3), LocalTime.of(10, 6)),
                new Attendance(LocalDate.of(2024, 12, 4), LocalTime.of(10, 6)),
                new Attendance(LocalDate.of(2024, 12, 5), LocalTime.of(10, 6))
        ));
        AttendanceBook book = new AttendanceBook(List.of(crew1, crew2, crew3));

        // when
        List<CrewAttendances> riskOfDismissalCrews = book.findSortedRiskOfDismissalCrews(
                LocalDate.of(2024, 12, 6)
        );

        // then
        Assertions.assertThat(riskOfDismissalCrews)
                .containsExactlyElementsOf(List.of(crew2, crew1, crew3));
    }
}
