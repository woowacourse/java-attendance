package attendance.util;

import attendance.domain.CrewAttendance;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class FileLoaderTest {

    @DisplayName("CrewAttendance 생성 성공")
    @Test
    void test1() {
        String data = "쿠키,2024-12-13 10:08";
        List<String> seperatedData = Arrays.stream(data.split(",")).toList();
        LocalDateTime localDateTime = LocalDateTime.parse(seperatedData.getLast().replace(" ", "T"));

        Assertions.assertThat(FileLoader.createCrewAttendance(seperatedData.getFirst(), localDateTime))
                .isNotNull()
                .isInstanceOf(CrewAttendance.class);
    }

    @DisplayName("CrewAttendance 여러 개 생성 성공")
    @Test
    void test2() {
        List<String> datas = List.of(
                "쿠키,2024-12-13 10:08",
                "빙봉,2024-12-13 10:07",
                "빙티,2024-12-13 10:07",
                "이든,2024-12-13 10:07"
        );

        List<CrewAttendance> crewAttendances = FileLoader.loadAll(datas);

        Assertions.assertThat(crewAttendances)
                .hasSize(4)
                .hasOnlyElementsOfType(CrewAttendance.class);
    }
}
