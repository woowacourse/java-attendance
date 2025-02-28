import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class CrewAttendanceTest {
    @DisplayName("새로운 크루의 출석 저장소를 생성/반환할 수 있다.")
    @Test
    void test1() {
        // given
        String crew = "밍곰";
        CrewAttendance crewAttendance = CrewAttendance.init();

        // when
        crewAttendance.create(crew);

        // then
        assertDoesNotThrow(() -> {
            crewAttendance.findAttendanceStorageByCrew(name);
        });
    }

    @DisplayName("이미 출석 저장소가 존재하는 크루는 저장소를 재생성 할 수 없다.")
    @Test
    void test2() {
        // given
        String crew = "밍곰";
        CrewAttendance crewAttendance = CrewAttendance.init();
        crewAttendance.create(crew);

        // when & then
        assertThatThrownBy(() -> {
            crewAttendance.create(crew);
        }).isInstanceOf(RuntimeException.class);
    }
}
