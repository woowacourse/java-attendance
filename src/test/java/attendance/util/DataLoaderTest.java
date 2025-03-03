package attendance.util;

import static org.assertj.core.api.Assertions.assertThat;

import attendance.domain.Attendances;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayName("데이터 객체 변환")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class DataLoaderTest {

    @Test
    void 문자열_데이터를_객체로_변환한다() {
        List<String> data = List.of(
                "쿠키,2024-12-13 10:08",
                "빙봉,2024-12-13 10:07",
                "빙티,2024-12-13 10:07",
                "이든,2024-12-13 10:07"
        );
        Map<String, Attendances> crewAttendances = DataLoader.loadAttendancesData(data);

        assertThat(crewAttendances.size()).isEqualTo(4);
    }
}
