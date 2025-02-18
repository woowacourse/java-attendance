package attendance.util;

import attendance.domain.Attendance;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class FileLoaderTest {

    @DisplayName("Attendance 생성 성공")
    @Test
    void test1(){
        String data = "쿠키,2024-12-13 10:08";
        List<String> seperatedData = Arrays.stream(data.split(",")).toList();
        LocalDateTime localDateTime = LocalDateTime.parse(seperatedData.getLast().replace(" ", "T"));

        Assertions.assertThat(FileLoader.createAttendance(seperatedData.getFirst(), localDateTime))
                .isNotNull()
                .isInstanceOf(Attendance.class);
    }

    @DisplayName("Attendance 여러 개 생성 성공")
    @Test
    void test2(){
        List<String> datas = List.of(
                "쿠키,2024-12-13 10:08",
                "빙봉,2024-12-13 10:07",
                "빙티,2024-12-13 10:07",
                "이든,2024-12-13 10:07"
        );

        List<Attendance> attendances = FileLoader.loadAll(datas);

        Assertions.assertThat(attendances)
                .hasSize(4)
                .hasOnlyElementsOfType(Attendance.class);
    }
}
