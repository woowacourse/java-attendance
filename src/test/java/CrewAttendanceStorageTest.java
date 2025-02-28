import exception.CrewNotExistException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class CrewAttendanceStorageTest {
    @DisplayName("새로운 크루의 출석 저장소를 생성/반환할 수 있다.")
    @Test
    void test1() {
        // given
        String crew = "밍곰";
        CrewAttendanceStorage crewAttendanceStorage = CrewAttendanceStorage.init();

        // when
        crewAttendanceStorage.create(crew);

        // then
        assertDoesNotThrow(() -> {
            crewAttendanceStorage.findAttendanceStorageByCrew(crew);
        });
    }

    @DisplayName("이미 출석 저장소가 존재하는 크루는 저장소를 재생성 할 수 없다.")
    @Test
    void test2() {
        // given
        String crew = "밍곰";
        CrewAttendanceStorage crewAttendanceStorage = CrewAttendanceStorage.init();
        crewAttendanceStorage.create(crew);

        // when & then
        assertThatThrownBy(() -> {
            crewAttendanceStorage.create(crew);
        }).isInstanceOf(RuntimeException.class);
    }

    @DisplayName("등록되지 않은 크루의 출석 저장소를 요청하는 경우 예외가 발생한다.")
    @Test
    void test3() {
        // given
        CrewAttendanceStorage crewAttendanceStorage = CrewAttendanceStorage.init();

        // when & then
        assertThatThrownBy(() -> {
            crewAttendanceStorage.findAttendanceStorageByCrew("누구");
        }).isInstanceOf(CrewNotExistException.class);
    }
}
