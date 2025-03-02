package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class CrewsTest {

    @Nested
    @DisplayName("닉네임에 해당하는 크루를 반환한다.")
    class FindCrewByName {

        @DisplayName("닉네임에 해당하는 크루를 반환한다.")
        @Test
        public void findByName() throws Exception {
            // given
            final var crewName = "우가";
            final var crew = new Crew(crewName);
            final var crews = new Crews(List.of(crew));

            // when
            final Crew actual = crews.findByName(crewName);

            // then
            assertThat(actual.getName()).isEqualTo(crewName);
        }

        @DisplayName("닉네임에 해당하는 크루가 존재하지 않는다면, 예외가 발생한다.")
        @Test
        public void findByNameInNotContainsCrew() throws Exception {
            // given
            final var crewName = "우가";
            final var crews = new Crews(List.of());

            // when & then
            assertThatThrownBy(() -> {
                crews.findByName(crewName);
            }).isInstanceOf(IllegalArgumentException.class);

        }

    }

    @Nested
    @DisplayName("AttendanceBook에 현재 등록된 크루를 등록한다.")
    class RegisterCrewsToAttendanceBook {

        @DisplayName("Crews에 등록된 크루들을 올바르게 AttendanceBook에 등록한다.")
        @Test
        public void registerCrewsToAttendanceBook() throws Exception {
            // given
            final List<String> crewNameList = List.of(
                    "헤일러", "우가", "포스티", "미소", "부기"
            );
            final List<Crew> crewList = crewNameList.stream()
                    .map(Crew::new)
                    .collect(Collectors.toList());
            final var crews = new Crews(crewList);
            final AttendanceBook attendanceBook = new AttendanceBook();

            // when
            crews.registerCrewsToAttendanceBook(attendanceBook);

            // then
            assertSoftly(s -> {
                crewList.forEach(crew -> s.assertThat(attendanceBook.containsCrew(crew)).isTrue());
            });
        }

    }

}
