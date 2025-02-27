package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class AttendanceBookTest {

    @Nested
    @DisplayName("성공 테스트")
    class SuccessCases {

        @DisplayName("크루에 해당하는 출석 기록을 반환한다.")
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

        @DisplayName("제적 위험자들의 통계를 반환한다.")
        @Test
        public void calculateRiskOfExpulsionCrewStatistics() throws Exception {
            // given
            final var crew = new Crew("헤일러");
            final var attendanceBook = new AttendanceBook();
            attendanceBook.registerCrew(crew);
            final LocalDate targetDate = LocalDate.of(2024, 12, 13);

            // when
            final List<AttendanceHistory> actual = attendanceBook.calculateRiskOfExpulsionHistory(targetDate);

            // then
            assertThat(actual.getFirst().getCrew()).isEqualTo(crew);
        }

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
    @DisplayName("실패 테스트")
    class FailCases {

        @DisplayName("이미 존재하는 크루에 대해서 새롭게 register하면, 예외가 발생한다.")
        @Test
        public void register() throws Exception {
            // given
            final var crew = new Crew("헤일러");
            final var attendanceBook = new AttendanceBook();
            attendanceBook.registerCrew(crew);

            // when & then
            assertThatThrownBy(() -> attendanceBook.registerCrew(crew))
                    .isInstanceOf(IllegalStateException.class);
        }

        @DisplayName("존재하지 않는 크루에 대해 AttendaceHistory를 요구하면, 예외가 발생한다.")
        @Test
        public void findByCrew() throws Exception {
            // given
            final var crew = new Crew("헤일러");
            final var attendanceBook = new AttendanceBook();

            // when & then
            assertThatThrownBy(() -> attendanceBook.findByCrew(crew))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }
}
