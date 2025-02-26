package attendance.loader;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendancesLoaderTest {

    @DisplayName("크루 이름과 기록이 개수대로 모두 저장된다")
    @Test
    void test_loadAttendanceFile() {
        // given
        AttendancesLoader attendancesLoader = new AttendancesLoader();

        // when
        attendancesLoader.load();

        // then
        Assertions.assertThat(attendancesLoader.getRawDatas()).hasSize(5);
        
        int totalDataCount = attendancesLoader.getRawDatas().values().stream()
                .mapToInt(List::size)
                .sum();
        Assertions.assertThat(totalDataCount).isEqualTo(41);
    }
}