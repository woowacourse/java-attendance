package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class AttendanceBookTest {

    @Nested
    @DisplayName("크루에 해당하는 출석 하기")
    class FindByCrew {

        @DisplayName("크루에 해당하는 출석 기록을 올바르게 반환한다.")
        @Test
        public void findByCrew() throws Exception {
            // given
            final var crew = new Crew("헤일러");
            final var attendanceBook = new AttendanceBook();
            attendanceBook.registerCrew(crew);

            // when
            final AttendanceHistory actual = attendanceBook.findByCrew(crew);

            // then
            assertThat(actual.getCrew()).isEqualTo(crew);
        }

        @DisplayName("존재하지 않는 크루에 대해 AttendaceHistory를 요구하면, 예외가 발생한다.")
        @Test
        public void findByCrewInNotContainsCrew() throws Exception {
            // given
            final var crew = new Crew("헤일러");
            final var attendanceBook = new AttendanceBook();

            // when & then
            assertThatThrownBy(() -> attendanceBook.findByCrew(crew))
                    .isInstanceOf(IllegalArgumentException.class);
        }

    }

    @Nested
    @DisplayName("제적 위험자들의 통계 계산하기")
    class CalculateRiskOfExpulsionCrewStatistics {

        @DisplayName("제적 위험자들의 통계를 올바르게 계산한다.")
        @ParameterizedTest
        @ValueSource(ints = {4, 5, 7, 10})
        public void calculateRiskOfExpulsionCrewStatistics(final int dayOfMonth) throws Exception {
            // given
            final var crew = new Crew("헤일러");
            final var attendanceBook = new AttendanceBook();
            attendanceBook.registerCrew(crew);
            final LocalDate targetDate = LocalDate.of(2024, 12, dayOfMonth);

            // when
            final List<AttendanceHistory> actual = attendanceBook.calculateRiskOfExpulsionHistory(targetDate);

            // then
            assertThat(actual.getFirst().getCrew()).isEqualTo(crew);
        }

        @DisplayName("제적 위험자가 아니라면, 통계에 포함되지 않는다.")
        @Test
        public void calculateRiskOfExpulsionCrewStatisticsForNormal() throws Exception {
            // given
            final var crew = new Crew("헤일러");
            final var attendanceBook = new AttendanceBook();
            attendanceBook.registerCrew(crew);
            final LocalDate targetDate = LocalDate.of(2024, 12, 3);

            // when
            final List<AttendanceHistory> actual = attendanceBook.calculateRiskOfExpulsionHistory(targetDate);

            // then
            assertThat(actual).isEmpty();
        }
    }

    @Nested
    @DisplayName("크루가 출석부에 등록되었는지 여부를 검사")
    class ContainsCrew {

        @DisplayName("크루가 출석부에 등록되어 있는지 여부를 검사한다.")
        @Test
        public void containsCrew() throws Exception {
            // given
            final var crew1 = new Crew("헤일러");
            final var crew2 = new Crew("미소");
            final var attendanceBook = new AttendanceBook();
            attendanceBook.registerCrew(crew1);

            // when
            final boolean actual1 = attendanceBook.containsCrew(crew1);
            final boolean actual2 = attendanceBook.containsCrew(crew2);

            // then
            assertThat(actual1).isTrue();
            assertThat(actual2).isFalse();
        }

    }

    @Nested
    @DisplayName("크루를 출석부에 등록하기")
    class RegisterCrew {

        @DisplayName("크루를 출석부에 등록하면 올바르게 등록된다.")
        @Test
        public void registerCrew() throws Exception {
            // given
            final var crew = new Crew("헤일러");
            final var attendanceBook = new AttendanceBook();

            // when
            attendanceBook.registerCrew(crew);

            // then
            assertThat(attendanceBook.containsCrew(crew)).isTrue();
        }

        @DisplayName("이미 존재하는 크루에 대해서 새롭게 register하면, 예외가 발생한다.")
        @Test
        public void registerCrewInContainsCrew() throws Exception {
            // given
            final var crew = new Crew("헤일러");
            final var attendanceBook = new AttendanceBook();
            attendanceBook.registerCrew(crew);

            // when & then
            assertThatThrownBy(() -> attendanceBook.registerCrew(crew))
                    .isInstanceOf(IllegalStateException.class);
        }

    }

}
