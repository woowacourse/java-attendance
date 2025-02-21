package repository;

import domain.Crew;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AttendanceRepositoryImplTest {
    AttendanceRepository attendanceRepository = new AttendanceRepositoryImpl();

    @DisplayName("이미 출석부를 생성한 크루는 재생성할 수 없다.")
    @Test
    void test1() {
        // given
        Crew crew = new Crew("밍곰");
        attendanceRepository.save(crew);

        // when & then
        assertThatThrownBy(() -> attendanceRepository.save(crew))
                .isInstanceOf(RuntimeException.class);
    }
}
